package com.angrysurfer.social.shrapnel.services.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "field_spec_model")//, schema = "shrapnel")
public class FieldSpec {

    @ManyToOne
    @JoinColumn(name = "field_type_id")
    public FieldType fieldType;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "property_name", nullable = false)
    private String propertyName;
    @Column(name = "label", nullable = false)
    private String label;
    @Column(name = "field_index", nullable = false)
    private Integer index;

}
