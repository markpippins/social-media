package com.angrysurfer.social.shrapnel.services.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "ColumnSpecModel")
@Getter
@Setter
@Entity
public class ColumnSpecModel {

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

}
