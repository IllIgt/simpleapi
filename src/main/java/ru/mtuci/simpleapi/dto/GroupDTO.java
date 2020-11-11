package ru.mtuci.simpleapi.dto;

import lombok.Data;
import ru.mtuci.simpleapi.model.Student;

import java.util.List;


@Data
public class GroupDTO {
    Long id;
    String code;
    String specialization;
    List<StudentDTO> students;
}
