create table if not exists course_subscription
(
    course_id          int       not null,
    max_students_count int       not null,
    max_videos_count   int       not null,
    max_resource_size  bigint    not null,
    expires_at         timestamp not null,
    is_deleted         boolean   not null,
    created_at         timestamp not null default current_timestamp,
    updated_at         timestamp not null default current_timestamp on update current_timestamp,
    primary key (course_id)
) engine = INNODB
  default charset = utf8mb4;