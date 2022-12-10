package io.loomus.edu.modules.course.entities.course_package

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CoursePackageEntityRepository : JpaRepository<CoursePackageEntity, Int> {

    fun findByCourseIdAndId(courseId: Int, id: Int): CoursePackageEntity?


    @Query("select p from package p where p.courseId = :courseId and p.isDeleted = false")
    fun findAllActiveByCourseId(courseId: Int): List<CoursePackageEntity>


    @Query(
        "select p from package p " +
                "inner join lesson_package_relation plr on plr.id.packageId = p.id and plr.id.lessonId = :lessonId and plr.isDeleted = false " +
                "where p.courseId = :courseId and p.isDeleted = false"
    )
    fun findAllActiveByCourseIdAndLessonId(courseId: Int, lessonId: Int): List<CoursePackageEntity>


    @Query(
        "select p from package p " +
                "inner join student s on s.id = :studentId and s.packageId = p.id " +
                "where p.courseId = :courseId and p.isDeleted = false"
    )
    fun findActiveByCourseIdAndStudentId(courseId: Int, studentId: Int): CoursePackageEntity?

}