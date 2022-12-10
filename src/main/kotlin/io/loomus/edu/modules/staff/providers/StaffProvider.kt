package io.loomus.edu.modules.staff.providers

import io.loomus.edu.modules.staff.controllers.staff.responses.StaffResponse
import io.loomus.edu.modules.staff.entities.staff.StaffEntityRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class StaffProvider(
    private val repository: StaffEntityRepository
) {

    fun findAllActiveByCourseId(courseId: Int) =
        repository.findAllActiveByCourseId(courseId, null)
            .map { entity ->
                StaffResponse(entity)
            }

}