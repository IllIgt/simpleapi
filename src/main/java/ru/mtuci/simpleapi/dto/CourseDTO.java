package ru.mtuci.simpleapi.dto;

import lombok.Data;

import java.util.List;


@Data
public class CourseDTO {
    Long id;
    String code;
    String name;
    String hours;
    Boolean isElective;
    List<GroupDTO> groups;
}