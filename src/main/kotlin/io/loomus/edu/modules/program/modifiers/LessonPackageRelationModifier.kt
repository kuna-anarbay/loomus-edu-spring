package io.loomus.edu.modules.program.modifiers

import io.loomus.edu.modules.program.entities.lesson_package_relation.LessonPackageRelationEntity
import io.loomus.edu.modules.program.entities.lesson_package_relation.LessonPackageRelationEntityRepository
import io.loomus.edu.utils.HttpResponse
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class LessonPackageRelationModifier(
    private val repository: LessonPackageRelationEntityRepository
) {

    fun createByCourseIdAndPackageId(courseId: Int, packageId: Int, lessonId: Int): HttpResponse {
        val id = LessonPackageRelationEntity.Id(packageId = packageId, lessonId = lessonId)
        repository.save(LessonPackageRelationEntity(id = id, courseId = courseId))

        return HttpResponse.success()
    }


    fun deleteByCourseIdAndPackageId(courseId: Int, packageId: Int, lessonId: Int): HttpResponse {
        val id = LessonPackageRelationEntity.Id(packageId = packageId, lessonId = lessonId)
        repository.deleteById(id)

        return HttpResponse.success()
    }

}