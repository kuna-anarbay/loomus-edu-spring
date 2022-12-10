package io.loomus.edu.modules.homework.modifiers

import io.loomus.edu.modules.homework.controllers.homework.requests.CreateHomeworkBody
import io.loomus.edu.modules.homework.controllers.homework.responses.HomeworkResponse
import io.loomus.edu.modules.homework.entities.homework.HomeworkEntityRepository
import io.loomus.edu.utils.HttpResponse
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class HomeworkModifier(
    private val repository: HomeworkEntityRepository
) {

    fun createByCourseId(courseId: Int, lessonId: Int, body: CreateHomeworkBody): HomeworkResponse {
        val entity = repository.save(body.toEntity(courseId, lessonId))

        return HomeworkResponse(entity)
    }


    fun deleteByCourseIdAndId(courseId: Int, lessonId: Int): HttpResponse {
        val entity = repository.findActiveByCourseIdAndId(courseId, lessonId)
            ?: throw HttpResponse.notFound()
        entity.isDeleted = true
        repository.save(entity)

        return HttpResponse.success()
    }

}