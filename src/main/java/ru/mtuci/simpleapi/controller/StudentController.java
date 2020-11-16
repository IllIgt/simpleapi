package ru.mtuci.simpleapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.simpleapi.dto.CourseDTO;
import ru.mtuci.simpleapi.dto.StudentDTO;
import ru.mtuci.simpleapi.service.StudentService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//  TODO add responses for wrong requests
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
    public List<StudentDTO> getAll() {
        log.info("get all students");
        return studentService.getAll();
    }

    @GetMapping(value = "/{id}")
    public StudentDTO get(@PathVariable("id") Long id) {
        log.info("get student id " + id);
        return studentService.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public StudentDTO save(@RequestBody StudentDTO student, HttpServletResponse response) {
        log.info("save student");
        StudentDTO studentDTO = studentService.save(student);
        response.setHeader("Location", studentDTO.getId().toString());
        return studentDTO;
    }

    @PutMapping("/{id}")
    @ResponseBody
    public StudentDTO updateStudent (@PathVariable("id") long id,
                               @RequestBody StudentDTO student) {
        student.setId(id);
        log.info("update student");
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        log.info("delete student id" + id);
        studentService.delete(id);
    }

}
