package io.loomus.edu.modules.program.providers

import io.loomus.edu.modules.course.controllers.course_package.responses.PackageResponse
import io.loomus.edu.modules.course.entities.course_package.CoursePackageEntityRepository
import io.loomus.edu.modules.program.controllers.lesson.responses.LessonResponse
import io.loomus.edu.modules.program.controllers.module.responses.ModuleResponse
import io.loomus.edu.modules.program.entities.lesson.LessonEntityRepository
import io.loomus.edu.modules.program.entities.lesson_package_relation.LessonPackageRelationEntityRepository
import io.loomus.edu.modules.program.entities.lesson_student_relation.LessonStudentRelationEntityRepository
import io.loomus.edu.modules.program.entities.module.ModuleEntityRepository
import io.loomus.edu.security.annotations.is_member.Member
import io.loomus.edu.utils.HttpResponse
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ModuleProvider(
    private val repository: ModuleEntityRepository,
    private val packageRepository: CoursePackageEntityRepository,
    private val lessonRepository: LessonEntityRepository,
    private val lessonPackageRelationRepository: LessonPackageRelationEntityRepository,
    private val lessonStudentRelationRepository: LessonStudentRelationEntityRepository
) {

    fun findAllByCourseId(member: Member, courseId: Int): List<ModuleResponse> {
        val entities = repository.findAllByCourseId(courseId)
        val coursePackage = if (member.role == Member.Role.STUDENT)
            packageRepository.findActiveByCourseIdAndStudentId(courseId, member.memberId)
                ?: throw HttpResponse.notFound()
        else null
        val lessons = if (member.role == Member.Role.STUDENT) {
            lessonRepository.findAllByCourseIdAndStudentId(courseId, member.memberId)
        } else lessonRepository.findAllByCourseId(courseId)
        val packages = if (member.role == Member.Role.STUDENT) listOf()
        else packageRepository.findAllActiveByCourseId(courseId)
            .map { PackageResponse(it) }
        val relations = if (member.role == Member.Role.STUDENT) {
            lessonPackageRelationRepository.findAllByCourseIdAndPackageId(courseId, coursePackage?.id ?: -1)
        } else lessonPackageRelationRepository.findAllByCourseId(courseId)
        val studentLessonRelations = if (member.role == Member.Role.STUDENT)
            lessonStudentRelationRepository.findAllByCourseIdAndStudentId(courseId, member.memberId)
        else listOf()

        return entities.mapNotNull { module ->
            val moduleLessons = lessons.toSet().filter { it.moduleId == module.id }
                .map { lesson ->
                    val packageIds = relations.filter { it.id.lessonId == lesson.id }
                        .map { it.id.packageId }
                    val lessonPackages = packages.filter { packageIds.contains(it.id) }
                    val homeworkPassed =
                        studentLessonRelations.firstOrNull { it.id.lessonId == lesson.id }?.homeworkPassed ?: false

                    LessonResponse(
                        entity = lesson,
                        video = null,
                        resources = listOf(),
                        homeworkPassed = homeworkPassed,
                        packages = lessonPackages
                    )
                }.sortedBy { it.index }

            if (moduleLessons.isEmpty() && member.role == Member.Role.STUDENT) null
            else ModuleResponse(module, moduleLessons)
        }
    }

    fun findByCourseIdAndId(courseId: Int, moduleId: Int) =
        repository.findByCourseIdAndId(courseId, moduleId)?.let {
            ModuleResponse(it)
        } ?: throw HttpResponse.notFound()

}