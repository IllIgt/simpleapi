package ru.mtuci.simpleapi.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Access(AccessType.FIELD)
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractBaseEntity {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Long id;

}
