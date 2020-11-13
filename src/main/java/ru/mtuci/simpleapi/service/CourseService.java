package ru.mtuci.simpleapi.service;

import ru.mtuci.simpleapi.dto.CourseDTO;
import ru.mtuci.simpleapi.dto.GroupDTO;

import java.util.List;


public interface CourseService {

    CourseDTO get(Long id);

    List<CourseDTO> getAll();

    CourseDTO save(CourseDTO group);

    List<GroupDTO> getCourseGroups(Long id);

    void delete(Long id);
}

