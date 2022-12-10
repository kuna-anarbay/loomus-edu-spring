package io.loomus.edu.modules.program.entities.lesson_student_relation

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity

@Entity(name = "lesson_student_relation")
class LessonStudentRelationEntity(

    @EmbeddedId
    val id: Id = Id(),

    @Column(name = "course_id")
    val courseId: Int = 0,

    @Column(name = "homework_passed")
    var homeworkPassed: Boolean = false,

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now()

) {

    @Embeddable
    class Id(

        @Column(name = "student_id")
        val studentId: Int = 0,

        @Column(name = "lesson_id")
        val lessonId: Int = 0

    ) : Serializable

}