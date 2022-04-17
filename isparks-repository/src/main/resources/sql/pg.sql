-- ----------------------------
-- Table structure for relation
-- ----------------------------
DROP TABLE IF EXISTS "relation";
CREATE TABLE "relation" (
  "id" varchar(55) NOT NULL,
  "status" int4 NOT NULL,
  "create_time" int8 NOT NULL,
  "modify_time" int8,
  "from_entity" varchar(30) NOT NULL,
  "from_id" varchar(55) NOT NULL,
  "to_entity" varchar(30) NOT NULL,
  "to_id" varchar(55) NOT NULL,
  PRIMARY KEY ("id")
);