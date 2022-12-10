create table if not exists user
(
    id         int auto_increment                    not null,
    phone      varchar(255) unique                   not null,
    password   varchar(255)                          not null,
    first_name varchar(255)                          not null,
    last_name  varchar(255),
    gender     enum ('MALE', 'FEMALE'),
    birthday   int,
    language   enum ('KK', 'RU', 'EN')               not null,
    status     enum ('ACTIVE', 'DELETED', 'BLOCKED') not null,
    created_at timestamp                             not null default current_timestamp,
    updated_at timestamp                             not null default current_timestamp on update current_timestamp,
    primary key (id),
    index (phone)
) engine = INNODB
  default charset = utf8mb4;