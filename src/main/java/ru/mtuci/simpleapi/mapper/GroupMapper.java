package ru.mtuci.simpleapi.mapper;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.dto.StudentDTO;
import ru.mtuci.simpleapi.model.Group;
import ru.mtuci.simpleapi.model.Student;

import java.util.ArrayList;
import java.util.List;



public class GroupMapper extends PropertyMap<Group, GroupDTO> {

    private final Converter<List<Student>, List<StudentDTO>> studentConverter;

    public GroupMapper() {
        this.studentConverter = new AbstractConverter<List<Student>, List<StudentDTO>>() {

            @Override
            protected List<StudentDTO> convert(List<Student> students) {
                List<StudentDTO> studentDTOs = new ArrayList<>();
                for (final Student student : students) {
                    StudentDTO studentDTO = new StudentDTO();
                    studentDTO.setId(student.getId());
                    studentDTO.setName(student.getName());
                    studentDTO.setSurname(student.getSurname());
                    studentDTO.setGroupId(student.getGroup().getId());
                    studentDTOs.add(studentDTO);
                }
                return studentDTOs;
            }
        };
    }


    protected void configure() {
        using(studentConverter).map().setStudents(source.getStudents());
    }
};

