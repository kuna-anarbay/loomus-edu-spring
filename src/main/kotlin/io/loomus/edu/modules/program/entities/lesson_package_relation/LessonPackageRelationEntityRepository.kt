package io.loomus.edu.modules.program.entities.lesson_package_relation

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface LessonPackageRelationEntityRepository :
    JpaRepository<LessonPackageRelationEntity, LessonPackageRelationEntity.Id> {

    interface CountDto {
        val packageId: Int
        val count: Long
    }

    @Query("select r from lesson_package_relation r where r.courseId = :courseId and r.id.packageId = :packageId and r.isDeleted = false")
    fun findAllByCourseIdAndPackageId(courseId: Int, packageId: Int): List<LessonPackageRelationEntity>


    @Query("select r from lesson_package_relation r where r.courseId = :courseId and r.isDeleted = false")
    fun findAllByCourseId(courseId: Int): List<LessonPackageRelationEntity>


    @Query(
        "select r.package_id as packageId, count(r.lesson_id) as count from lesson_package_relation r " +
                "where r.course_id = :courseId and r.is_deleted = false " +
                "group by r.package_id", nativeQuery = true
    )
    fun countAllByCourseId(courseId: Int): List<CountDto>

}