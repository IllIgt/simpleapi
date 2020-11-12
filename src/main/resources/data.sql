INSERT INTO public.groups (code, specialization) VALUES ('ЗММ2001', 'Менеджмент');
INSERT INTO public.groups (code, specialization) VALUES ('ЗИТ2003', 'ИТ');

INSERT INTO public.student (name, surname, group_id) VALUES ('Матвей', 'Матвеев', 1);
INSERT INTO public.student (name, surname, group_id) VALUES ('Иван', 'Иванов', 2);
INSERT INTO public.student (name, surname, group_id) VALUES ('Иннокентий', 'Иннокентьев', 2);
INSERT INTO public.student (name, surname, group_id) VALUES ('Семен', 'Семенов', 2);

INSERT INTO public.course (code, name, hours, elective) VALUES ("АИСД1", "Aлгоритмы и структуры данных", 40, FALSE);
INSERT INTO public.course (code, name, hours, elective) VALUES ("ТВИМС2", "Теория вероятностей и математическая статистика", 62, FALSE);
INSERT INTO public.course (code, name, hours, elective) VALUES ("ПВЭ", "Приближенные вычисления", 20, TRUE);
INSERT INTO public.course (code, name, hours, elective) VALUES ("ФИК1", "Философия и культурология", 40, FALSE);
INSERT INTO public.course (code, name, hours, elective) VALUES ("УП", "Управление Предприятием", 60, FALSE);
INSERT INTO public.course (code, name, hours, elective) VALUES ("УИПЭ", "Управление инноваиционными проектами", 40, TRUE);

INSERT INTO public.course_group(group_id, course_id) VALUES (1, 2);
INSERT INTO public.course_group(group_id, course_id) VALUES (1, 5);
INSERT INTO public.course_group(group_id, course_id) VALUES (1, 6);
INSERT INTO public.course_group(group_id, course_id) VALUES (1, 4);
INSERT INTO public.course_group(group_id, course_id) VALUES (2, 1);
INSERT INTO public.course_group(group_id, course_id) VALUES (2, 4);
INSERT INTO public.course_group(group_id, course_id) VALUES (2, 2);
INSERT INTO public.course_group(group_id, course_id) VALUES (2, 3);
