package ru.mtuci.simpleapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Student extends AbstractBaseEntity {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotNull
    private Integer group_id;
}
