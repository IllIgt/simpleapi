package ru.mtuci.simpleapi.dto;

import lombok.Data;

@Data
public class StudentDTO {
    Long id;
    String name;
    String surname;
    Long groupId;
}
