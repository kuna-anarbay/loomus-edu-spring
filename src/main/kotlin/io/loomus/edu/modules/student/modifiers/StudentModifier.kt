package io.loomus.edu.modules.student.modifiers

import io.loomus.edu.extensions.nullIfNeeded
import io.loomus.edu.extensions.phone
import io.loomus.edu.modules.student.controllers.student.requests.CreateStudentBody
import io.loomus.edu.modules.student.controllers.student.requests.EditStudentBody
import io.loomus.edu.modules.student.controllers.student.responses.StudentResponse
import io.loomus.edu.modules.student.entities.student.StudentEntityRepository
import io.loomus.edu.security.annotations.is_staff.Staff
import io.loomus.edu.security.permission.PermissionService
import io.loomus.edu.utils.HttpResponse
import io.loomus.edu.utils.LocaledMessage
import io.loomus.edu.utils.Validator
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class StudentModifier(
    private val repository: StudentEntityRepository,
    private val permissionService: PermissionService
) {

    fun createByCourseId(staff: Staff, courseId: Int, body: CreateStudentBody): StudentResponse {
        Validator.validatePhone(body.phone)
        if (repository.existsByCourseIdAndPhone(courseId, body.phone))
            throw HttpResponse.badRequest(LocaledMessage.localize("exception.student.phone-already-exists"))
        if (body.isActive) {
            if (!permissionService.canAddStudent(courseId))
                throw HttpResponse.forbidden()
        }
        val entity = repository.save(body.toEntity(courseId))
        if (body.isActive) {
            permissionService.evictInfoByCourseId(courseId)
            permissionService.evictRoleByPhone(body.phone.phone)
        }

        return StudentResponse(entity)
    }


    fun importByCourseId(staff: Staff, courseId: Int, body: List<CreateStudentBody>): List<StudentResponse> {
        if (body.isEmpty()) throw HttpResponse.badRequest()
        if (body.size > 100) throw HttpResponse.badRequest()
        if (repository.existsByCourseIdAndPhoneIn(courseId, body.map { it.phone }.toSet()))
            throw HttpResponse.badRequest(LocaledMessage.localize("exception.student.phone-already-exists"))
        val activeStudents = body.filter { it.isActive }
        if (activeStudents.isNotEmpty()) {
            if (!permissionService.canAddStudent(courseId, activeStudents.size))
                throw HttpResponse.forbidden()
        }
        val entities = repository.saveAll(body.map { it.toEntity(courseId) })
        val activeEntities = entities.filter { it.isActive }
        if (activeEntities.isNotEmpty()) {
            permissionService.evictRoleByPhones(activeEntities.map { it.phone }.toSet())
            permissionService.evictInfoByCourseId(courseId)
        }

        return entities.map { StudentResponse(it) }
    }


    fun editLastOpenedAt(courseId: Int, studentId: Int): HttpResponse {
        val entity = repository.findByCourseIdAndId(courseId, studentId)
            ?: throw HttpResponse.notFound()
        entity.lastOpenedAt = LocalDateTime.now()
        repository.save(entity)

        return HttpResponse.success()
    }


    fun editByCourseIdAndId(courseId: Int, studentId: Int, body: EditStudentBody): HttpResponse {
        val entity = repository.findByCourseIdAndId(courseId, studentId)
            ?: throw HttpResponse.notFound()

        if (entity.isActive != body.isActive) {
            if (!entity.isActive && body.isActive) {
                if (!permissionService.canAddStudent(courseId))
                    throw HttpResponse.forbidden()
            }
            permissionService.evictInfoByCourseId(courseId)
            permissionService.evictRoleByPhone(entity.phone.phone)
        }

        entity.firstName = body.firstName
        entity.lastName = body.lastName?.nullIfNeeded
        entity.packageId = body.packageId
        repository.save(entity)

        return HttpResponse.success()
    }


    fun deleteByCourseIdAndId(courseId: Int, studentId: Int): HttpResponse {
        val entity = repository.findByCourseIdAndId(courseId, studentId)
            ?: throw HttpResponse.notFound()
        entity.isDeleted = true
        repository.save(entity)
        permissionService.evictInfoByCourseId(courseId)
        permissionService.evictRoleByPhone(entity.phone.phone)

        return HttpResponse.success()
    }

}