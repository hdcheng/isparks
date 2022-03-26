-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id VARCHAR PRIMARY KEY,
  status int4,
  create_time BIGINT,
  modify_time BIGINT,
  nick_name VARCHAR,
  user_name VARCHAR,
  email VARCHAR,
  password VARCHAR,
  avatar VARCHAR
);
-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS attachment;
CREATE TABLE attachment (
  id VARCHAR PRIMARY KEY,
  status int4,
  create_time BIGINT,
  modify_time BIGINT,
  post_id VARCHAR ,
  file_id VARCHAR
);
-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS category;
CREATE TABLE category (
  id VARCHAR PRIMARY KEY,
  status int4,
  create_time BIGINT,
  modify_time BIGINT,
  name VARCHAR,
  description VARCHAR,
  parent_id VARCHAR
);
-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS comment;
CREATE TABLE comment (
  id VARCHAR PRIMARY KEY,
  status int4,
  create_time BIGINT,
  modify_time BIGINT,
  content VARCHAR,
  name VARCHAR,
  email  VARCHAR(30),
  address VARCHAR,
  ip VARCHAR
);
-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS file;
CREATE TABLE file (
  id VARCHAR PRIMARY KEY,
  status int4,
  create_time BIGINT,
  modify_time BIGINT,
  name VARCHAR,
  media_type VARCHAR,
  file_type VARCHAR,
  origin VARCHAR,
  location VARCHAR
);
-- ----------------------------
-- Table structure for journal
-- ----------------------------
DROP TABLE IF EXISTS journal;
CREATE TABLE journal (
  id VARCHAR PRIMARY KEY,
  status int4,
  create_time BIGINT,
  modify_time BIGINT,
  content VARCHAR
);
-- ----------------------------
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS link;
CREATE TABLE link (
  id VARCHAR PRIMARY KEY,
  status int4,
  create_time BIGINT,
  modify_time BIGINT,
  name VARCHAR,
  url VARCHAR,
  logo VARCHAR,
  type int4
);
-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS log;
CREATE TABLE log (
  id VARCHAR PRIMARY KEY,
  status int4,
  create_time BIGINT,
  modify_time BIGINT,
  types VARCHAR,
  content VARCHAR,
  ip VARCHAR,
  date VARCHAR
);
-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS post;
CREATE TABLE post (
  id VARCHAR PRIMARY KEY,
  status int4,
  create_time BIGINT,
  modify_time BIGINT,
  title VARCHAR,
  url VARCHAR,
  origin_content VARCHAR,
  summary VARCHAR
);
-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS tag;
CREATE TABLE tag (
  id VARCHAR PRIMARY KEY,
  status int4,
  create_time BIGINT,
  modify_time BIGINT,
  name VARCHAR,
  description VARCHAR
);

-- ----------------------------
-- Table structure for post_category_rl
-- ----------------------------
DROP TABLE IF EXISTS post_category_rl;
CREATE TABLE post_category_rl (
  id VARCHAR PRIMARY KEY,
  status int4,
  create_time BIGINT,
  modify_time BIGINT,
  post_id VARCHAR,
  category_id VARCHAR
);
-- ----------------------------
-- Table structure for post_tag_rl
-- ----------------------------
DROP TABLE IF EXISTS post_tag_rl;
CREATE TABLE post_tag_rl (
  id VARCHAR PRIMARY KEY,
  status int4,
  create_time BIGINT,
  modify_time BIGINT,
  post_id VARCHAR,
  tag_id VARCHAR
);

-- ----------------------------
-- Table structure for post_comment_rl
-- ----------------------------
DROP TABLE IF EXISTS post_comment_rl;
CREATE TABLE post_comment_rl (
  id VARCHAR PRIMARY KEY,
  status int4,
  create_time BIGINT,
  modify_time BIGINT,
  post_id VARCHAR,
  comment_id VARCHAR
);

-- ----------------------------
-- Table structure for option
-- ----------------------------
DROP TABLE IF EXISTS option;
CREATE TABLE option (
  id VARCHAR PRIMARY KEY,
  status int4,
  create_time BIGINT,
  modify_time BIGINT,
  key VARCHAR,
  value VARCHAR,
  type int4
);

-- ----------------------------
-- Table structure for Relation
-- ----------------------------
DROP TABLE IF EXISTS relation;
CREATE TABLE relation (
  id VARCHAR PRIMARY KEY,
  status int4,
  create_time BIGINT,
  modify_time BIGINT,
  from_entity VARCHAR,
  from_id VARCHAR,
  to_entity VARCHAR,
  to_id VARCHAR
);

-- ----------------------------
-- Table structure for Relation
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  "name" varchar(255) NOT NULL,
  "value" int4 NOT NULL,
  PRIMARY KEY ("value"),
  UNIQUE ("name")
);