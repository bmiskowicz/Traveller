CREATE TABLE profiles.image
(
    image_id     BIGINT       NOT NULL,
    data         TEXT         NOT NULL,
    format       VARCHAR(255) NOT NULL,
    name         VARCHAR(255) NOT NULL,
    post_post_id BIGINT,
    CONSTRAINT pk_image PRIMARY KEY (image_id)
);

ALTER TABLE profiles.image
    ADD CONSTRAINT FK_IMAGE_ON_POST_POSTID FOREIGN KEY (post_post_id) REFERENCES profiles.post (post_id);