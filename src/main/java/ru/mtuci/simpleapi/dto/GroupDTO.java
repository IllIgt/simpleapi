package ru.mtuci.simpleapi.dto;

import lombok.Data;

import java.util.List;


@Data
public class GroupDTO {
    Long id;
    String code;
    String specialization;
    List<Long> studentsId;
    List<Long> coursesId;
}
