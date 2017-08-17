/**
 * create person
 */

DROP TABLE IF EXISTS person CASCADE;

CREATE TABLE IF NOT EXISTS person
(
  id BIGSERIAL PRIMARY KEY NOT NULL,
  code VARCHAR(300) NOT NULL,
  name VARCHAR(512),
  active BOOLEAN,
  city VARCHAR(100) NOT NULL,
  street VARCHAR(512),
  zip_code VARCHAR(100)

);

ALTER TABLE person ADD CONSTRAINT person_code_ck UNIQUE (code);

INSERT INTO person (code, name, active, city, street, zip_Code)
VALUES('1234', 'Leonel Messi', TRUE, 'Buenos Aires', 'Boca', '234-432');


