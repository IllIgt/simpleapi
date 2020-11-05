package ru.mtuci.simpleapi.service;

import ru.mtuci.simpleapi.model.Student;

import java.util.List;

public interface StudentService {
    Student get(Long id);

    List<Student> getAll();

    Student save(Student student);

    void delete(Long id);
}
