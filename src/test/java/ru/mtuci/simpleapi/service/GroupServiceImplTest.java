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
import ru.mtuci.simpleapi.dao.StudentRepository;
import ru.mtuci.simpleapi.dto.CourseDTO;
import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.dto.StudentDTO;
import ru.mtuci.simpleapi.mapper.CourseMapper;
import ru.mtuci.simpleapi.mapper.StudentMapper;
import ru.mtuci.simpleapi.model.Course;
import ru.mtuci.simpleapi.model.Group;
import ru.mtuci.simpleapi.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

    @Mock
    StudentRepository studentRepository;

    @Mock
    GroupRepository groupRepository;

    @Mock
    CourseRepository courseRepository;

    ModelMapper modelMapper;

    GroupServiceImpl groupService;

    private GroupDTO groupDTO;
    private List<Group> groups;
    private Group group;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        modelMapper.addMappings(new StudentMapper());
        modelMapper.addMappings(new CourseMapper());
        groupService = new GroupServiceImpl(groupRepository, modelMapper, studentRepository, courseRepository);

        this.group = new Group();
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
        groups = new ArrayList<>();
        groups.add(group);
        groups.add(secondGroup);


        Course course = new Course(1L, "TSTCRS", "TEST COURSE", 100, false, groups);

        List<Course> courses = new ArrayList<>();
        courses.add(course);

        group.setCourses(courses);

        this.groupDTO = new GroupDTO();
        this.groupDTO.setId(group.getId());
        this.groupDTO.setCode(group.getCode());
        this.groupDTO.setSpecialization(group.getSpecialization());
        this.groupDTO.setCoursesId(group.getCourses().stream().map(Course::getId).collect(Collectors.toList()));
        this.groupDTO.setStudentsId(group.getStudents().stream().map(Student::getId).collect(Collectors.toList()));
    }

    @Test
    void get() {
        Long groupId = group.getId();
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
        GroupDTO findedGroup = groupService.get(groupId);
        Assertions.assertEquals(findedGroup.getId(), group.getId());
        Assertions.assertEquals(findedGroup.getCoursesId().size(), group.getCourses().size());
        Assertions.assertEquals(findedGroup.getStudentsId().size(), group.getStudents().size());
    }

    @Test
    void getAll() {
        when(groupRepository.findAll()).thenReturn(groups);
        List<GroupDTO> groupsDTOs = groupService.getAll();
        Assertions.assertEquals(groupsDTOs.size(), groups.size());
    }

    @Test
    void saveGroup() {
        List<Long> studentIds = group.getStudents().stream().map(Student::getId).collect(Collectors.toList());
        List<Long> coursesIds = group.getCourses().stream().map(Course::getId).collect(Collectors.toList());
        when(studentRepository.findAllById(studentIds)).thenReturn(group.getStudents());
        when(courseRepository.findAllById(coursesIds)).thenReturn(group.getCourses());
        when(groupRepository.save(any(Group.class))).thenReturn(group);
        when(studentRepository.saveAll(group.getStudents())).thenReturn(any());
        when(courseRepository.saveAll(group.getCourses())).thenReturn(any());

        GroupDTO findedGroup = groupService.save(groupDTO);
        Assertions.assertEquals(findedGroup.getId(), group.getId());
        Assertions.assertEquals(findedGroup.getCoursesId().size(), group.getCourses().size());
        Assertions.assertEquals(findedGroup.getStudentsId().size(), group.getStudents().size());
    }

    @Test
    void getGroupStudents() {
        Long groupId = group.getId();
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        List<StudentDTO> studentDTOs = groupService.getGroupStudents(groupId);

        Assertions.assertEquals(studentDTOs.size(), group.getStudents().size());

    }

    @Test
    void getGroupCourses() {
        Long groupId = group.getId();
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        List<CourseDTO> courseDTOs = groupService.getGroupCourses(groupId);

        Assertions.assertEquals(courseDTOs.size(), group.getCourses().size());

    }

    @Test
    void deleteGroupWithRelations() {
        Long groupId = group.getId();
        List<Long> studentIds = group.getStudents().stream().map(Student::getId).collect(Collectors.toList());
        doReturn(Optional.of(group)).when(groupRepository).findById(groupId);

        groupService.delete(groupId);

        verify(studentRepository).delete(studentIds);
        verify(groupRepository).delete(groupId);
    }
}