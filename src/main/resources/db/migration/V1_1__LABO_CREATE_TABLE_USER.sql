CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modification_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    mail_verified TINYINT NOT NULL DEFAULT 0,
    mail_verification_token VARCHAR(255) NULL,
    reset_password_token VARCHAR(255) NULL,
    last_verification_mail_date TIMESTAMP DEFAULT NULL,
    blocked BOOLEAN DEFAULT FALSE NOT NULL,
    tentatives INT DEFAULT 0 NOT NULL
);

ALTER TABLE user ADD UNIQUE (email);
ALTER TABLE user ADD UNIQUE (username);