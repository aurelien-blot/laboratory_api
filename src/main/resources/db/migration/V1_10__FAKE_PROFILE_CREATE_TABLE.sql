CREATE TABLE image_type
(
    id                     INT AUTO_INCREMENT PRIMARY KEY,
    creation_time          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modification_time      TIMESTAMP    NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    name                   VARCHAR(255) NOT NULL,
    description            TEXT         NULL,
    seed                   BIGINT       NOT NULL,
    subseed                BIGINT       NULL,
    width                  INT          NOT NULL,
    height                 INT          NOT NULL,
    sampler_name           VARCHAR(255) NOT NULL,
    cfg_scale              INT          NOT NULL,
    steps                  INT          NOT NULL,
    restore_faces          BOOLEAN      NOT NULL,
    face_restoration_model VARCHAR(255) NULL,
    sd_model_checkpoint    VARCHAR(255) NOT NULL,
    denoising_strength     DOUBLE       NOT NULL,

    CONSTRAINT unique_image_type_name UNIQUE (name)
);

CREATE TABLE model
(
    id                     INT AUTO_INCREMENT PRIMARY KEY,
    creation_time          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modification_time      TIMESTAMP    NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    name                   VARCHAR(255) NOT NULL,
    description            TEXT         NULL,
    prompt            TEXT         NULL,
    negative_prompt            TEXT         NULL,

    CONSTRAINT unique_model_name UNIQUE (name)
);