package ru.mtuci.simpleapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.mtuci.simpleapi.model.Course;
import ru.mtuci.simpleapi.model.Group;
import ru.mtuci.simpleapi.model.Student;
import ru.mtuci.simpleapi.service.CourseService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CourseController.class)
@ActiveProfiles("test")
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private List<CourseDTO> courseDTOs;

    private List<GroupDTO> groupDTOS;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        GroupDTO managementGroup = new GroupDTO();
        managementGroup.setId(1L);
        managementGroup.setCode("MOM2002");
        managementGroup.setSpecialization("Management");

        GroupDTO itGroup = new GroupDTO();
        itGroup.setId(2L);
        itGroup.setCode("BZDB2032");
        itGroup.setSpecialization("IT");

        this.groupDTOS = new ArrayList<>();
        this.groupDTOS.add(managementGroup);
        this.groupDTOS.add(itGroup);


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
    }

    @Test
    void getCourse() throws Exception {

        CourseDTO courseDTO = courseDTOs.get(0);
        Long courseId = courseDTO.getId();
        given(courseService.get(courseId)).willReturn(courseDTO);

        this.mockMvc.perform(get("/api/v1/courses/{id}", courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(courseDTO.getName())))
                .andExpect(jsonPath("$.code", is(courseDTO.getCode())))
                .andExpect(jsonPath("$.hours", is(courseDTO.getHours())))
                .andExpect(jsonPath("$.elective", is(courseDTO.isElective())))
                .andExpect(jsonPath("$.groups.size()", is(courseDTO.getGroups().size())));
    }

    @Test
    void getAll() throws Exception {
        given(courseService.getAll()).willReturn(courseDTOs);

        this.mockMvc.perform(get("/api/v1/courses")).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(courseDTOs.size())));
    }

    @Test
    void saveCourse() throws Exception {

        CourseDTO courseDTO = courseDTOs.get(0);
        given(courseService.save(any(CourseDTO.class))).willReturn(courseDTO);


        this.mockMvc.perform(post("/api/v1/courses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().longValue("Location", courseDTO.getId()))
                .andExpect(jsonPath("$.name", is(courseDTO.getName())))
                .andExpect(jsonPath("$.code", is(courseDTO.getCode())))
                .andExpect(jsonPath("$.hours", is(courseDTO.getHours())))
                .andExpect(jsonPath("$.elective", is(courseDTO.isElective())))
                .andExpect(jsonPath("$.groups.size()", is(courseDTO.getGroups().size())));
    }


    @Test
    void getGroups() throws Exception {

        CourseDTO courseDTO = courseDTOs.get(0);
        given(courseService.getCourseGroups(courseDTO.getId())).willReturn(groupDTOS);

        this.mockMvc.perform(get("/api/v1/courses/{id}/groups", courseDTO.getId())).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(groupDTOS.size())));


    }

    @Test
    void deleteCourse() throws Exception {
        Long courseId = 1L;
        doNothing().when(courseService).delete(courseId);

        this.mockMvc.perform(delete("/api/v1/courses/{id}", courseId))
                .andExpect(status().isNoContent());

    }
}