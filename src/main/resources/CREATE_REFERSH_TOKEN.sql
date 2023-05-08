CREATE TABLE refresh_token
(
    refresh_token_id bigint   NOT NULL AUTO_INCREMENT COMMENT 'pk',
    refresh_token     bigint   NOT NULL COMMENT '카카오 refreshToken',
    user_id          bigint   NOT NULL COMMENT 'user_id',
    created_at       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    PRIMARY KEY (refresh_token_id)
) ENGINE = InnoDB COMMENT '사용자 정보';