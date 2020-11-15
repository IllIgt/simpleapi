package ru.mtuci.simpleapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ru.mtuci.simpleapi.dao.StudentRepository;
import ru.mtuci.simpleapi.dto.StudentDTO;
import ru.mtuci.simpleapi.mapper.GroupMapper;
import ru.mtuci.simpleapi.model.Group;
import ru.mtuci.simpleapi.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    StudentRepository studentRepository;

    ModelMapper modelMapper;

    StudentServiceImpl studentService;

    private Student student;
    private List<Student> students;
    private StudentDTO studentDTO;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        modelMapper.addMappings(new GroupMapper());
        studentService = new StudentServiceImpl(studentRepository, modelMapper);

        Group group = new Group();
        group.setId(102L);

        this.student = new Student("student", "studentov", group);
        this.student.setId(1L);
        Student secondStudent = new Student("secondStudent", "secStudov", group);
        this.students = new ArrayList<>();
        this.students.add(student);
        this.students.add(secondStudent);
        group.setStudents(students);

        this.studentDTO = new StudentDTO();
        this.studentDTO.setId(student.getId());
        this.studentDTO.setName(student.getName());
        this.studentDTO.setSurname(student.getSurname());
        this.studentDTO.setGroupId(group.getId());
    }

    @Test
    void getStudentById() {
        Long studentId = student.getId();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        StudentDTO findedStudent = studentService.get(studentId);
        Assertions.assertEquals(findedStudent.getId(), studentId);
        Assertions.assertEquals(findedStudent.getGroupId(), student.getGroup().getId());
    }

    @Test
    void getAll() {
        when(studentRepository.findAll()).thenReturn(students);
        List<StudentDTO> findedStudents = studentService.getAll();
        Assertions.assertEquals(students.size(), findedStudents.size());
    }

    @Test
    void save() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentDTO studentSavedDTO = studentService.save(studentDTO);
        Assertions.assertEquals(studentSavedDTO.getId(), studentDTO.getId());
        Assertions.assertEquals(studentSavedDTO.getName(), studentDTO.getName());
        Assertions.assertEquals(studentSavedDTO.getSurname(), studentDTO.getSurname());
        Assertions.assertEquals(studentSavedDTO.getGroupId(), studentDTO.getGroupId());
    }

    @Test
    void delete() {
        Long studentId = student.getId();
        when(studentRepository.delete(studentId)).thenReturn(anyInt());

        studentService.delete(studentId);
        verify(studentRepository).delete(studentId);
    }
}