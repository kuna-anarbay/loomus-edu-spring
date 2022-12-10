package io.loomus.edu.modules.program.providers

import io.loomus.edu.modules.program.controllers.lesson_package_relation.responses.LessonPackageRelationResponse
import io.loomus.edu.modules.program.controllers.lesson_package_relation.responses.ModulePackageRelationResponse
import io.loomus.edu.modules.program.entities.lesson_package_relation.LessonPackageRelationEntityRepository
import io.loomus.edu.modules.program.controllers.lesson.responses.LessonResponse
import io.loomus.edu.modules.program.controllers.module.responses.ModuleResponse
import io.loomus.edu.modules.program.entities.lesson.LessonEntityRepository
import io.loomus.edu.modules.program.entities.module.ModuleEntityRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class LessonPackageRelationProvider(
    private val repository: LessonPackageRelationEntityRepository,
    private val moduleRepository: ModuleEntityRepository,
    private val lessonRepository: LessonEntityRepository
) {

    fun findAllByCourseIdAndPackageId(courseId: Int, packageId: Int): List<ModulePackageRelationResponse> {
        val entities = repository.findAllByCourseIdAndPackageId(courseId, packageId)
        val modules = moduleRepository.findAllByCourseId(courseId)
        val lessons = lessonRepository.findAllByCourseId(courseId)

        return modules.map { module ->
            val moduleLessons = lessons.filter { it.moduleId == module.id }.map { lesson ->
                val lessonResponse = LessonResponse(lesson)
                val isConnected = entities.any { it.id.lessonId == lesson.id }
                LessonPackageRelationResponse(lessonResponse, isConnected)
            }
            val moduleResponse = ModuleResponse(module)

            ModulePackageRelationResponse(moduleResponse, moduleLessons)
        }
    }

}