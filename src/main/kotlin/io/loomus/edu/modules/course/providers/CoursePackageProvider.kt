package io.loomus.edu.modules.course.providers

import io.loomus.edu.modules.course.controllers.course_package.responses.PackageResponse
import io.loomus.edu.modules.course.entities.course_package.CoursePackageEntityRepository
import io.loomus.edu.modules.program.entities.lesson_package_relation.LessonPackageRelationEntityRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class CoursePackageProvider(
    private val repository: CoursePackageEntityRepository,
    private val lessonPackageRelationRepository: LessonPackageRelationEntityRepository
) {


    fun findAllByCourseId(courseId: Int): List<PackageResponse> {
        val entities = repository.findAllActiveByCourseId(courseId)
        val counts = lessonPackageRelationRepository.countAllByCourseId(courseId)

        return entities.map { entity ->
            PackageResponse(entity, counts.firstOrNull { it.packageId == entity.id }?.count ?: 0)
        }
    }


}