package io.loomus.edu.modules.homework.entities.homework_submission_resource

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface HomeworkSubmissionResourceEntityRepository : JpaRepository<HomeworkSubmissionResourceEntity, Int> {

    @Query("select r from homework_submission_resource r where r.id = :id and r.courseId = :courseId and r.isDeleted = false")
    fun findByCourseIdAndId(courseId: Int, id: Int): HomeworkSubmissionResourceEntity?


    @Query("select r from homework_submission_resource r where r.studentId = :studentId and r.homeworkId = :homeworkId and r.isDeleted = false")
    fun findAllByHomeworkIdAndStudentId(studentId: Int, homeworkId: Int): List<HomeworkSubmissionResourceEntity>


    @Query("select r from homework_submission_resource r where r.studentId in :studentIds and r.homeworkId = :homeworkId and r.isDeleted = false")
    fun findAllByHomeworkIdAndStudentIds(homeworkId: Int, studentIds: Set<Int>): List<HomeworkSubmissionResourceEntity>


    @Query("select sum(r.size) from homework_submission_resource r where r.courseId = :courseId and r.isDeleted = false and r.isActive = true")
    fun sumSizeByCourseId(courseId: Int): Long?

}