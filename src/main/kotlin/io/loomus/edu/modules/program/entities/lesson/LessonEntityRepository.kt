package io.loomus.edu.modules.program.entities.lesson

import io.loomus.edu.modules.program.entities.lesson_student_relation.LessonStudentRelationEntityRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface LessonEntityRepository : JpaRepository<LessonEntity, Int> {

    interface ProgressDto {
        val packageId: Int
        val courseId: Int
        val count: Long
    }

    @Query("select l from lesson l where l.courseId = :courseId and l.isDeleted = false")
    fun findAllByCourseId(courseId: Int): List<LessonEntity>


    fun findByCourseIdAndId(courseId: Int, id: Int): LessonEntity?


    fun countByCourseIdAndModuleId(courseId: Int, moduleId: Int): Long


    @Query(
        "select distinct (l) from lesson l " +
                "inner join student s on s.id = :studentId and s.isDeleted = false and s.isActive = true " +
                "inner join lesson_package_relation r on r.id.packageId = s.packageId and r.id.lessonId = l.id and r.isDeleted = false " +
                "where l.courseId = :courseId and l.isDeleted = false " +
                "and l.status <> 'DRAFT' "
    )
    fun findAllByCourseIdAndStudentId(courseId: Int, studentId: Int): List<LessonEntity>


    @Query("select c from lesson c where c.courseId = :courseId and c.index > :index")
    fun findAllHigherByCourseId(courseId: Int, index: Int): List<LessonEntity>


    @Query("select c from lesson c where c.courseId = :courseId and c.moduleId = :moduleId and c.index >= :from and c.index <= :to")
    fun findAllByCourseIdAndModuleIdAndIndexBetween(
        courseId: Int,
        moduleId: Int,
        from: Int,
        to: Int
    ): List<LessonEntity>


    @Query(
        "select l.course_id as courseId, lpr.package_id as packageId, " +
                "count(l.id) as count from lesson l " +
                "inner join lesson_package_relation lpr on lpr.lesson_id = l.id and lpr.package_id in :packageIds and lpr.is_deleted = false " +
                "where l.is_deleted = false " +
                "group by l.course_id, lpr.package_id", nativeQuery = true
    )
    fun findLessonsCountByPackageIds(packageIds: Set<Int>): List<ProgressDto>

}