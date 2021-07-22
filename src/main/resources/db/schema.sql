CREATE TABLE `users`
(
    `id`         bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `username`   varchar(20)  NOT NULL DEFAULT '',
    `password`   varchar(255) NOT NULL DEFAULT '',
    `email`      varchar(120) NOT NULL DEFAULT '',
    `enabled`    tinyint(1) NOT NULL DEFAULT '0',
    `updated_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UX_USERS_USERNAME` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `authorities`
(
    `id`        bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `authority` varchar(40) NOT NULL DEFAULT '',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `authority_user`
(
    `id`           int(11) unsigned NOT NULL AUTO_INCREMENT,
    `authority_id` bigint(20) NOT NULL,
    `user_id`      bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UX_AUTHORITYUSER_AUTHORITYID_USERID` (`authority_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `confirmation_tokens`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `user_id`      bigint(20) NOT NULL,
    `token`        varchar(32) NOT NULL DEFAULT '',
    `created_at`   timestamp   NOT NULL,
    `expires_at`   timestamp   NOT NULL,
    `confirmed_at` timestamp NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UX_CONFIRMATIONTOKENS_TOKEN` (`token`),
    KEY            `IDX_CONFIRMATIONTOKENS_USERID` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;