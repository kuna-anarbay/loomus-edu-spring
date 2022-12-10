package io.loomus.edu.modules.program.modifiers

import io.loomus.edu.extensions.nullIfNeeded
import io.loomus.edu.modules.course.controllers.course_package.responses.PackageResponse
import io.loomus.edu.modules.course.entities.course_package.CoursePackageEntityRepository
import io.loomus.edu.modules.program.controllers.lesson.requests.CreateLessonBody
import io.loomus.edu.modules.program.controllers.lesson.requests.EditLessonBody
import io.loomus.edu.modules.program.controllers.lesson.requests.ReorderLessonBody
import io.loomus.edu.modules.program.controllers.lesson.responses.LessonResponse
import io.loomus.edu.modules.program.entities.lesson.LessonEntity
import io.loomus.edu.modules.program.entities.lesson.LessonEntityRepository
import io.loomus.edu.utils.HttpResponse
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import kotlin.math.max
import kotlin.math.min

@Service
@Transactional
class LessonModifier(
    private val repository: LessonEntityRepository,
    private val resourceModifier: LessonResourceModifier,
    private val lessonPackageRelationModifier: LessonPackageRelationModifier,
    private val packageRepository: CoursePackageEntityRepository,
    private val lessonVideoModifier: LessonVideoModifier
) {

    fun createByCourseId(courseId: Int, body: CreateLessonBody): LessonResponse {
        val index = repository.countByCourseIdAndModuleId(courseId, body.moduleId)
        val entity = body.toEntity(courseId, index.toInt() + 1)
        val lesson = repository.save(entity)
        body.packageIds.toSet().map {
            lessonPackageRelationModifier.createByCourseIdAndPackageId(courseId, it, lesson.id)
        }
        val packages = packageRepository.findAllById(body.packageIds.toSet()).map {
            PackageResponse(it)
        }

        return LessonResponse(lesson, null, listOf(), false, packages)
    }


    fun editByCourseIdAndId(courseId: Int, lessonId: Int, body: EditLessonBody): HttpResponse {
        val entity = repository.findByCourseIdAndId(courseId, lessonId) ?: throw HttpResponse.notFound()
        entity.name = body.name
        entity.description = body.description?.nullIfNeeded
        entity.status = LessonEntity.Status.valueOf(body.status)
        repository.save(entity)

        return HttpResponse.success()
    }

    fun reorderByCourseIdAndId(courseId: Int, lessonId: Int, body: ReorderLessonBody): HttpResponse {
        val lesson = repository.findByCourseIdAndId(courseId, lessonId) ?: throw HttpResponse.notFound()
        if (lesson.index == body.index) return HttpResponse.success()
        val minIndex = min(body.index, lesson.index)
        val maxIndex = max(body.index, lesson.index)
        val lessons = repository.findAllByCourseIdAndModuleIdAndIndexBetween(
            lesson.courseId,
            lesson.moduleId,
            minIndex, maxIndex
        )

        if (lesson.index > body.index) {
            lessons.forEach { it.index++ }
        } else if (lesson.index < body.index) {
            lessons.forEach { it.index-- }
        }
        lessons.firstOrNull { it.id == lessonId }?.index = body.index

        repository.saveAll(lessons)

        return HttpResponse.success()
    }


    fun deleteByCourseIdAndId(courseId: Int, lessonId: Int): HttpResponse {
        val lesson = repository.findByCourseIdAndId(courseId, lessonId) ?: throw HttpResponse.notFound()
        val lessons = repository.findAllHigherByCourseId(lesson.courseId, lesson.index)
        lessons.forEach { it.index-- }

        resourceModifier.deleteByCourseIdAndLessonId(courseId, lessonId)
        repository.saveAll(lessons)
        repository.delete(lesson)
        lessonVideoModifier.deleteByLessonId(courseId, lessonId)

        return HttpResponse.success()
    }

}