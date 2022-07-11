CREATE SCHEMA IF NOT EXISTS `final_project_test` DEFAULT CHARACTER SET utf8;
USE `final_project_test`;

CREATE TABLE IF NOT EXISTS `final_project_test`.`authors`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(32) NOT NULL,
    `last_name`  VARCHAR(32) NOT NULL,
    `bio`        TEXT DEFAULT NULL
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `final_project_test`.`books`
(
    `id`              BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`            VARCHAR(32) NOT NULL,
    `info`            TEXT    DEFAULT NULL,
    `release_date`    DATE        NOT NULL,
    `age_limitations` TINYINT DEFAULT NULL,
    `cover_photo`     BLOB    DEFAULT NULL
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `final_project_test`.`genres`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(32) NOT NULL UNIQUE
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `final_project_test`.`users`
(
    `id`           BIGINT                              NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `password`     VARCHAR(32)                         NOT NULL,
    `first_name`   VARCHAR(32)                         NOT NULL,
    `last_name`    VARCHAR(32)                         NOT NULL,
    `email`        VARCHAR(32)                         NOT NULL UNIQUE,
    `birthdate`    DATE                                NOT NULL,
    `login`        VARCHAR(32)                         NOT NULL UNIQUE,
    `is_blocked`   TINYINT                                      DEFAULT 0,
    `role`         ENUM ('USER', 'LIBRARIAN', 'ADMIN') NOT NULL DEFAULT 'USER',
    `days_balance` INT                                 NOT NULL DEFAULT 0
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `final_project_test`.`books_authors`
(
    `id`        BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `author_id` BIGINT NOT NULL,
    `book_id`   BIGINT NOT NULL
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `final_project_test`.`books_genres`
(
    `id`       BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `genre_id` BIGINT NOT NULL,
    `book_id`  BIGINT NOT NULL
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `final_project_test`.`library_items`
(
    `id`      BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT DEFAULT NULL,
    `book_id` BIGINT NOT NULL
)
    ENGINE = InnoDB;