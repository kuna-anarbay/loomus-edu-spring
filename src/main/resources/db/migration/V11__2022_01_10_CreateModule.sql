create table if not exists module
(
    id         int auto_increment not null,
    course_id  int                not null,
    `index`    int                not null,
    name       varchar(255)       not null,
    is_deleted boolean            not null,
    created_at timestamp          not null default current_timestamp,
    updated_at timestamp          not null default current_timestamp on update current_timestamp,
    primary key (id),
    index (course_id, `index`)
) engine = INNODB
  default charset = utf8mb4;