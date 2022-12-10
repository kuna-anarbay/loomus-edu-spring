create table if not exists lesson_video
(
    lesson_id  int                                 not null,
    course_id  int                                 not null,
    video_id   varchar(255)                        not null,
    embed_url  varchar(255)                        not null,
    type       enum ('UPLOAD', 'YOUTUBE', 'VIMEO') not null,
    is_active  boolean                             not null,
    is_deleted boolean                             not null,
    created_at timestamp                           not null default current_timestamp,
    updated_at timestamp                           not null default current_timestamp on update current_timestamp,
    primary key (lesson_id),
    index (course_id, lesson_id)
) engine = INNODB
  default charset = utf8mb4;