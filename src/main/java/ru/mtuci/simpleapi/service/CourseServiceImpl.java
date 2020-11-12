package ru.mtuci.simpleapi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mtuci.simpleapi.dao.CourseRepository;
import ru.mtuci.simpleapi.dao.GroupRepository;
import ru.mtuci.simpleapi.dto.CourseDTO;
import ru.mtuci.simpleapi.mapper.CourseMapper;
import ru.mtuci.simpleapi.model.Course;
import ru.mtuci.simpleapi.model.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CourseServiceImpl implements CourseService {

    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, GroupRepository groupRepository, ModelMapper modelMapper) {

        this.courseRepository = courseRepository;
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(new CourseMapper());
    }


    @Override
    public CourseDTO get(Long id) {
        //  TODO add null Handling
        return modelMapper.map(courseRepository.findById(id).orElse(new Course()), CourseDTO.class);
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
        if(course.getGroups() == null) {
            course.setGroups(new ArrayList<>());
        } else {
            List<Group> groups = groupRepository.findAllById(courseDTO.getGroups());
            course.setGroups(groups);
        }
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
