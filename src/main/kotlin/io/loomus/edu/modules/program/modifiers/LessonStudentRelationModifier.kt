package io.loomus.edu.modules.program.modifiers

import io.loomus.edu.modules.homework.entities.homework.HomeworkEntityRepository
import io.loomus.edu.modules.program.controllers.lesson_student_relation.responses.LessonStudentRelationResponse
import io.loomus.edu.modules.program.entities.lesson_student_relation.LessonStudentRelationEntity
import io.loomus.edu.modules.program.entities.lesson_student_relation.LessonStudentRelationEntityRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class LessonStudentRelationModifier(
    private val repository: LessonStudentRelationEntityRepository,
    private val homeworkRepository: HomeworkEntityRepository
) {


    fun setLessonPassedByCourseIdAndStudentId(
        courseId: Int, studentId: Int, lessonId: Int
    ): LessonStudentRelationResponse {
        val id = LessonStudentRelationEntity.Id(studentId = studentId, lessonId = lessonId)
        val entity = repository.findByIdOrNull(id)
            ?: LessonStudentRelationEntity(id = id, courseId = courseId)

        if (!entity.homeworkPassed) {
            val test = homeworkRepository.findByIdOrNull(entity.id.lessonId)
            if (test == null) {
                entity.homeworkPassed = true
            }
        }
        repository.save(entity)

        return LessonStudentRelationResponse(entity)
    }


    fun setHomeworkPassedByCourseIdAndStudentId(
        courseId: Int, studentId: Int, lessonId: Int
    ) {
        val id = LessonStudentRelationEntity.Id(studentId = studentId, lessonId = lessonId)
        repository.findByIdOrNull(id)?.let {
            it.homeworkPassed = true

            repository.save(it)
        }
    }

}