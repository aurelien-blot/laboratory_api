CREATE TABLE map (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      modification_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
);