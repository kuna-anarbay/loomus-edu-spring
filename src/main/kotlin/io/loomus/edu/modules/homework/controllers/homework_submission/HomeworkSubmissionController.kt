package io.loomus.edu.modules.homework.controllers.homework_submission

import io.loomus.edu.modules.homework.controllers.homework_submission.requests.CreateHomeworkSubmissionBody
import io.loomus.edu.modules.homework.controllers.homework_submission.requests.ReplyHomeworkSubmissionBody
import io.loomus.edu.modules.homework.entities.homework_submission.HomeworkSubmissionEntity
import io.loomus.edu.modules.homework.modifiers.HomeworkSubmissionModifier
import io.loomus.edu.modules.homework.providers.HomeworkSubmissionProvider
import io.loomus.edu.security.annotations.is_staff.IsStaff
import io.loomus.edu.security.annotations.is_staff.Staff
import io.loomus.edu.security.annotations.is_student.IsStudent
import io.loomus.edu.security.annotations.is_student.Student
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid
import javax.validation.constraints.Max

@RestController
@RequestMapping("/v1/course/{courseId}/homework/{homeworkId}/submission")
class HomeworkSubmissionController(
    private val modifier: HomeworkSubmissionModifier,
    private val provider: HomeworkSubmissionProvider
) {

    @GetMapping
    fun findAllByCourseIdAndHomeworkId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @PathVariable homeworkId: String,
        @RequestParam status: HomeworkSubmissionEntity.Status?,
        @RequestParam(defaultValue = "20") @Max(100) size: Int,
        @RequestParam(defaultValue = "0") page: Int
    ) = provider.findAllByCourseIdAndHomeworkId(courseId.toInt(), homeworkId.toInt(), status, page, size)


    @PostMapping
    fun createByCourseId(
        @IsStudent student: Student,
        @PathVariable courseId: String,
        @PathVariable homeworkId: String,
        @Valid @RequestBody body: CreateHomeworkSubmissionBody
    ) = modifier.createByCourseId(courseId.toInt(), homeworkId.toInt(), student.id, body)


    @PutMapping("{submissionId}")
    fun replyByCourseIdAndId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @PathVariable homeworkId: String,
        @PathVariable submissionId: String,
        @Valid @RequestBody body: ReplyHomeworkSubmissionBody
    ) = modifier.replyByCourseIdAndId(staff, courseId.toInt(), homeworkId.toInt(), submissionId.toInt(), body)


    @DeleteMapping("{submissionId}")
    fun deleteByCourseIdAndId(
        @IsStaff staff: Staff,
        @PathVariable courseId: String,
        @PathVariable homeworkId: String,
        @PathVariable submissionId: String
    ) = modifier.deleteByCourseIdAndId(courseId.toInt(), homeworkId.toInt(), submissionId.toInt())

}