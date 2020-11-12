package ru.mtuci.simpleapi.service;

import ru.mtuci.simpleapi.dto.StudentDTO;

import java.util.List;

public interface StudentService {
    StudentDTO get(Long id);

    List<StudentDTO> getAll();

    StudentDTO save(StudentDTO student);

    void delete(Long id);
}
