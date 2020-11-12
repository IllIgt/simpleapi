package ru.mtuci.simpleapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
public class Course {

    @Id
    @SequenceGenerator(name = "course_seq", sequenceName = "course_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    private Long id;

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotNull
    private Integer hours;

    private boolean elective;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "course_group",
             joinColumns = { @JoinColumn(name = "course_id") },
             inverseJoinColumns = { @JoinColumn(name = "group_id") })
    private List<Group> groups = new ArrayList<>();
}
