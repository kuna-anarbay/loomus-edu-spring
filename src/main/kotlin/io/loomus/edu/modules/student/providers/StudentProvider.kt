package io.loomus.edu.modules.student.providers

import io.loomus.edu.extensions.nullIfNeeded
import io.loomus.edu.extensions.searchQuery
import io.loomus.edu.modules.course.entities.course_package.CoursePackageEntityRepository
import io.loomus.edu.modules.program.entities.lesson.LessonEntityRepository
import io.loomus.edu.modules.program.entities.lesson_student_relation.LessonStudentRelationEntityRepository
import io.loomus.edu.modules.student.controllers.student.responses.StudentResponse
import io.loomus.edu.modules.student.entities.student.StudentEntityRepository
import io.loomus.edu.utils.PaginateResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class StudentProvider(
    private val repository: StudentEntityRepository,
    private val packageRepository: CoursePackageEntityRepository,
    private val lessonStudentRelationRepository: LessonStudentRelationEntityRepository,
    private val lessonRepository: LessonEntityRepository
) {

    fun findAllActiveByCourseId(
        courseId: Int,
        size: Int,
        page: Int,
        query: String?,
        packageId: Int?,
        isActive: Boolean?,
        orderBy: String,
        orderDirection: String
    ): PaginateResponse<StudentResponse> {
        val sort = Sort.by(Sort.Direction.fromString(orderDirection), orderBy)
        val entities = repository.findAllActiveByCourseIdAndQuery(
            courseId, query?.nullIfNeeded?.searchQuery, packageId, isActive,
            PageRequest.of(page, size, sort)
        )
        val total = if (page == 0) repository.countAllActiveByCourseIdAndQuery(
            courseId, query?.nullIfNeeded?.searchQuery, packageId, isActive
        ) else 0
        val packages = packageRepository.findAllById(entities.map { it.packageId }.toSet())
        val studentProgress = lessonStudentRelationRepository.findProgressByStudentIds(entities.map { it.id }.toSet())
        val lessonsCount = lessonRepository.findLessonsCountByPackageIds(packages.map { it.id }.toSet())

        return PaginateResponse(total, entities.map { entity ->
            val progress = StudentResponse.Progress(
                opened = studentProgress.firstOrNull { it.studentId == entity.id }?.opened ?: 0,
                passed = studentProgress.firstOrNull { it.studentId == entity.id }?.passed ?: 0,
                total = lessonsCount.firstOrNull { it.packageId == entity.packageId }?.count?.toInt() ?: 0
            )
            StudentResponse(
                entity,
                progress
            )
        })
    }

}