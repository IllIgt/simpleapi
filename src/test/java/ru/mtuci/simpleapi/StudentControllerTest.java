package ru.mtuci.simpleapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.mtuci.simpleapi.controller.StudentController;
import ru.mtuci.simpleapi.model.Group;
import ru.mtuci.simpleapi.model.Student;
import ru.mtuci.simpleapi.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(controllers = StudentController.class)
@ActiveProfiles("test")
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private List<Student> studentList;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Group managementGroup = new Group(1L, "ZMMB2002", "Managment");
        Group itGroup = new Group(2L, "OMIB2030", "IT");

        this.studentList = new ArrayList<>();
        this.studentList.add(new Student("Ivan", "Ivanovich", itGroup));
        this.studentList.add(new Student("Kirill", "Kirillovich", managementGroup));
        this.studentList.add(new Student("Anton", "Antonovich", managementGroup));
    }

    @Test
    void shouldFetchAllStudents() throws Exception {
        given(studentService.getAll()).willReturn(studentList);

        this.mockMvc.perform(get("/api/v1/students")).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(studentList.size())));
    }

    @Test
    void shouldFetchOneStudentById() throws Exception {
        final Long studentId = 1L;
        Group group = new Group(1L, "code", "spec");
        final Student student = new Student("Isaac","Isaacovich", group);

        given(studentService.get(studentId)).willReturn(student);

        this.mockMvc.perform(get("/api/v1/students/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(student.getName())))
                .andExpect(jsonPath("$.surname", is(student.getSurname())))
                .andExpect(jsonPath("$.group.id", is(student.getGroup().getId().intValue())));
    }

    @Test
    void shouldCreateNewStudent() throws Exception {
        given(studentService.save(any(Student.class))).willAnswer((invocation) -> invocation.getArgument(0));

        Group group = new Group(1L, "code", "spec");
        Student student = new Student("student", "studentov", group);
        this.mockMvc.perform(post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(student.getName())))
                .andExpect(jsonPath("$.surname", is(student.getSurname())))
                .andExpect(jsonPath("$.group.id", is(student.getGroup().getId().intValue())));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        Long studentId = 1L;
        doNothing().when(studentService).delete(studentId);

        this.mockMvc.perform(delete("/api/v1/students/{id}", studentId))
                .andExpect(status().isNoContent());
    }
}