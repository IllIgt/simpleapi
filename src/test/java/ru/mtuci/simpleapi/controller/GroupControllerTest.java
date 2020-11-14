package ru.mtuci.simpleapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.mtuci.simpleapi.dto.CourseDTO;
import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.dto.StudentDTO;
import ru.mtuci.simpleapi.service.GroupService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = GroupController.class)
@ActiveProfiles("test")
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    private List<CourseDTO> courseDTOs;

    private List<GroupDTO> groupDTOS;

    private List<StudentDTO> studentDTOS;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        this.courseDTOs = new ArrayList<>();

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(1L);
        courseDTO.setName("ТЕОРИЯ ВЕРОЯТНОСТИ");
        courseDTO.setCode("ТВМС");
        courseDTO.setHours(46);
        courseDTO.setElective(false);
        courseDTO.setGroups(Arrays.asList(1L,2L));

        CourseDTO secondCourseDTO = new CourseDTO();
        secondCourseDTO.setId(2L);
        secondCourseDTO.setName("Теория оптимизации");
        secondCourseDTO.setCode("ТО");
        secondCourseDTO.setHours(58);
        secondCourseDTO.setElective(true);
        secondCourseDTO.setGroups(Collections.singletonList(2L));

        CourseDTO thirdCourseDTO = new CourseDTO();
        thirdCourseDTO.setId(2L);
        thirdCourseDTO.setName("Теория оптимизации");
        thirdCourseDTO.setCode("ТО");
        thirdCourseDTO.setHours(58);
        thirdCourseDTO.setElective(true);
        thirdCourseDTO.setGroups(Collections.singletonList(2L));

        courseDTOs.add(courseDTO);
        courseDTOs.add(secondCourseDTO);
        courseDTOs.add(thirdCourseDTO);

        this.studentDTOS = new ArrayList<>();

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

        studentDTOS.add(firstStudent);
        studentDTOS.add(secondStudent);
        studentDTOS.add(thirdStudent);

        GroupDTO managementGroup = new GroupDTO();
        managementGroup.setId(1L);
        managementGroup.setCode("MOM2002");
        managementGroup.setSpecialization("Management");
        managementGroup.setStudentsId(Arrays.asList(1L, 2L));
        managementGroup.setCoursesId(Collections.singletonList(3L));

        GroupDTO itGroup = new GroupDTO();
        itGroup.setId(2L);
        itGroup.setCode("BZDB2032");
        itGroup.setSpecialization("IT");
        managementGroup.setStudentsId(Collections.singletonList(3L));
        managementGroup.setCoursesId(Arrays.asList(1L, 2L));

        this.groupDTOS = new ArrayList<>();
        this.groupDTOS.add(managementGroup);
        this.groupDTOS.add(itGroup);
    }

    @Test
    void getGroup() throws Exception {
        GroupDTO groupDTO = groupDTOS.get(0);
        Long groupId = groupDTO.getId();
        given(groupService.get(groupId)).willReturn(groupDTO);

        this.mockMvc.perform(get("/api/v1/groups/{id}", groupId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specialization", is(groupDTO.getSpecialization())))
                .andExpect(jsonPath("$.code", is(groupDTO.getCode())))
                .andExpect(jsonPath("$.coursesId.size()", is(groupDTO.getCoursesId().size())))
                .andExpect(jsonPath("$.studentsId.size()", is(groupDTO.getStudentsId().size())));
    }

    @Test
    void getAllGroups() throws Exception {
        given(groupService.getAll()).willReturn(groupDTOS);

        this.mockMvc.perform(get("/api/v1/groups")).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(groupDTOS.size())));
    }

    @Test
    void getStudents() throws Exception {
        Long groupId = 1L;
        given(groupService.getGroupStudents(groupId)).willReturn(studentDTOS);

        this.mockMvc.perform(get("/api/v1/groups/{id}/students", groupId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(studentDTOS.size())));
    }

    @Test
    void getCourses() throws Exception{
        Long groupId = 1L;
        given(groupService.getGroupCourses(groupId)).willReturn(courseDTOs);

        this.mockMvc.perform(get("/api/v1/groups/{id}/courses", groupId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(courseDTOs.size())));
    }

    @Test
    void saveGroup() throws Exception {
        GroupDTO groupDTO = groupDTOS.get(0);
        given(groupService.save(any(GroupDTO.class))).willReturn(groupDTO);


        this.mockMvc.perform(post("/api/v1/groups")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(groupDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().longValue("Location", groupDTO.getId()))
                .andExpect(jsonPath("$.specialization", is(groupDTO.getSpecialization())))
                .andExpect(jsonPath("$.code", is(groupDTO.getCode())))
                .andExpect(jsonPath("$.coursesId.size()", is(groupDTO.getCoursesId().size())))
                .andExpect(jsonPath("$.studentsId.size()", is(groupDTO.getStudentsId().size())));
    }

    @Test
    void deleteGroupById() throws Exception {
        Long groupId = 1L;
        doNothing().when(groupService).delete(groupId);

        this.mockMvc.perform(delete("/api/v1/groups/{id}", groupId))
                .andExpect(status().isNoContent());
    }
}