CREATE TABLE post
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    creation_time     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modification_time TIMESTAMP    NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    creation_by_id    INT          NOT NULL,
    content    Text NULL,
    FOREIGN KEY (creation_by_id) REFERENCES user (id)
);