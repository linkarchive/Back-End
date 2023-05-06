CREATE TABLE user_master
(
    user_code        bigint       NOT NULL AUTO_INCREMENT COMMENT 'pk',
    kakao_id      bigint  NOT NULL COMMENT '카카오 소셜 아이디',
    kakao_profile_img       varchar(255)  NOT NULL COMMENT '프로필 사진',
    kakao_nickname       varchar(20)  NOT NULL COMMENT '닉네임',
    kakao_email varchar(20) NOT NULL COMMENT '이메일',
    user_role      varchar(20)     NOT NULL COMMENT '유저 권한',
    created_at     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    PRIMARY KEY (user_code)
) ENGINE = InnoDB COMMENT '사용자 정보';