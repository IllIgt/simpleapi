package ru.mtuci.simpleapi.mapper;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.model.Course;
import ru.mtuci.simpleapi.model.Group;
import ru.mtuci.simpleapi.model.Student;

import java.util.ArrayList;
import java.util.List;


public class GroupMapper extends PropertyMap<Group, GroupDTO> {

    private final Converter<List<Student>, List<Long>> studentConverter;
    private final Converter<List<Course>, List<Long>> courseConverter;

    public GroupMapper() {
        this.studentConverter = new AbstractConverter<List<Student>, List<Long>>() {

            @Override
            protected List<Long> convert(List<Student> students) {
                List<Long> studentIds = new ArrayList<>();
                for (final Student student : students) {
                    studentIds.add(student.getId());
                }
                return studentIds;
            }
        };

        this.courseConverter = new AbstractConverter<List<Course>, List<Long>>() {
            @Override
            protected List<Long> convert(List<Course> courses) {
                List<Long> coursesIds = new ArrayList<>();
                for ( final Course course : courses){
                    coursesIds.add(course.getId());
                }
                    return coursesIds;
            }
        };
    }

    protected void configure() {
        using(studentConverter).map(source.getStudents()).setStudentsId(null);
        using(courseConverter).map(source.getCourses()).setCoursesId(null);
    }
};

