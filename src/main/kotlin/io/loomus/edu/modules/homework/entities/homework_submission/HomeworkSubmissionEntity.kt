package io.loomus.edu.modules.homework.entities.homework_submission

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "homework_submission")
class HomeworkSubmissionEntity(

    @EmbeddedId
    val id: Id = Id(),

    @Column(name = "course_id")
    val courseId: Int = 0,

    @Column
    var value: String? = null,

    @Column
    @Enumerated(value = EnumType.STRING)
    var status: Status = Status.PENDING,

    @Column(name = "tries_count")
    var triesCount: Int = 0,

    @Column
    var notes: String? = null,

    @Column(name = "submitted_at")
    var submittedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now()

) {

    enum class Status {
        PENDING, ACCEPTED, DECLINED
    }

    @Embeddable
    class Id(

        @Column(name = "homework_id")
        val homeworkId: Int = 0,

        @Column(name = "student_id")
        val studentId: Int = 0

    ) : Serializable

}