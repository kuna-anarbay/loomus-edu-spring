create table if not exists lesson_package_relation
(
    lesson_id  int       not null,
    package_id int       not null,
    course_id  int       not null,
    is_deleted boolean   not null,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp on update current_timestamp,
    primary key (lesson_id, package_id),
    index (course_id, package_id, lesson_id)
) engine = INNODB
  default charset = utf8mb4;