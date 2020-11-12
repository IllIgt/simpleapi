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
    isElective BOOLEAN NOT NULL,
    UNIQUE(id)
);

DROP TABLE IF EXISTS course_group;
CREATE TABLE course_group
(
    group_id    BIGINT NOT NULL,
    course_id   BIGINT NOT NULL,
    PRIMARY KEY (group_id, course_id),
    CONSTRAINT FK_GROUP FOREIGN KEY (group_id) REFERENCES  groups (id),
    CONSTRAINT FK_COURSE FOREIGN KEY (course_id) REFERENCES course (id)
);