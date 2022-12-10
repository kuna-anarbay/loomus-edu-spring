create table if not exists homework_submission
(
    homework_id  int                                      not null,
    student_id   int                                      not null,
    course_id    int                                      not null,
    value        text,
    status       enum ('PENDING', 'ACCEPTED', 'DECLINED') not null,
    tries_count  int                                      not null,
    notes        varchar(1023),
    submitted_at timestamp                                not null,
    is_deleted   boolean                                  not null,
    created_at   timestamp                                not null default current_timestamp,
    updated_at   timestamp                                not null default current_timestamp on update current_timestamp,
    primary key (homework_id, student_id),
    index (course_id, homework_id, student_id),
    index (course_id, student_id)
) engine = INNODB
  default charset = utf8mb4;