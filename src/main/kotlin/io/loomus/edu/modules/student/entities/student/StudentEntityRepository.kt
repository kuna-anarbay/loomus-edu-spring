package io.loomus.edu.modules.student.entities.student

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface StudentEntityRepository : JpaRepository<StudentEntity, Int> {


    /**
     *  Checkers
     */
    fun existsByCourseIdAndPhone(courseId: Int, phone: String): Boolean


    fun existsByCourseIdAndPhoneIn(courseId: Int, phone: Set<String>): Boolean


    /**
     *  Finders
     */
    fun findByCourseIdAndId(courseId: Int, id: Int): StudentEntity?


    @Query(
        "select distinct (s) from student s " +
                "inner join user u on u.phone = s.phone and u.id = :userId " +
                "where s.isDeleted = false and s.isActive = true"
    )
    fun findAllByUserId(userId: Int): List<StudentEntity>


    @Query(
        "select s from student s where s.courseId = :courseId and s.isDeleted = false " +
                "and (:query is null or lower(s.firstName) like :query or lower(s.phone) like :query) " +
                "and (:packageId is null or s.packageId = :packageId) " +
                "and (:isActive is null or s.isActive = :isActive) "
    )
    fun findAllActiveByCourseIdAndQuery(
        courseId: Int,
        query: String?,
        packageId: Int?,
        isActive: Boolean?,
        pageable: Pageable
    ): List<StudentEntity>


    @Query(
        "select s from student s " +
                "inner join user u on u.phone = s.phone and u.id = :userId " +
                "where s.isDeleted = false and s.isActive = true"
    )
    fun findStudentRolesByUserId(userId: Int): List<StudentEntity>


    /**
     *  Counters
     */
    @Query(
        "select count(s) from student s where s.courseId = :courseId and s.isDeleted = false " +
                "and (:query is null or lower(s.firstName) like :query or lower(s.phone) like :query) " +
                "and (:packageId is null or s.packageId = :packageId) " +
                "and (:isActive is null or s.isActive = :isActive) "
    )
    fun countAllActiveByCourseIdAndQuery(
        courseId: Int, query: String?, packageId: Int?, isActive: Boolean?
    ): Long


    @Query("select count(s.id) from student s where s.courseId = :courseId and s.isDeleted = false and s.isActive = true")
    fun countAllLearningByCourseId(courseId: Int): Long

}