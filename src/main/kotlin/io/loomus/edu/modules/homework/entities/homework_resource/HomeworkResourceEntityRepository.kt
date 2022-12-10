package io.loomus.edu.modules.homework.entities.homework_resource

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface HomeworkResourceEntityRepository : JpaRepository<HomeworkResourceEntity, Int> {

    @Query("select r from homework_resource r where r.courseId = :courseId and r.id = :id and r.isDeleted = false")
    fun findByCourseIdAndId(courseId: Int, id: Int): HomeworkResourceEntity?


    @Query("select r from homework_resource r where r.courseId = :courseId and r.homeworkId = :homeworkId and r.isDeleted = false")
    fun findAllByCourseIdAndHomeworkId(courseId: Int, homeworkId: Int): List<HomeworkResourceEntity>


    @Query("select sum(r.size) from homework_resource r where r.courseId = :courseId and r.isDeleted = false and r.isActive = true")
    fun sumSizeByCourseId(courseId: Int): Long?

}