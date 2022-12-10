package io.loomus.edu.modules.program.entities.lesson_student_relation

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LessonStudentRelationEntityRepository :
    JpaRepository<LessonStudentRelationEntity, LessonStudentRelationEntity.Id> {

    interface ProgressDto {
        val studentId: Int
        val courseId: Int
        val opened: Int
        val passed: Int
    }

    @Query("select r from lesson_student_relation r where r.courseId = :courseId and r.id.studentId = :studentId and r.isDeleted = false")
    fun findAllByCourseIdAndStudentId(courseId: Int, studentId: Int): List<LessonStudentRelationEntity>


    @Query(
        "select lsr.course_id as courseId, lsr.student_id as studentId, " +
                "count(lsr.lesson_id) as opened, " +
                "sum(if(lsr.homework_passed, 1, 0)) as passed from lesson_student_relation lsr " +
                "where lsr.student_id in :studentIds and lsr.is_deleted = false " +
                "group by lsr.course_id, lsr.student_id",
        nativeQuery = true
    )
    fun findProgressByStudentIds(studentIds: Set<Int>): List<ProgressDto>

}