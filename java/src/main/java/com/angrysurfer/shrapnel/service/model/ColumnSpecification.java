package com.angrysurfer.shrapnel.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ColumnSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "propertyName", nullable = false)
    private String propertyName;

    private String headerLabel;

    @Column(name = "property_type", nullable = false)
    private String type;
}
