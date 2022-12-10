package io.loomus.edu.modules.course.entities.course_subscription

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CourseSubscriptionEntityRepository : JpaRepository<CourseSubscriptionEntity, Int>{

    @Query("select cs from course_subscription cs where cs.courseId = :courseId and cs.isDeleted = false")
    fun findByCourseId(courseId: Int): CourseSubscriptionEntity?

}
