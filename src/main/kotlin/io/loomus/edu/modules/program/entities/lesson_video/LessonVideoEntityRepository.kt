package io.loomus.edu.modules.program.entities.lesson_video

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LessonVideoEntityRepository : JpaRepository<LessonVideoEntity, Int> {

    @Query(
        "select r from lesson_video r where r.courseId = :courseId and " +
                "r.lessonId = :lessonId and r.isDeleted = false and r.isActive = true"
    )
    fun findByCourseIdAndLessonId(courseId: Int, lessonId: Int): LessonVideoEntity?


    @Query(
        "select count(r) from lesson_video r where r.courseId = :courseId and " +
                "r.isActive = true and r.type = 'UPLOAD' and r.isDeleted = false"
    )
    fun countAllByCourseId(courseId: Int): Long


}