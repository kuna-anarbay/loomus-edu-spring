package io.loomus.edu.modules.course.modifiers

import io.loomus.edu.modules.course.controllers.course_package.requests.CreatePackageBody
import io.loomus.edu.modules.course.controllers.course_package.requests.EditPackageBody
import io.loomus.edu.modules.course.controllers.course_package.responses.PackageResponse
import io.loomus.edu.modules.course.entities.course_package.CoursePackageEntityRepository
import io.loomus.edu.utils.HttpResponse
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class CoursePackageModifier(
    private val repository: CoursePackageEntityRepository
) {

    fun createByCourseId(courseId: Int, body: CreatePackageBody): PackageResponse {
        val entity = repository.save(body.toEntity(courseId))

        return PackageResponse(entity)
    }


    fun editByCourseIdAndId(courseId: Int, packageId: Int, body: EditPackageBody): HttpResponse {
        val entity = repository.findByCourseIdAndId(courseId, packageId)
            ?: throw HttpResponse.notFound()
        entity.name = body.name
        entity.homeworkAvailable = body.homeworkAvailable
        repository.save(entity)

        return HttpResponse.success()
    }


    fun deleteByCourseIdAndId(courseId: Int, packageId: Int): HttpResponse {
        val entity = repository.findByCourseIdAndId(courseId, packageId)
            ?: throw HttpResponse.notFound()
        entity.isDeleted = true
        repository.save(entity)

        return HttpResponse.success()
    }


}