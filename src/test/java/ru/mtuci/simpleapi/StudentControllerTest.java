package ru.mtuci.simpleapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.mtuci.simpleapi.controller.StudentController;
import ru.mtuci.simpleapi.model.Student;
import ru.mtuci.simpleapi.service.StudentService;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.BDDMockito.given;
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

    @BeforeEach
    void setUp() {
        this.studentList = new ArrayList<>();
        this.studentList.add(new Student("Ivan", "Ivanovich", 1));
        this.studentList.add(new Student("Kirill", "Kirillovich", 2));
        this.studentList.add(new Student("Anton", "Antonovich", 3));
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
        final Student student = new Student("Isaac","Isaacovich", 3);

        given(studentService.get(studentId)).willReturn(student);

        this.mockMvc.perform(get("/api/users/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(student.getName())))
                .andExpect(jsonPath("$.surname", is(student.getSurname())))
                .andExpect(jsonPath("$.group_id", is(student.getGroup_id())));
    }
}