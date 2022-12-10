create table if not exists student
(
    id             int auto_increment not null,
    course_id      int                not null,
    package_id     int                not null,
    first_name     varchar(255)       not null,
    last_name      varchar(255),
    phone          varchar(255)       not null,
    is_active      boolean            not null,
    last_opened_at timestamp,
    is_deleted     boolean            not null,
    created_at     timestamp          not null default current_timestamp,
    updated_at     timestamp          not null default current_timestamp on update current_timestamp,
    primary key (id),
    index (phone),
    index (course_id, phone)
) engine = INNODB
  default charset = utf8mb4;