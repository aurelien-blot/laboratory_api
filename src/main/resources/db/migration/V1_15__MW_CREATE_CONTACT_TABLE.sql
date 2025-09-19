CREATE TABLE contact
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    creation_time     TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modification_time TIMESTAMP   NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    user_id           INT         NOT NULL,
    contact_id        INT         NOT NULL,
    contact_status    VARCHAR(15) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (contact_id) REFERENCES user (id),
    UNIQUE (user_id, contact_id)
)