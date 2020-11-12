package ru.mtuci.simpleapi.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mtuci.simpleapi.dao.CourseRepository;
import ru.mtuci.simpleapi.dto.CourseDTO;
import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.mapper.StudentMapper;
import ru.mtuci.simpleapi.model.Course;
import ru.mtuci.simpleapi.model.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;


    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {

        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CourseDTO get(Long id) {
        return null;
    }

    @Override
    public List<CourseDTO> getAll() {
        //  TODO add null Handling
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CourseDTO save(CourseDTO courseDTO) {
        Course course = modelMapper.map(courseDTO, Course.class);
        return modelMapper.map(courseRepository.save(course), CourseDTO.class);
    }

    @Override
    public List<CourseDTO> getCourseGroups(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
