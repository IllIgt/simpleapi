DROP TABLE IF EXISTS groups CASCADE;
DROP SEQUENCE IF EXISTS group_seq;
CREATE SEQUENCE group_seq START WITH 1;
CREATE TABLE groups (
    id          BIGINT PRIMARY KEY DEFAULT nextval('group_seq'),
    code        VARCHAR         NOT NULL,
    specialization      VARCHAR         NOT NULL
);

DROP TABLE IF EXISTS student CASCADE;
DROP SEQUENCE IF EXISTS student_seq;
CREATE SEQUENCE student_seq START WITH 1;
CREATE TABLE student
(
    id       BIGINT PRIMARY KEY DEFAULT nextval('student_seq'),
    name     VARCHAR NOT NULL,
    surname  VARCHAR NOT NULL,
    group_id BIGINT  NOT NULL REFERENCES groups (id)
);