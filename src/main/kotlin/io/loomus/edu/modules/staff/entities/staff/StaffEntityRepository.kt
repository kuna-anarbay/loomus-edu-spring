package io.loomus.edu.modules.staff.entities.staff

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface StaffEntityRepository : JpaRepository<StaffEntity, Int> {

    /**
     *  Checkers
     */
    fun existsByCourseIdAndPhone(courseId: Int, email: String): Boolean


    /**
     *  Finders
     */
    @Query(
        "select s from staff s " +
                "inner join user u on u.phone = s.phone and u.id = :userId " +
                "where s.isDeleted = false"
    )
    fun findAllByUserId(userId: Int): List<StaffEntity>


    @Query(
        "select s from staff s " +
                "where s.courseId = :courseId and s.isDeleted = false " +
                "and (:role is null or s.role = :role)"
    )
    fun findAllActiveByCourseId(courseId: Int, role: StaffEntity.Role?): List<StaffEntity>


    @Query(
        "select s from staff s " +
                "where s.role = 'OWNER' and s.courseId in :courseIds and s.isDeleted = false "
    )
    fun findAllOwnersActiveByCourseIds(courseIds: Set<Int>): List<StaffEntity>


    @Query("select s from staff s where s.id = :staffId and s.courseId = :courseId and s.isDeleted = false")
    fun findActiveByCourseIdAndId(courseId: Int, staffId: Int): StaffEntity?


    @Query(
        "select s from staff s " +
                "inner join user u on u.phone = s.phone and u.id = :userId " +
                "where s.isDeleted = false"
    )
    fun findStaffRolesByUserId(userId: Int): List<StaffEntity>

}