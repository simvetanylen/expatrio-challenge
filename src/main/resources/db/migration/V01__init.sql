
-- Note : user is a reserved word in postgresql
CREATE TABLE "app_user" (
  "id"              CHAR(36)        NOT NULL PRIMARY KEY    ,
  "creation_time"   timestamp       NOT NULL                ,
  "update_time"     timestamp       NOT NULL                ,
  "role"            VARCHAR(32)     NOT NULL                ,
  "password"        VARCHAR(128)                            ,
  "firstname"       VARCHAR(32)     NOT NULL                ,
  "lastname"        VARCHAR(32)     NOT NULL                ,
  "email"           VARCHAR(128)    NOT NULL UNIQUE         ,
  "description"     VARCHAR(500)
);
