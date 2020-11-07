package ru.mtuci.simpleapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.simpleapi.model.Student;
import ru.mtuci.simpleapi.service.GroupService;
import ru.mtuci.simpleapi.service.StudentService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = StudentController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentController {
    public final static String REST_URL = "/api/v1/students";

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {

        this.studentService = studentService;

    }

    @GetMapping
    public List<Student> getAll() {
        log.info("get all students");
        return studentService.getAll();
    }

    @GetMapping(value = "/{id}")
    public Student get(@PathVariable("id") Long id) {
        log.info("get student id " + id);
        return studentService.get(id);

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Student save(@RequestBody Student student) {
        log.info("save student" + student);
        return studentService.save(student);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        log.info("delete student id" + id);
        studentService.delete(id);
    }

}
