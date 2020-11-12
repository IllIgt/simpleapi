INSERT INTO public.groups (code, specialization) VALUES ('ЗММ2001', 'Менеджмент');
INSERT INTO public.groups (code, specialization) VALUES ('ЗИТ2003', 'ИТ');

INSERT INTO public.student (name, surname, group_id) VALUES ('Матвей', 'Матвеев', 1);
INSERT INTO public.student (name, surname, group_id) VALUES ('Иван', 'Иванов', 2);
INSERT INTO public.student (name, surname, group_id) VALUES ('Иннокентий', 'Иннокентьев', 2);
INSERT INTO public.student (name, surname, group_id) VALUES ('Семен', 'Семенов', 2);

INSERT INTO public.course (code, name, hours, isElective) VALUES ("АИСД1", "Aлгоритмы и структуры данных", 40, FALSE);
INSERT INTO public.course (code, name, hours, isElective) VALUES ("ТВИМС2", "Теория вероятностей и математическая статистика", 62, FALSE);
INSERT INTO public.course (code, name, hours, isElective) VALUES ("ПВЭ", "Приближенные вычисления", 20, TRUE);
INSERT INTO public.course (code, name, hours, isElective) VALUES ("ФИК1", "Философия и культурология", 40, FALSE);
INSERT INTO public.course (code, name, hours, isElective) VALUES ("УП", "Управление Предприятием", 60, FALSE);
INSERT INTO public.course (code, name, hours, isElective) VALUES ("УИПЭ", "Управление инноваиционными проектами", 40, TRUE);