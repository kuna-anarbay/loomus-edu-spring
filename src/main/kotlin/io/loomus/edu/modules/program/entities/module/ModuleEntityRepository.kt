package io.loomus.edu.modules.program.entities.module

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ModuleEntityRepository : JpaRepository<ModuleEntity, Int> {

    fun findAllByCourseId(courseId: Int): List<ModuleEntity>


    fun findByCourseIdAndId(courseId: Int, id: Int): ModuleEntity?


    fun countByCourseId(courseId: Int): Long


    @Query("select m from module m where m.courseId = :courseId  and m.index > :index")
    fun findAllHigherByCourseIdAndSectionId(courseId: Int, index: Int): List<ModuleEntity>


    @Query("select m from module m where m.courseId = :courseId and m.index >= :from and m.index <= :to")
    fun findAllByCourseIdAndIndexBetween(courseId: Int, from: Int, to: Int): List<ModuleEntity>

}