package ru.mtuci.simpleapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.simpleapi.dto.CourseDTO;
import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.service.CourseService;
import ru.mtuci.simpleapi.service.GroupService;

import java.util.List;


//  TODO add responses for wrong requests
@Slf4j
@RestController
@RequestMapping(value = CourseController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseController {
    public final static String REST_URL = "/api/v1/courses";

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(value = "/{id}")
    public CourseDTO get(@PathVariable("id") Long id) {
        log.info("get course by id " + id);
        return courseService.get(id);
    }

    @GetMapping
    public List<CourseDTO> getAll() {
        log.info("get all courses");
        return courseService.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CourseDTO save(@RequestBody CourseDTO group) {
        log.info("save course");
        return courseService.save(group);
    }
}