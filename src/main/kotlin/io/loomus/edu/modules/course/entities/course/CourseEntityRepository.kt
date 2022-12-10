package io.loomus.edu.modules.course.entities.course

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CourseEntityRepository : JpaRepository<CourseEntity, Int> {

    fun findByUsername(username: String): CourseEntity?

    @Query("select c from course c where c.id = :courseId and c.isDeleted = false")
    fun findActiveById(courseId: Int): CourseEntity?


    @Query("select c from course c where c.id in :courseIds and c.isDeleted = false")
    fun findAllActiveByIds(courseIds: Set<Int>): List<CourseEntity>


}