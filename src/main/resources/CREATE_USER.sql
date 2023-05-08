CREATE TABLE user
(
    user_id           bigint       NOT NULL AUTO_INCREMENT COMMENT 'pk',
    social_id          bigint       NOT NULL COMMENT '카카오 소셜 아이디',
    user_name    varchar(20)  NOT NULL COMMENT '사용자 이름',
    email       varchar(20)  NOT NULL COMMENT '이메일',
    user_introduce         text,
    user_role         varchar(20)  NOT NULL COMMENT '유저 권한',
    created_at        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    updated_at        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정 시간',
    PRIMARY KEY (user_id)
) ENGINE = InnoDB COMMENT '사용자 정보';