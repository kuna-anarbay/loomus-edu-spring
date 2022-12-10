package io.loomus.edu.modules.course.entities.course_banner

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseBannerEntityRepository : JpaRepository<CourseBannerEntity, Int> {
}