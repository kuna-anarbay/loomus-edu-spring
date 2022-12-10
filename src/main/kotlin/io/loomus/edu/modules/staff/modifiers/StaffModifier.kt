package io.loomus.edu.modules.staff.modifiers

import io.loomus.edu.extensions.nullIfNeeded
import io.loomus.edu.extensions.phone
import io.loomus.edu.modules.staff.controllers.staff.requests.CreateStaffBody
import io.loomus.edu.modules.staff.controllers.staff.requests.EditStaffBody
import io.loomus.edu.modules.staff.controllers.staff.responses.StaffResponse
import io.loomus.edu.modules.staff.entities.staff.StaffEntity
import io.loomus.edu.modules.staff.entities.staff.StaffEntityRepository
import io.loomus.edu.security.annotations.is_staff.Staff
import io.loomus.edu.security.permission.PermissionService
import io.loomus.edu.utils.HttpResponse
import io.loomus.edu.utils.LocaledMessage
import io.loomus.edu.utils.Validator
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class StaffModifier(
    private val repository: StaffEntityRepository,
    private val permissionService: PermissionService
) {

    fun createByCourseId(staff: Staff, courseId: Int, body: CreateStaffBody): StaffResponse {
        Validator.validatePhone(body.phone)
        if (repository.existsByCourseIdAndPhone(courseId, body.phone.phone))
            throw HttpResponse.badRequest(LocaledMessage.localize("exception.staff.email-already-exists"))
        permissionService.evictRoleByPhone(body.phone.phone)
        val entity = repository.save(body.toEntity(courseId))

        return StaffResponse(entity)
    }


    fun editByCourseIdAndId(courseId: Int, staffId: Int, body: EditStaffBody): HttpResponse {
        val entity = repository.findActiveByCourseIdAndId(courseId, staffId)
            ?: throw HttpResponse.notFound()
        if (body.phone.phone != entity.phone.phone && repository.existsByCourseIdAndPhone(courseId, body.phone.phone))
            throw HttpResponse.badRequest(LocaledMessage.localize("exception.staff.email-already-exists"))
        entity.firstName = body.firstName
        entity.lastName = body.lastName?.nullIfNeeded
        entity.phone = body.phone.phone
        repository.save(entity)

        return HttpResponse.success()
    }


    fun deleteByCourseIdAndId(courseId: Int, staffId: Int): HttpResponse {
        val entity = repository.findActiveByCourseIdAndId(courseId, staffId)
            ?: throw HttpResponse.notFound()
        if (entity.role == StaffEntity.Role.OWNER)
            throw HttpResponse.forbidden()
        entity.isDeleted = true
        repository.save(entity)

        permissionService.evictRoleByPhone(entity.phone.phone)

        return HttpResponse.success()
    }

}