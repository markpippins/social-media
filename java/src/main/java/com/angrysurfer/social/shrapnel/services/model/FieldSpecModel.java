package com.angrysurfer.social.shrapnel.services.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "field_spec")
@Getter
@Setter
@Entity
public class FieldSpecModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "propertyName", nullable = false)
    private String propertyName;

    private String headerLabel;

    @Column(name = "property_type", nullable = false)
    private String type;

    @Column(name = "column_index", nullable = false)
    private Integer index;
}
