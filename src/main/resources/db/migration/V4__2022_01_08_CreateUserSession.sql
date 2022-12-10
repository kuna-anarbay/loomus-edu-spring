create table if not exists user_session
(
    id             int auto_increment        not null,
    user_id        int                       not null,
    is_active      boolean                   not null,
    ip_address     varchar(255)              not null,
    device_type    varchar(255)              not null,
    os             varchar(255)              not null,
    platform       enum ('PHONE', 'DESKTOP') not null,
    version        varchar(255)              not null,
    last_active_at timestamp                 not null,
    created_at     timestamp                 not null default current_timestamp,
    primary key (id),
    index (user_id)
) engine = INNODB
  default charset = utf8mb4;