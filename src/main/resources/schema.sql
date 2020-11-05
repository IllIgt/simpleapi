DROP TABLE IF EXISTS student CASCADE;
DROP SEQUENCE IF EXISTS global_seq;
CREATE SEQUENCE global_seq START WITH 1;
CREATE TABLE student(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name        VARCHAR         NOT NULL,
    surname       VARCHAR         NOT NULL,
    group_id       INTEGER         NOT NULL
);