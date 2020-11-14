package ru.mtuci.simpleapi.dto;

import lombok.Data;
import lombok.Generated;

import java.util.List;


@Generated
@Data
public class GroupDTO {
    Long id;
    String code;
    String specialization;
    List<Long> studentsId;
    List<Long> coursesId;
}
