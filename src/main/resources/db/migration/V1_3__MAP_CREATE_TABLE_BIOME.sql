CREATE TABLE biome
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    creation_time     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modification_time TIMESTAMP    NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    technical_name    VARCHAR(255) NOT NULL,
    name              VARCHAR(255) NOT NULL,
    ordre             INT          NULL,
    level             INT          NULL,
    color             VARCHAR(50)  NOT NULL
);

INSERT INTO biome (technical_name, name, color, ordre, level)
VALUES ('ocean', 'Océan', '#03224C', 100, 0),
       ('plain', 'Plaine', '#57D53B', 200, 0),
       ('forest', 'Fôret', '#095228', 300, 0),
       ('desert', 'Désert', '#E0CDA9', 400, 0),
       ('mountain', 'Montage', '#848484', 500, 3000);