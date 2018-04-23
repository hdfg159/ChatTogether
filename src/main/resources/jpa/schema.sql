drop table if exists user;
drop table if exists user_account_state;
drop table if exists user_permission;
drop table if exists user_authorization;
drop table if exists user_profile;
drop table if exists user_friend;
drop table if exists message;
drop table if exists message_attachment;
drop table if exists message_notification;
drop table if exists microword;
drop table if exists microword_agree;
drop table if exists microword_comment;
drop table if exists microword_attachment;
drop table if exists microword_comment_reply;
drop table if exists microword_comment_agree;
drop table if exists suggestion;

CREATE TABLE user
(
  id           BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  gmt_create   DATETIME     NOT NULL,
  gmt_modified DATETIME     NOT NULL,
  password     VARCHAR(255) NOT NULL,
  username     VARCHAR(255) NOT NULL,
  CONSTRAINT UK_sb8bbouer5wak8vyiiy4pf2bx
  UNIQUE (username)
);

CREATE TABLE user_account_state
(
  id           BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  gmt_create   DATETIME NOT NULL,
  is_enabled   INT      NOT NULL,
  gmt_modified DATETIME NOT NULL,
  user_id      BIGINT   NOT NULL,
  CONSTRAINT UK_od24me5m43irdbskad46xcj5k
  UNIQUE (user_id),
  CONSTRAINT FKr3o65acjjmk05mmbj0gb0tsvw
  FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE user_permission
(
  id           BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  gmt_create   DATETIME     NOT NULL,
  gmt_modified DATETIME     NOT NULL,
  role_name    VARCHAR(255) NOT NULL,
  CONSTRAINT UK_p8wfmyb77wr6ql8r45kf8s5d
  UNIQUE (role_name)
);

CREATE TABLE user_authorization
(
  id                 BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  gmt_create         DATETIME NOT NULL,
  gmt_modified       DATETIME NOT NULL,
  user_id            BIGINT   NOT NULL,
  user_permission_id BIGINT   NOT NULL,
  CONSTRAINT UK9ymd43h55rdto0hv5ealwgjun
  UNIQUE (user_id, user_permission_id),
  CONSTRAINT FKmn5m43rc9c5dd6ceu8ixqn6ej
  FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE user_profile
(
  id                BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  age               INT          NULL,
  birthday          DATETIME     NULL,
  gmt_create        DATETIME     NOT NULL,
  email             VARCHAR(255) NULL,
  gmt_modified      DATETIME     NOT NULL,
  phone_number      VARCHAR(255) NULL,
  profile_photo     VARCHAR(255) NULL,
  qq                VARCHAR(255) NULL,
  self_introduction VARCHAR(50)  NULL,
  sex               VARCHAR(255) NULL,
  wechat            VARCHAR(255) NULL,
  user_id           BIGINT       NOT NULL,
  CONSTRAINT UK_ebc21hy5j7scdvcjt0jy6xxrv
  UNIQUE (user_id),
  CONSTRAINT FK6kwj5lk78pnhwor4pgosvb51r
  FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE user_friend
(
  id              BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  gmt_create      DATETIME NOT NULL,
  gmt_modified    DATETIME NOT NULL,
  active_user_id  BIGINT   NOT NULL,
  passive_user_id BIGINT   NOT NULL,
  CONSTRAINT UKopilw54oy62fesrvi62v59bo7
  UNIQUE (active_user_id, passive_user_id),
  CONSTRAINT FKs9rrbyi5pvjic7jnxcje7o4eg
  FOREIGN KEY (active_user_id) REFERENCES user (id),
  CONSTRAINT FKbh4gismitdef373aqkfgtunjv
  FOREIGN KEY (passive_user_id) REFERENCES user (id)
);

CREATE TABLE message
(
  id              BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  content         VARCHAR(5000) NULL,
  gmt_create      DATETIME      NOT NULL,
  is_read         INT           NOT NULL,
  gmt_modified    DATETIME      NOT NULL,
  receive_user_id BIGINT        NOT NULL,
  send_user_id    BIGINT        NOT NULL
);

CREATE TABLE message_attachment
(
  id           BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  gmt_create   DATETIME     NOT NULL,
  gmt_modified DATETIME     NOT NULL,
  url          VARCHAR(255) NOT NULL,
  message_id   BIGINT       NOT NULL,
  CONSTRAINT FKjy2qr5ok9lfr6m7nss2fywqjn
  FOREIGN KEY (message_id) REFERENCES message (id)
);

CREATE TABLE message_notification
(
  id              BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  content         VARCHAR(255) NOT NULL,
  gmt_create      DATETIME     NOT NULL,
  is_read         INT          NOT NULL,
  gmt_modified    DATETIME     NOT NULL,
  type            INT          NOT NULL,
  url             VARCHAR(255) NOT NULL,
  receive_user_id BIGINT       NOT NULL,
  send_user_id    BIGINT       NOT NULL
);

CREATE TABLE microword
(
  id           BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  content      VARCHAR(200) NOT NULL,
  gmt_create   DATETIME     NOT NULL,
  gmt_modified DATETIME     NOT NULL,
  user_id      BIGINT       NOT NULL
);

CREATE TABLE microword_agree
(
  id           BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  gmt_create   DATETIME NOT NULL,
  gmt_modified DATETIME NOT NULL,
  microword_id BIGINT   NOT NULL,
  user_id      BIGINT   NOT NULL,
  CONSTRAINT FKqj58qlaueslpoxk29e2exu6aq
  FOREIGN KEY (microword_id) REFERENCES microword (id)
);

CREATE TABLE microword_attachment
(
  id           BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  gmt_create   DATETIME     NOT NULL,
  gmt_modified DATETIME     NOT NULL,
  url          VARCHAR(255) NOT NULL,
  microword_id BIGINT       NOT NULL,
  CONSTRAINT FKo30qsw53mvvwrnwbocjt3143m
  FOREIGN KEY (microword_id) REFERENCES microword (id)
);

CREATE TABLE microword_comment
(
  id              BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  content         VARCHAR(200) NOT NULL,
  gmt_create      DATETIME     NOT NULL,
  gmt_modified    DATETIME     NOT NULL,
  comment_user_id BIGINT       NOT NULL,
  microword_id    BIGINT       NOT NULL,
  CONSTRAINT FKkl59xcgnppq832i0v9dlqoqcl
  FOREIGN KEY (microword_id) REFERENCES microword (id)
);

CREATE TABLE microword_comment_reply
(
  id                   BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  content              VARCHAR(200) NOT NULL,
  gmt_create           DATETIME     NOT NULL,
  gmt_modified         DATETIME     NOT NULL,
  microword_comment_id BIGINT       NOT NULL,
  replied_user_id      BIGINT       NOT NULL,
  reply_user_id        BIGINT       NOT NULL,
  CONSTRAINT FKr4ecj7cqa4qwo12xj7ceaxo57
  FOREIGN KEY (microword_comment_id) REFERENCES microword_comment (id)
);

CREATE TABLE microword_comment_agree
(
  id                   BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  gmt_create           DATETIME NOT NULL,
  gmt_modified         DATETIME NOT NULL,
  microword_comment_id BIGINT   NOT NULL,
  user_id              BIGINT   NOT NULL,
  CONSTRAINT FKe0wyeaf3r4ltmwm5xjpp01s34
  FOREIGN KEY (microword_comment_id) REFERENCES microword_comment (id)
);

CREATE TABLE suggestion
(
  id           BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  content      VARCHAR(100) NOT NULL,
  gmt_create   DATETIME     NOT NULL,
  gmt_modified DATETIME     NOT NULL,
  title        VARCHAR(20)  NOT NULL,
  type         INT          NOT NULL,
  user_id      BIGINT       NOT NULL
);

CREATE INDEX FK3rg9ilkfcwu7ywo9utiwo4q24
  ON message (send_user_id);

CREATE INDEX FKhgf3w0h82ws01op4d4b8qj4ti
  ON message (receive_user_id);

CREATE INDEX FKjy2qr5ok9lfr6m7nss2fywqjn
  ON message_attachment (message_id);

CREATE INDEX FK28lg4yt67y2x89cf93wp7gsvx
  ON message_notification (receive_user_id);

CREATE INDEX FKmpv9gj5fvwm4r19bdfjb9wrbo
  ON message_notification (send_user_id);

CREATE INDEX FKlgmko70tyv31fghb53gb3d1op
  ON microword (user_id);

CREATE INDEX FK14qd6cq6ydfvn4ccylq0vebbk
  ON microword_agree (user_id);

CREATE INDEX FKqj58qlaueslpoxk29e2exu6aq
  ON microword_agree (microword_id);

CREATE INDEX FKo30qsw53mvvwrnwbocjt3143m
  ON microword_attachment (microword_id);

CREATE INDEX FKdvqt9ems3fcsym0u28o4olscg
  ON microword_comment (comment_user_id);

CREATE INDEX FKkl59xcgnppq832i0v9dlqoqcl
  ON microword_comment (microword_id);

CREATE INDEX FKe0wyeaf3r4ltmwm5xjpp01s34
  ON microword_comment_agree (microword_comment_id);

CREATE INDEX FKs8g7tsy9r5df58x8b2s3roxal
  ON microword_comment_agree (user_id);

CREATE INDEX FKb44k688738ikia8uw9gwgrpwe
  ON microword_comment_reply (replied_user_id);

CREATE INDEX FKqqsgw29rthe2ujad7athdea6f
  ON microword_comment_reply (reply_user_id);

CREATE INDEX FKr4ecj7cqa4qwo12xj7ceaxo57
  ON microword_comment_reply (microword_comment_id);

CREATE INDEX FKnocwtfc34pk1e7upvi1plaedu
  ON suggestion (user_id);

ALTER TABLE message
  ADD CONSTRAINT FKhgf3w0h82ws01op4d4b8qj4ti
FOREIGN KEY (receive_user_id) REFERENCES user (id);

ALTER TABLE message
  ADD CONSTRAINT FK3rg9ilkfcwu7ywo9utiwo4q24
FOREIGN KEY (send_user_id) REFERENCES user (id);

ALTER TABLE message_notification
  ADD CONSTRAINT FK28lg4yt67y2x89cf93wp7gsvx
FOREIGN KEY (receive_user_id) REFERENCES user (id);

ALTER TABLE message_notification
  ADD CONSTRAINT FKmpv9gj5fvwm4r19bdfjb9wrbo
FOREIGN KEY (send_user_id) REFERENCES user (id);

ALTER TABLE microword
  ADD CONSTRAINT FKlgmko70tyv31fghb53gb3d1op
FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE microword_agree
  ADD CONSTRAINT FK14qd6cq6ydfvn4ccylq0vebbk
FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE microword_comment
  ADD CONSTRAINT FKdvqt9ems3fcsym0u28o4olscg
FOREIGN KEY (comment_user_id) REFERENCES user (id);

ALTER TABLE microword_comment_agree
  ADD CONSTRAINT FKs8g7tsy9r5df58x8b2s3roxal
FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE microword_comment_reply
  ADD CONSTRAINT FKb44k688738ikia8uw9gwgrpwe
FOREIGN KEY (replied_user_id) REFERENCES user (id);

ALTER TABLE microword_comment_reply
  ADD CONSTRAINT FKqqsgw29rthe2ujad7athdea6f
FOREIGN KEY (reply_user_id) REFERENCES user (id);

ALTER TABLE suggestion
  ADD CONSTRAINT FKnocwtfc34pk1e7upvi1plaedu
FOREIGN KEY (user_id) REFERENCES user (id);

CREATE INDEX FKkdo718h8lxy4nueeyjqqvtedi
  ON user_authorization (user_permission_id);

CREATE INDEX FKbh4gismitdef373aqkfgtunjv
  ON user_friend (passive_user_id);

ALTER TABLE user_authorization
  ADD CONSTRAINT FKkdo718h8lxy4nueeyjqqvtedi
FOREIGN KEY (user_permission_id) REFERENCES user_permission (id);



