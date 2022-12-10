create table if not exists course
(
    id          int auto_increment not null,
    username    varchar(63) unique not null,
    name        varchar(255)       not null,
    description varchar(2047),
    is_active   boolean            not null,
    is_deleted  boolean            not null,
    created_at  timestamp          not null default current_timestamp,
    updated_at  timestamp          not null default current_timestamp on update current_timestamp,
    primary key (id)
) engine = INNODB
  default charset = utf8mb4;