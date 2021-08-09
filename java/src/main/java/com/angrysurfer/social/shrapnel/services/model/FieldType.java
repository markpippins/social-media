package com.angrysurfer.social.shrapnel.services.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "field_type_model")
public class FieldType {

    @Id
    @Column(name = "code", nullable = false)
    private Integer code;

    @Column(name = "name", nullable = false)
    private String name;
}
