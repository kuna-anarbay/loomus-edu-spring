package io.loomus.edu.modules.program.entities.module

import io.loomus.edu.utils.BaseEntity
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "module")
class ModuleEntity(

    @Column(name = "course_id")
    val courseId: Int = 0,

    @Column(name = "`index`")
    var index: Int = 1,

    @Column
    var name: String = "",

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now()

) : BaseEntity()