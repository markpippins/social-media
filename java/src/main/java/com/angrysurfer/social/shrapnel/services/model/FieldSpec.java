package com.angrysurfer.social.shrapnel.services.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "field_spec_model")
public class FieldSpec {

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

    @ManyToOne
    @JoinColumn(name = "field_type_id")
    public FieldType fieldType;

    public com.angrysurfer.social.shrapnel.component.FieldSpec createFieldSpec() {
        com.angrysurfer.social.shrapnel.component.FieldSpec result = new com.angrysurfer.social.shrapnel.component.FieldSpec();
        result.setPropertyName(getPropertyName());
        result.setLabel(getLabel());
        result.setType(com.angrysurfer.social.shrapnel.component.FieldSpec.FieldTypeEnum.from(fieldType.getName()));
        result.setIndex(getIndex());
        return result;
    }
}
