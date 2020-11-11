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
import ru.mtuci.simpleapi.dto.StudentDTO;
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

    private List<StudentDTO> studentDTOList;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Group managementGroup = new Group();
        managementGroup.setId(1L);
        managementGroup.setCode("MOM2002");
        managementGroup.setSpecialization("Management");
        Group itGroup = new Group();
        itGroup.setId(2L);
        itGroup.setCode("BZDB2032");
        itGroup.setSpecialization("IT");

        this.studentList = new ArrayList<>();
        this.studentList.add(new Student("Ivan", "Ivanovich", itGroup));
        this.studentList.add(new Student("Kirill", "Kirillovich", managementGroup));
        this.studentList.add(new Student("Anton", "Antonovich", managementGroup));

        this.studentDTOList = new ArrayList<>();

        StudentDTO firstStudent = new StudentDTO();
        firstStudent.setId(1L);
        firstStudent.setName("Kirill");
        firstStudent.setSurname("Kirillin");
        firstStudent.setGroupId(1L);

        StudentDTO secondStudent = new StudentDTO();
        secondStudent.setId(2L);
        secondStudent.setName("Nikita");
        secondStudent.setSurname("Nikitina");
        secondStudent.setGroupId(2L);

        StudentDTO thirdStudent = new StudentDTO();
        thirdStudent.setId(3L);
        thirdStudent.setName("Michail");
        thirdStudent.setSurname("Michailin");
        thirdStudent.setGroupId(2L);

        studentDTOList.add(firstStudent);
        studentDTOList.add(secondStudent);
        studentDTOList.add(thirdStudent);
    }

    @Test
    void shouldFetchAllStudents() throws Exception {
        given(studentService.getAll()).willReturn(studentDTOList);

        this.mockMvc.perform(get("/api/v1/students")).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(studentList.size())));
    }

    @Test
    void shouldFetchOneStudentById() throws Exception {
        final Long studentId = 1L;
        Group group = new Group();
        group.setId(1L);
        group.setCode("MGMT");
        group.setSpecialization("CoolSpec");

        final StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(25L);
        studentDTO.setName("Isaac");
        studentDTO.setSurname("Isaacov");
        studentDTO.setGroupId(group.getId());

        given(studentService.get(studentId)).willReturn(studentDTO);

        this.mockMvc.perform(get("/api/v1/students/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(studentDTO.getName())))
                .andExpect(jsonPath("$.surname", is(studentDTO.getSurname())))
                .andExpect(jsonPath("$.groupId", is(studentDTO.getGroupId().intValue())));
    }

    @Test
    void shouldCreateNewStudent() throws Exception {
        given(studentService.save(any(Student.class))).willAnswer((invocation) -> invocation.getArgument(0));

        Group group = new Group();
        group.setId(1L);
        group.setCode("MGMT");
        group.setSpecialization("CoolSpec");

        Student student = new Student("student", "studentov", group);
        this.mockMvc.perform(post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(student.getName())))
                .andExpect(jsonPath("$.surname", is(student.getSurname())))
                .andExpect(jsonPath("$.groupId", is(student.getGroup().getId().intValue())));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        Long studentId = 1L;
        doNothing().when(studentService).delete(studentId);

        this.mockMvc.perform(delete("/api/v1/students/{id}", studentId))
                .andExpect(status().isNoContent());
    }
}