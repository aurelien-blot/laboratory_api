CREATE TABLE generated_image
(
    id                                INT AUTO_INCREMENT PRIMARY KEY,
    creation_time                     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modification_time                 TIMESTAMP NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    template_title                     VARCHAR(255),
    seed_used                          BIGINT,
    subseed_used                       BIGINT,
    file_path                          VARCHAR(255),
    prompt                            VARCHAR(255),
    negative_prompt                    VARCHAR(255),
    styles                            VARCHAR(255),
    subseed_strength                   INT,
    seed_resize_from_h                 INT,
    seed_resize_from_w                 INT,
    sampler_name                       VARCHAR(255),
    scheduler                          VARCHAR(255),
    batch_size                         INT,
    n_iter                             INT,
    steps                              INT,
    cfg_scale                          INT,
    width                              INT,
    height                             INT,
    restore_faces                      BOOLEAN,
    tiling                             BOOLEAN,
    do_not_save_samples                BOOLEAN,
    do_not_save_grid                   BOOLEAN,
    eta                                INT,
    denoising_strength                 INT,
    s_min_uncond                       INT,
    s_churn                            INT,
    s_tmax                             INT,
    s_tmin                             INT,
    s_noise                            INT,
    override_settings_restore_afterwards BOOLEAN,
    refiner_checkpoint                 VARCHAR(255),
    refiner_switch_at                   INT,
    disable_extra_networks              BOOLEAN,
    firstpass_image                    VARCHAR(255),
    enable_hr                          BOOLEAN,
    firstphase_width                   INT,
    firstphase_height                  INT,
    hr_scale                           INT,
    hr_upscaler                        VARCHAR(255),
    hr_second_pass_steps               INT,
    hr_resize_x                        INT,
    hr_resize_y                        INT,
    hr_checkpoint_name                  VARCHAR(255),
    hr_sampler_name                     VARCHAR(255),
    hr_scheduler                       VARCHAR(255),
    hr_prompt                          VARCHAR(255),
    hr_negative_prompt                  VARCHAR(255),
    force_task_id                       VARCHAR(255),
    sampler_index                      VARCHAR(255),
    script_name                        VARCHAR(255),
    script_args                        VARCHAR(255),
    send_images                        BOOLEAN,
    save_images                        BOOLEAN,
    infotext                          VARCHAR(255)
);