create table if not exists course_banner
(
    course_id  int          not null,
    version    int          not null,
    path       varchar(255) not null,
    created_at timestamp    not null default current_timestamp,
    updated_at timestamp    not null default current_timestamp on update current_timestamp,
    primary key (course_id)
) engine = INNODB
  default charset = utf8mb4;