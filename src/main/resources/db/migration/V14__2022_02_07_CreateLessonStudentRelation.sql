create table if not exists lesson_student_relation
(
    lesson_id       int       not null,
    student_id      int       not null,
    course_id       int       not null,
    homework_passed boolean   not null,
    is_deleted      boolean   not null,
    created_at      timestamp not null default current_timestamp,
    updated_at      timestamp not null default current_timestamp on update current_timestamp,
    primary key (lesson_id, student_id),
    index (course_id, student_id, lesson_id)
) engine = INNODB
  default charset = utf8mb4;