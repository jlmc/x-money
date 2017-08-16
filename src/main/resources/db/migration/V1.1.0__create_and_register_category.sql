/**
 * create and register category
 */

CREATE TABLE IF NOT EXISTS category
(
  code BIGSERIAL PRIMARY KEY NOT NULL,
  name VARCHAR NOT NULL
);
CREATE UNIQUE INDEX category_code_uindex ON category (code);

/** Default categories*/

INSERT INTO category (name) VALUES ('Recreation');
INSERT INTO category (name) VALUES ('Feeding');
INSERT INTO category (name) VALUES ('Supermarket');
INSERT INTO category (name) VALUES ('Drugstore');
INSERT INTO category (name) VALUES ('Others');