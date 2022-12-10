package io.loomus.edu.modules.course.entities.course_subscription

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "course_subscription")
class CourseSubscriptionEntity(

    @Id
    @Column(name = "course_id")
    val courseId: Int = 0,

    @Column(name = "max_students_count")
    var maxStudentsCount: Int = 0,

    @Column(name = "max_videos_count")
    var maxVideosCount: Int = 0,

    @Column(name = "max_resource_size")
    var maxResourceSize: Long = 0,

    @Column(name = "expires_at")
    @CreationTimestamp
    var expiresAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now()

)