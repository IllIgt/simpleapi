package ru.mtuci.simpleapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.mtuci.simpleapi.dao.StudentRepository;
import ru.mtuci.simpleapi.model.Student;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final StudentRepository studentRepository;

    @Autowired
    public CommandLineAppStartupRunner(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String...args) throws Exception {
        System.out.println(studentRepository.findById(1L).get());

    }
}
