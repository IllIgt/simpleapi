package ru.mtuci.simpleapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ru.mtuci.simpleapi.dao.CourseRepository;
import ru.mtuci.simpleapi.dao.GroupRepository;
import ru.mtuci.simpleapi.dto.CourseDTO;
import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.mapper.GroupMapper;
import ru.mtuci.simpleapi.mapper.StudentMapper;
import ru.mtuci.simpleapi.model.Course;
import ru.mtuci.simpleapi.model.Group;
import ru.mtuci.simpleapi.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {
    @Mock
    GroupRepository groupRepository;

    @Mock
    CourseRepository courseRepository;

    ModelMapper modelMapper;

    CourseServiceImpl courseService;

    private CourseDTO courseDTO;
    private List<Course> courses;
    private Course course;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        modelMapper.addMappings(new StudentMapper());
        modelMapper.addMappings(new GroupMapper());
        courseService = new CourseServiceImpl(courseRepository, groupRepository, modelMapper);

        Group group = new Group();
        group.setId(1L);
        group.setCode("TSTGRP");
        group.setSpecialization("TEST GROUP");

        Group secondGroup = new Group();
        secondGroup.setId(2L);
        secondGroup.setCode("TSTGRP2");
        secondGroup.setSpecialization("TEST GROUP2");


        Student student = new Student("student", "studentov", group);
        student.setId(1L);
        Student secondStudent = new Student("secondStudent", "secStudov", group);
        secondStudent.setId(2L);
        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(secondStudent);

        group.setStudents(students);
        List<Group> groups = new ArrayList<>();
        groups.add(group);
        groups.add(secondGroup);


        this.course = new Course(1L, "TSTCRS", "TEST COURSE", 100, false, groups);
        Course secondCourse = new Course(2L, "TSTCRS2", "TEST COURSE 2", 50, true, groups);
        this.courses = new ArrayList<>();
        courses.add(course);
        courses.add(secondCourse);

        group.setCourses(courses);

        this.courseDTO = new CourseDTO();
        this.courseDTO.setId(course.getId());
        this.courseDTO.setCode(course.getCode());
        this.courseDTO.setName(course.getName());
        this.courseDTO.setElective(course.isElective());
        this.courseDTO.setHours(course.getHours());
        this.courseDTO.setGroups(course.getGroups().stream().map(Group::getId).collect(Collectors.toList()));
    }


    @Test
    void get() {
        Long courseId = course.getId();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        CourseDTO foundCourse = courseService.get(courseId);

        Assertions.assertEquals(foundCourse.getId(), course.getId());
        Assertions.assertEquals(foundCourse.getCode(), course.getCode());
        Assertions.assertEquals(foundCourse.getName(), course.getName());
        Assertions.assertEquals(foundCourse.getHours(), course.getHours());
        Assertions.assertEquals(foundCourse.isElective(), course.isElective());
        Assertions.assertEquals(foundCourse.getGroups(),
                course.getGroups()
                        .stream()
                        .map(Group::getId)
                        .collect(Collectors.toList()));

    }

    @Test
    void getAll() {
        when(courseRepository.findAll()).thenReturn(courses);

        List<CourseDTO> foundCourseDOS = courseService.getAll();

        Assertions.assertEquals(foundCourseDOS.size(), courses.size());
    }

    @Test
    void save() {
        when(groupRepository.findAllById(courseDTO.getGroups())).thenReturn(course.getGroups());
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        CourseDTO foundCourse = courseService.save(courseDTO);

        Assertions.assertEquals(foundCourse.getId(), course.getId());
        Assertions.assertEquals(foundCourse.getCode(), course.getCode());
        Assertions.assertEquals(foundCourse.getName(), course.getName());
        Assertions.assertEquals(foundCourse.getHours(), course.getHours());
        Assertions.assertEquals(foundCourse.isElective(), course.isElective());
        Assertions.assertEquals(foundCourse.getGroups(),
                course.getGroups()
                        .stream()
                        .map(Group::getId)
                        .collect(Collectors.toList()));
    }

    @Test
    void getCourseGroups() {
        Long courseId = course.getId();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        List<GroupDTO> foundGroups = courseService.getCourseGroups(courseId);
        Assertions.assertEquals(foundGroups.size(), course.getGroups().size());
    }

    @Test
    void delete() {
        Long courseId = course.getId();
        when(courseRepository.delete(courseId)).thenReturn(anyInt());

        courseService.delete(courseId);
        verify(courseRepository).delete(courseId);
    }

    @Test
    void updateCourse() {
        Course newCourse = new Course();
        newCourse.setId(course.getId());
        newCourse.setName(course.getName());
        newCourse.setCode(course.getCode());
        newCourse.setElective(course.isElective());
        newCourse.setHours(course.getHours());
        newCourse.setGroups(course.getGroups());

        when(groupRepository.findAllById(courseDTO.getGroups())).thenReturn(course.getGroups());
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        CourseDTO foundCourse = courseService.updateCourse(courseDTO);

        Assertions.assertEquals(foundCourse.getId(), newCourse.getId());
        Assertions.assertEquals(foundCourse.getCode(), newCourse.getCode());
        Assertions.assertEquals(foundCourse.getName(), newCourse.getName());
        Assertions.assertEquals(foundCourse.getHours(), newCourse.getHours());
        Assertions.assertEquals(foundCourse.isElective(), newCourse.isElective());
        Assertions.assertEquals(foundCourse.getGroups(),
                newCourse.getGroups()
                        .stream()
                        .map(Group::getId)
                        .collect(Collectors.toList()));
    }
}