package io.loomus.edu.modules.course.entities.course_banner

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "course_banner")
class CourseBannerEntity(

    @Id
    @Column(name = "course_id")
    val courseId: Int = 0,

    @Column(name = "version")
    var version: Int = 1,

    @Column
    var path: String = "",

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now()

)