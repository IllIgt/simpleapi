package ru.mtuci.simpleapi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mtuci.simpleapi.dao.CourseRepository;
import ru.mtuci.simpleapi.dao.GroupRepository;
import ru.mtuci.simpleapi.dto.CourseDTO;
import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.dto.StudentDTO;
import ru.mtuci.simpleapi.mapper.CourseMapper;
import ru.mtuci.simpleapi.model.Course;
import ru.mtuci.simpleapi.model.Group;
import ru.mtuci.simpleapi.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
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
            course.setGroups(groupRepository.findAllById(courseDTO.getGroups()));
        }
        return modelMapper.map(courseRepository.save(course), CourseDTO.class);
    }

    @Override
    public List<GroupDTO> getCourseGroups(Long id) {
        Course course = courseRepository.findById(id).orElse(new Course());
        List<GroupDTO> groups = new ArrayList<>();
        for (final Group group : course.getGroups()) {
            groups.add(modelMapper.map(group, GroupDTO.class));
        }
        return groups;
    }

    @Override
    public void delete(Long id) {
        courseRepository.delete(id);
    }

    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO) {
        Course newCourse = modelMapper.map(courseDTO, Course.class);
        if(newCourse.getGroups() == null) {
            newCourse.setGroups(new ArrayList<>());
        } else {
            newCourse.setGroups(groupRepository.findAllById(courseDTO.getGroups()));
        }
        Optional<Course> savedCourse = courseRepository.findById(courseDTO.getId());
        if(savedCourse.isPresent()) {
            Course course = savedCourse.get();
            course.setName(newCourse.getName());
            course.setCode(newCourse.getCode());
            course.setHours(newCourse.getHours());
            course.setElective(newCourse.isElective());
            course.setGroups(newCourse.getGroups());

            return modelMapper.map(courseRepository.save(course), CourseDTO.class);
        } else {
            return modelMapper.map(courseRepository.save(newCourse), CourseDTO.class);
        }
    }
}
