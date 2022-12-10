package io.loomus.edu.modules.homework.entities.homework_submission

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface HomeworkSubmissionEntityRepository : JpaRepository<HomeworkSubmissionEntity, HomeworkSubmissionEntity.Id> {

    interface CountDto {
        val staffId: Int
        val count: Long
    }

    @Query(
        "select s from homework_submission s " +
                "inner join student st on st.id = s.id.studentId and st.isDeleted = false " +
                "where s.courseId = :courseId and s.id.homeworkId = :homeworkId " +
                "and (:status is null or s.status = :status) " +
                "order by s.submittedAt desc "
    )
    fun findAllByCourseIdAndHomeworkId(
        courseId: Int, homeworkId: Int, status: HomeworkSubmissionEntity.Status?, pageable: Pageable
    ): List<HomeworkSubmissionEntity>


    @Query(
        "select count(s) from homework_submission s " +
                "inner join student st on st.id = s.id.studentId and st.isDeleted = false " +
                "where s.courseId = :courseId and s.id.homeworkId = :homeworkId " +
                "and (:status is null or s.status = :status) "
    )
    fun countAllByCourseIdAndHomeworkId(
        courseId: Int, homeworkId: Int, status: HomeworkSubmissionEntity.Status?
    ): Long

//
//    @Query(
//        "select hs.staff_id as staffId, count(hs.student_id) as count from homework_submission hs " +
//                "where hs.staff_id is not null and hs.course_id = :courseId and hs.homework_id = :homeworkId " +
//                "group by hs.staff_id", nativeQuery = true
//    )
//    fun findWeightsByCourseIdAndHomeworkId(
//        courseId: Int, homeworkId: Int
//    ): List<CountDto>

}