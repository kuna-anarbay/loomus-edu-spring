package io.loomus.edu.modules.program.modifiers

import io.loomus.edu.modules.program.controllers.module.requests.CreateModuleBody
import io.loomus.edu.modules.program.controllers.module.requests.ReorderModuleBody
import io.loomus.edu.modules.program.controllers.module.responses.ModuleResponse
import io.loomus.edu.modules.program.entities.module.ModuleEntityRepository
import io.loomus.edu.utils.HttpResponse
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import kotlin.math.max
import kotlin.math.min

@Service
@Transactional
class ModuleModifier(
    private val repository: ModuleEntityRepository
) {

    fun createByCourseId(courseId: Int, body: CreateModuleBody): ModuleResponse {
        val index = repository.countByCourseId(courseId).toInt() + 1
        val module = repository.save(body.toEntity(courseId, index))

        return ModuleResponse(module)
    }


    fun editByCourseIdAndId(courseId: Int, moduleId: Int, body: CreateModuleBody): HttpResponse {
        val module = repository.findByCourseIdAndId(courseId, moduleId) ?: throw HttpResponse.notFound()
        module.name = body.name
        repository.save(module)

        return HttpResponse.success()
    }


    fun reorderByCourseIdAndId(courseId: Int, moduleId: Int, body: ReorderModuleBody): HttpResponse {
        val module = repository.findByCourseIdAndId(courseId, moduleId) ?: throw HttpResponse.notFound()
        if (module.index == body.index) return HttpResponse.success()
        val minIndex = min(body.index, module.index)
        val maxIndex = max(body.index, module.index)
        val modules = repository.findAllByCourseIdAndIndexBetween(
            module.courseId,
            minIndex,
            maxIndex
        )

        if (module.index > body.index) {
            modules.forEach { it.index++ }
        } else if (module.index < body.index) {
            modules.forEach { it.index-- }
        }
        modules.firstOrNull { it.id == moduleId }?.index = body.index

        repository.saveAll(modules)

        return HttpResponse.success()
    }


    fun deleteByCourseIdAndId(courseId: Int, moduleId: Int): HttpResponse {
        val module = repository.findByCourseIdAndId(courseId, moduleId) ?: throw HttpResponse.notFound()
        val modules = repository.findAllHigherByCourseIdAndSectionId(module.courseId, module.index)
        modules.forEach { it.index-- }

        repository.saveAll(modules)
        repository.delete(module)

        return HttpResponse.success()
    }

}
