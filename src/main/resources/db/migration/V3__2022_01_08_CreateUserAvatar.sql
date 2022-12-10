create table if not exists user_avatar
(
    user_id    int          not null,
    path       varchar(255) not null,
    version    int          not null,
    created_at timestamp    not null default current_timestamp,
    updated_at timestamp    not null default current_timestamp on update current_timestamp,
    primary key (user_id)
) engine = INNODB
  default charset = utf8mb4;