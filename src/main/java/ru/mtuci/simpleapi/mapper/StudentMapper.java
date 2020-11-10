package ru.mtuci.simpleapi.mapper;


import org.modelmapper.PropertyMap;
import ru.mtuci.simpleapi.dto.StudentDTO;
import ru.mtuci.simpleapi.model.Student;

public class StudentMapper extends PropertyMap<Student, StudentDTO> {

    protected void configure() {
        map().setGroupId(source.getGroup().getId());
        }
};
