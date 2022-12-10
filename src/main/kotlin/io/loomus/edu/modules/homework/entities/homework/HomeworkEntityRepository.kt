package io.loomus.edu.modules.homework.entities.homework

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface HomeworkEntityRepository : JpaRepository<HomeworkEntity, Int> {

    interface HomeworkStatDto {
        val total: Long
        val submittedCount: Long
        val acceptedCount: Long
    }

    @Query("select h from homework h where h.id = :lessonId and h.courseId = :courseId and h.isDeleted = false")
    fun findActiveByCourseIdAndId(courseId: Int, lessonId: Int): HomeworkEntity?


    @Query(
        "select count(s.id) as total, " +
                "count(hs.student_id) as submittedCount, " +
                "sum(if(hs.is_reviewed = true, 1, 0)) as acceptedCount from student s " +
                "left join homework_submission hs on s.id = hs.student_id and hs.homework_id = :lessonId " +
                "where s.is_active = true and s.course_id = :courseId", nativeQuery = true
    )
    fun findHomeworkStat(courseId: Int, lessonId: Int): HomeworkStatDto

}