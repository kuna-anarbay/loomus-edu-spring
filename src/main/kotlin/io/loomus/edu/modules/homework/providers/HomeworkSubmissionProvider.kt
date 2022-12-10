package io.loomus.edu.modules.homework.providers

import io.loomus.edu.modules.homework.controllers.homework_submission.responses.HomeworkSubmissionResponse
import io.loomus.edu.modules.homework.entities.homework_submission.HomeworkSubmissionEntity
import io.loomus.edu.modules.homework.entities.homework_submission.HomeworkSubmissionEntityRepository
import io.loomus.edu.modules.homework.entities.homework_submission_resource.HomeworkSubmissionResourceEntityRepository
import io.loomus.edu.modules.student.entities.student.StudentEntityRepository
import io.loomus.edu.security.annotations.is_staff.Staff
import io.loomus.edu.utils.HttpResponse
import io.loomus.edu.utils.PaginateResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class HomeworkSubmissionProvider(
    private val repository: HomeworkSubmissionEntityRepository,
    private val studentRepository: StudentEntityRepository,
    private val resourceRepository: HomeworkSubmissionResourceEntityRepository
) {

    fun findAllByCourseIdAndHomeworkId(
        courseId: Int, homeworkId: Int, status: HomeworkSubmissionEntity.Status?,
        page: Int, size: Int
    ): PaginateResponse<HomeworkSubmissionResponse> {
        val entities =
            repository.findAllByCourseIdAndHomeworkId(
                courseId, homeworkId, status, PageRequest.of(page, size)
            )
        val total = if (page == 0)
            repository.countAllByCourseIdAndHomeworkId(
                courseId, homeworkId, status
            ) else 0
        val students = studentRepository.findAllById(entities.map { it.id.studentId }.toSet())
        val resources =
            resourceRepository.findAllByHomeworkIdAndStudentIds(homeworkId, entities.map { it.id.studentId }.toSet())

        return PaginateResponse(total, entities.mapNotNull { entity ->
            students.firstOrNull { it.id == entity.id.studentId }?.let { student ->
                HomeworkSubmissionResponse(entity, student, resources.filter { it.studentId == student.id })
            }
        })
    }


    fun findByCourseIdAndIdOrNull(
        courseId: Int, homeworkId: Int, studentId: Int
    ): HomeworkSubmissionResponse? {
        val id = HomeworkSubmissionEntity.Id(studentId = studentId, homeworkId = homeworkId)
        val entity = repository.findByIdOrNull(id)
            ?: return null
        val student = studentRepository.findByIdOrNull(studentId)
            ?: throw HttpResponse.notFound()
        val resources = resourceRepository.findAllByHomeworkIdAndStudentId(homeworkId, studentId)

        return HomeworkSubmissionResponse(entity, student, resources)
    }

}