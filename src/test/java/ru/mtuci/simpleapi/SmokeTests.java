package ru.mtuci.simpleapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mtuci.simpleapi.controller.StatusController;
import ru.mtuci.simpleapi.controller.StudentController;
import ru.mtuci.simpleapi.service.StudentServiceImpl;

@SpringBootTest
public class SmokeTests {

    @Autowired
    private StatusController statusController;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private StudentController studentControllerWithService = new StudentController(studentService);

    @Test
    public void contextLoads() throws Exception {
        assertThat(statusController).isNotNull();
        assertThat(studentController).isNotNull();
        assertThat(studentControllerWithService).isNotNull();
    }


}

