package io.loomus.edu.modules.program.entities.lesson_resource

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LessonResourceEntityRepository : JpaRepository<LessonResourceEntity, Int> {

    @Query(
        "select r from lesson_resource r where r.courseId = :courseId and " +
                "r.lessonId = :lessonId and r.id = :id and r.isActive = true"
    )
    fun findByCourseIdAndLessonIdAndId(courseId: Int, lessonId: Int, id: Int): LessonResourceEntity?


    @Query(
        "select r from lesson_resource r where r.courseId = :courseId and " +
                "r.lessonId = :lessonId and r.isActive = true"
    )
    fun findAllByCourseIdAndLessonId(courseId: Int, lessonId: Int): List<LessonResourceEntity>


    @Query(
        "select r from lesson_resource r where r.courseId = :courseId and " +
                "r.lessonId in :lessonIds and r.isActive = true"
    )
    fun findAllByCourseIdAndLessonIds(courseId: Int, lessonIds: Set<Int>): List<LessonResourceEntity>


    @Query("select sum(r.size) from lesson_resource r where r.isActive = true and r.courseId = :courseId and r.isDeleted = false")
    fun sumSizeByCourseId(courseId: Int): Long?

}