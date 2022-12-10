package io.loomus.edu.modules.course.providers

import io.loomus.edu.modules.course.entities.course_banner.CourseBannerEntityRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class CourseBannerProvider(
    private val repository: CourseBannerEntityRepository
) {

}