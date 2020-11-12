package ru.mtuci.simpleapi.mapper;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import ru.mtuci.simpleapi.dto.CourseDTO;
import ru.mtuci.simpleapi.model.Course;
import ru.mtuci.simpleapi.model.Group;

import java.util.ArrayList;
import java.util.List;


public class CourseMapper extends PropertyMap<Course, CourseDTO> {

    private final Converter<List<Group>, List<Long>> groupConverter;

    public CourseMapper() {
        this.groupConverter = new AbstractConverter<List<Group>, List<Long>>() {

            @Override
            protected List<Long> convert(List<Group> Groups) {
                List<Long> GroupIds = new ArrayList<>();
                for (final Group group : Groups) {
                    GroupIds.add(group.getId());
                }
                return GroupIds;
            }
        };
    }

    protected void configure(){
        using(groupConverter).map(source.getGroups()).setGroups(null);
    }
};