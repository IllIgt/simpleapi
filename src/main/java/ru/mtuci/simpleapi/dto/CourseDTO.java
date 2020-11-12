package ru.mtuci.simpleapi.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;


@Data
public class CourseDTO {
    Long id;
    String code;
    String name;
    Integer hours;
    boolean isElective;
    List<Long> groups;
}