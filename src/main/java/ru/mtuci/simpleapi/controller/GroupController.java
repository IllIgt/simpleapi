package ru.mtuci.simpleapi.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.simpleapi.dto.CourseDTO;
import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.dto.StudentDTO;
import ru.mtuci.simpleapi.service.GroupService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//  TODO add responses for wrong requests
@Slf4j
@RestController
@RequestMapping(value = GroupController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController {
    public final static String REST_URL = "/api/v1/groups";

    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping(value = "/{id}")
    public GroupDTO get(@PathVariable("id") Long id) {
        log.info("get group by id " + id);
        return groupService.get(id);
    }

    @GetMapping
    public List<GroupDTO> getAll() {
        log.info("get all groups");
        return groupService.getAll();
    }

    @GetMapping(value = "/{id}/students")
    public List<StudentDTO> getStudents(@PathVariable("id") Long id) {
        log.info("get group students by id " + id);
        return groupService.getGroupStudents(id);
    }

    @GetMapping(value = "/{id}/courses")
    public List<CourseDTO> getCourses(@PathVariable("id") Long id) {
        log.info("get group courses by id " + id);
        return groupService.getGroupCourses(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public GroupDTO save(@RequestBody GroupDTO group, HttpServletResponse response) {
        log.info("save group");
        GroupDTO groupDTO = groupService.save(group);
        response.setHeader("Location", groupDTO.getId().toString());
        return groupDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        log.info("delete group by id" + id);
        groupService.delete(id);
    }

}
