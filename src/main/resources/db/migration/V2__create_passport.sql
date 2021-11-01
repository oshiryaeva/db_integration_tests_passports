CREATE TABLE IF NOT EXISTS passport (
  id BIGINT NOT NULL,
  serial_number INTEGER,
  number INTEGER NOT NULL,
  issue_date date NOT NULL,
  expiration_date date,
  active BOOLEAN NOT NULL,
  passport_type INTEGER NOT NULL,
  person_id BIGINT,
  CONSTRAINT pk_passport PRIMARY KEY (id)
);
