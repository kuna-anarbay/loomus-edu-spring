create table if not exists sms_verification
(
    id           int auto_increment  not null,
    phone        varchar(255) unique not null,
    codes        varchar(255)        not null,
    created_at   timestamp           not null default current_timestamp,
    last_send_at timestamp           not null,
    primary key (id)
) engine = INNODB
  default charset = utf8mb4;