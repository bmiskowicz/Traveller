CREATE TABLE profiles.image
(
    image_id BIGINT       NOT NULL,
    src      VARCHAR(255) NOT NULL,
    alt      VARCHAR(255) NOT NULL,
    post_id  BIGINT       NOT NULL,
    CONSTRAINT pk_image PRIMARY KEY (image_id)
);

CREATE TABLE profiles.post
(
    post_id    BIGINT       NOT NULL,
    name       VARCHAR(255) NOT NULL,
    content    VARCHAR(255) NOT NULL,
    profile_id BIGINT       NOT NULL,
    CONSTRAINT pk_post PRIMARY KEY (post_id)
);

ALTER TABLE profiles.post
    ADD CONSTRAINT uc_post_name UNIQUE (name);

ALTER TABLE profiles.image
    ADD CONSTRAINT FK_IMAGE_ON_IMAGEID FOREIGN KEY (image_id) REFERENCES profiles.post (post_id);

ALTER TABLE profiles.image
    ADD CONSTRAINT FK_IMAGE_ON_POSTID FOREIGN KEY (post_id) REFERENCES profiles.post (post_id);

ALTER TABLE profiles.post
    ADD CONSTRAINT FK_POST_ON_PROFILEID FOREIGN KEY (profile_id) REFERENCES profiles.profile (profile_id);