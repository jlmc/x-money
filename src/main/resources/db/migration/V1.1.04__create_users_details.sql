/**
 * create user details
 */

DROP TABLE IF EXISTS x_user_role;
DROP TABLE IF EXISTS x_user;
DROP TABLE IF EXISTS x_role;

CREATE TABLE x_user
(
  username VARCHAR(100) PRIMARY KEY NOT NULL,
  password VARCHAR(1024)            NOT NULL,
  name     VARCHAR(150)             NOT NULL
);


CREATE TABLE x_role
(
  name        VARCHAR(100) PRIMARY KEY NOT NULL,
  description VARCHAR(256)
);


CREATE TABLE x_user_role
(
  username  VARCHAR(100) NOT NULL,
  role_name VARCHAR(100) NOT NULL,
  CONSTRAINT x_user_role_x_user_username_fk FOREIGN KEY (username) REFERENCES x_user (username),
  CONSTRAINT x_user_role_x_role_name_fk FOREIGN KEY (role_name) REFERENCES x_role (name),
  CONSTRAINT x_user_role_username_role_name_pk UNIQUE (username, role_name)
);


/**
 *
 */


INSERT INTO x_user (username, password, name)
VALUES ('costajlmpp@gmail.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.', 'Administrator');
INSERT INTO x_user (username, password, name)
VALUES ('funny8086@gmail.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.', 'funny');

INSERT INTO x_role (name, description) VALUES ('ROLE_ADD_CATEGORIES', '');
INSERT INTO x_role (name, description) VALUES ('ROLE_SEARCH_CATEGORIES', '');

INSERT INTO x_role (name, description) VALUES ('ROLE_ADD_PERSON', '');
INSERT INTO x_role (name, description) VALUES ('ROLE_REMOVE_PERSON', '');
INSERT INTO x_role (name, description) VALUES ('ROLE_SEARCH_PERSON', '');

INSERT INTO x_role (name, description) VALUES ('ROLE_ADD_ACTIVITY', '');
INSERT INTO x_role (name, description) VALUES ('ROLE_REMOVE_ACTIVITY', '');
INSERT INTO x_role (name, description) VALUES ('ROLE_SEARCH_ACTIVITY', '');

-- admin with all permissions
INSERT INTO x_user_role (username, role_name)
  SELECT
    'costajlmpp@gmail.com',
    name
  FROM x_role;

-- demo user just to search operations
INSERT INTO x_user_role (username, role_name)
VALUES ('funny8086@gmail.com', 'ROLE_SEARCH_CATEGORIES');
INSERT INTO x_user_role (username, role_name)
VALUES ('funny8086@gmail.com', 'ROLE_SEARCH_PERSON');
INSERT INTO x_user_role (username, role_name)
VALUES ('funny8086@gmail.com', 'ROLE_SEARCH_ACTIVITY');


