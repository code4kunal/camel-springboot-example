CREATE TABLE users(
  id bigserial  PRIMARY KEY,
  username varchar(100) NOT NULL UNIQUE,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) DEFAULT NULL
);