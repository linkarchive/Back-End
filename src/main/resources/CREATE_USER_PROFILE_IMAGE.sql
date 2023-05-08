CREATE TABLE user_profile_image
(
    user_profile_image_id           bigint       NOT NULL AUTO_INCREMENT COMMENT 'pk',
    user_profile_img varchar(255) NOT NULL COMMENT '프로필 사진',
    user_id    bigint  NOT NULL COMMENT '유저 pk',
    created_at        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    updated_at        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정 시간',
    PRIMARY KEY (user_profile_image_id)
) ENGINE = InnoDB COMMENT '사용자 정보';