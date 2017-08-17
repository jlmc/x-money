/**
 * create activity table
 */

drop table if exists activity;

CREATE TABLE activity
(
  code BIGSERIAL PRIMARY KEY NOT NULL,
  description VARCHAR,
  observation VARCHAR,
  payday DATE NOT NULL,
  maturity DATE NOT NULL,
  value DECIMAL(10,2) NOT NULL,
  type VARCHAR(20) NOT NULL,
  person_id BIGINT NOT NULL,
  category_code BIGINT NOT NULL,
  CONSTRAINT activity_category_code_fk FOREIGN KEY (category_code) REFERENCES category (code),
  CONSTRAINT activity_person_id_fk FOREIGN KEY (person_id) REFERENCES person (id)
);