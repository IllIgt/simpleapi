package ru.mtuci.simpleapi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mtuci.simpleapi.dao.StudentRepository;
import ru.mtuci.simpleapi.dto.StudentDTO;
import ru.mtuci.simpleapi.mapper.StudentMapper;
import ru.mtuci.simpleapi.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public StudentServiceImpl(
            StudentRepository studentRepository,
            ModelMapper modelMapper) {

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

    @Override
    public StudentDTO updateStudent(StudentDTO studentDTO) {
        Student newStudent = modelMapper.map(studentDTO, Student.class);
        Optional<Student> savedStudent = studentRepository.findById(studentDTO.getId());
        if(savedStudent.isPresent()) {
            Student student = savedStudent.get();
            student.setName(newStudent.getName());
            student.setSurname(newStudent.getSurname());
            student.setGroup(newStudent.getGroup());
            return modelMapper.map(studentRepository.save(student), StudentDTO.class);
        } else {
            return modelMapper.map(studentRepository.save(newStudent), StudentDTO.class);
        }
    }
}
