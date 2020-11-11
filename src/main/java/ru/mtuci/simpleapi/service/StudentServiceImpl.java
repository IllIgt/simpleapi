package ru.mtuci.simpleapi.service;

import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mtuci.simpleapi.dao.StudentRepository;
import ru.mtuci.simpleapi.mapper.StudentMapper;
import ru.mtuci.simpleapi.model.Student;
import ru.mtuci.simpleapi.dto.StudentDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper) {

        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(new StudentMapper());
    }

    @Override
    public StudentDTO get(Long id) {
        //  TODO add null Handling
        return modelMapper.map(studentRepository.findById(id).orElse(new Student()), StudentDTO.class);
    }

    @Override
    public List<StudentDTO> getAll() {
        //  TODO add null Handling
        List<Student> students = studentRepository.findAll();

        return students.stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public StudentDTO save(StudentDTO studentDTO) {
        Student student = modelMapper.map(studentDTO, Student.class);
        return modelMapper.map(studentRepository.save(student), StudentDTO.class);
    }

    @Override
    public void delete(Long id) {
        studentRepository.delete(id);
        //  TODO add Exceptions Handling
    }
}
