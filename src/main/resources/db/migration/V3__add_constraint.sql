ALTER TABLE passport ADD CONSTRAINT FK_PASSPORT_ON_PERSON FOREIGN KEY (person_id) REFERENCES person (id);