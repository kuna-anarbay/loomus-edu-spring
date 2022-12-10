create table if not exists homework
(
    id          int       not null,
    course_id   int       not null,
    value       text      not null,
    deadline_at timestamp,
    is_deleted  boolean   not null,
    created_at  timestamp not null default current_timestamp,
    updated_at  timestamp not null default current_timestamp on update current_timestamp,
    primary key (id)
) engine = INNODB
  default charset = utf8mb4;