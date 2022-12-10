package io.loomus.edu.modules.homework.providers

import io.loomus.edu.modules.homework.controllers.homework.responses.HomeworkResponse
import io.loomus.edu.modules.homework.entities.homework.HomeworkEntityRepository
import io.loomus.edu.modules.homework.entities.homework_resource.HomeworkResourceEntityRepository
import io.loomus.edu.security.annotations.is_member.Member
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class HomeworkProvider(
    private val repository: HomeworkEntityRepository,
    private val homeworkSubmissionProvider: HomeworkSubmissionProvider,
    private val resourceRepository: HomeworkResourceEntityRepository
) {

    fun findAllActiveByCourseIdAndIdOrNull(
        member: Member,
        courseId: Int,
        homeworkId: Int
    ): HomeworkResponse? {
        val entity = repository.findActiveByCourseIdAndId(courseId, homeworkId)
            ?: return null
        val submission = if (member.role == Member.Role.STUDENT)
            homeworkSubmissionProvider.findByCourseIdAndIdOrNull(courseId, homeworkId, member.memberId)
        else null
        val resources = resourceRepository.findAllByCourseIdAndHomeworkId(courseId, homeworkId)

        return HomeworkResponse(entity, resources, submission)
    }

}