CREATE TABLE picture
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    creation_time     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modification_time TIMESTAMP    NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    filename VARCHAR(255) NOT NULL,
    extension VARCHAR(10) NOT NULL,
    original_size DOUBLE NOT NULL,
    resized_file_filepath VARCHAR(255) NOT NULL,
    original_filepath VARCHAR(255) NOT NULL,
    post_id INT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post (id)
);