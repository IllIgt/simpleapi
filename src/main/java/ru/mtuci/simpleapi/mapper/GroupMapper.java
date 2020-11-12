package ru.mtuci.simpleapi.mapper;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import ru.mtuci.simpleapi.dto.CourseDTO;
import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.dto.StudentDTO;
import ru.mtuci.simpleapi.model.Course;
import ru.mtuci.simpleapi.model.Group;
import ru.mtuci.simpleapi.model.Student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class GroupMapper extends PropertyMap<Group, GroupDTO> {

    private final Converter<List<Student>, List<StudentDTO>> studentConverter;
    private final Converter<List<Course>, List<CourseDTO>> courseConverter;

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

        this.courseConverter = new AbstractConverter<List<Course>, List<CourseDTO>>() {
            @Override
            protected List<CourseDTO> convert(List<Course> courses) {
                List<CourseDTO> coursesDTOs = new ArrayList<>();
                for ( final Course course : courses){
                    CourseDTO courseDTO = new CourseDTO();
                    courseDTO.setId(course.getId());
                    courseDTO.setCode(course.getCode());
                    courseDTO.setName(course.getName());
                    courseDTO.setHours(course.getHours());
                    courseDTO.setElective(course.isElective());
                    courseDTO.setGroups(course.getGroups()
                            .stream()
                            .map(Group::getId)
                            .collect(Collectors.toList()));
                    coursesDTOs.add(courseDTO);
                }
                    return coursesDTOs;
            }
        };
    }

    protected void configure() {
        using(studentConverter).map(source.getStudents()).setStudents(null);
        using(courseConverter).map(source.getCourses()).setCourses(null);
    }
};

