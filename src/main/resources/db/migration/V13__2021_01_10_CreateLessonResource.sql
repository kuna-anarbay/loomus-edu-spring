create table if not exists lesson_resource
(
    id         int auto_increment not null,
    course_id  int                not null,
    lesson_id  int                not null,
    is_active  boolean            not null,
    path       varchar(255)       not null,
    name       varchar(255)       not null,
    size       bigint             not null,
    is_deleted boolean            not null,
    created_at timestamp          not null default current_timestamp,
    updated_at timestamp          not null default current_timestamp on update current_timestamp,
    primary key (id),
    index (course_id, lesson_id)
) engine = INNODB
  default charset = utf8mb4;