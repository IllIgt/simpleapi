package ru.mtuci.simpleapi.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id")
    private Group group;

}
