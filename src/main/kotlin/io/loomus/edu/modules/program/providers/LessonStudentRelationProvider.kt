package io.loomus.edu.modules.program.providers

import io.loomus.edu.modules.program.controllers.lesson_student_relation.responses.LessonStudentRelationResponse
import io.loomus.edu.modules.program.entities.lesson_student_relation.LessonStudentRelationEntity
import io.loomus.edu.modules.program.entities.lesson_student_relation.LessonStudentRelationEntityRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class LessonStudentRelationProvider(
    private val repository: LessonStudentRelationEntityRepository
) {

    fun findAllByCourseIdAndStudentId(courseId: Int, studentId: Int) =
        repository.findAllByCourseIdAndStudentId(courseId, studentId).map {
            LessonStudentRelationResponse(it)
        }


    fun findByStudentIdAndLessonIdOrNull(studentId: Int, lessonId: Int) =
        repository.findByIdOrNull(LessonStudentRelationEntity.Id(studentId = studentId, lessonId = lessonId))?.let {
            LessonStudentRelationResponse(it)
        }

}