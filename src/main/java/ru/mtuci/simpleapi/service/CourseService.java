package ru.mtuci.simpleapi.service;

import ru.mtuci.simpleapi.dto.CourseDTO;
import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.dto.StudentDTO;

import java.util.List;


public interface CourseService {

    CourseDTO get(Long id);

    List<CourseDTO> getAll();

    CourseDTO save(CourseDTO group);

    List<CourseDTO> getCourseGroups(Long id);

    void delete(Long id);
}

