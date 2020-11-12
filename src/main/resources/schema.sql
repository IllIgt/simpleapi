DROP TABLE IF EXISTS groups CASCADE;
DROP SEQUENCE IF EXISTS group_seq;
CREATE SEQUENCE group_seq START WITH 1;
CREATE TABLE groups (
    id                  BIGINT PRIMARY KEY DEFAULT nextval('group_seq'),
    code                VARCHAR         NOT NULL,
    specialization      VARCHAR         NOT NULL,
    UNIQUE(id)
);

DROP TABLE IF EXISTS student CASCADE;
DROP SEQUENCE IF EXISTS student_seq;
CREATE SEQUENCE student_seq START WITH 1;
CREATE TABLE student
(
    id       BIGINT PRIMARY KEY DEFAULT nextval('student_seq'),
    name     VARCHAR NOT NULL,
    surname  VARCHAR NOT NULL,
    group_id BIGINT  NOT NULL REFERENCES groups (id),
    UNIQUE(id)
);

DROP TABLE IF EXISTS course;
DROP SEQUENCE IF EXISTS course_seq;
CREATE SEQUENCE course_seq START WITH 1;
CREATE TABLE course
(
    id       BIGINT PRIMARY KEY DEFAULT nextval('course_seq'),
    code     VARCHAR NOT NULL,
    name     VARCHAR NOT NULL,
    hours    INTEGER NOT NULL,
    elective BOOLEAN NOT NULL,
    UNIQUE(id)
);

DROP TABLE IF EXISTS course_group;
CREATE TABLE course_group
(
    group_id    BIGINT NOT NULL REFERENCES groups(id) ON UPDATE CASCADE ON DELETE CASCADE,
    course_id   BIGINT NOT NULL REFERENCES course(id) ON UPDATE CASCADE,
    CONSTRAINT group_course_pkey PRIMARY KEY (group_id, course_id)
);