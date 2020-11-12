package ru.mtuci.simpleapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class Group {

    @Id
    @SequenceGenerator(name = "group_seq", sequenceName = "group_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq")
    private Long id;

    @NotBlank
    private String code;

    @NotBlank
    private String specialization;

    @OneToMany(mappedBy = "group")
    private List<Student> students = new ArrayList<>();

    @ManyToMany(mappedBy = "groups")
    private List<Course> courses = new ArrayList<>();
}
