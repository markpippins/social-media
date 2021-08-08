package com.angrysurfer.social.shrapnel.services.model;

import com.angrysurfer.social.shrapnel.component.FieldSpec;
import com.angrysurfer.social.shrapnel.component.FieldTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "field_spec_model")
public class FieldSpecModel {

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

    @Column(name = "property_type", nullable = false)
    private String type;

    @Column(name = "field_index", nullable = false)
    private Integer index;

    public FieldSpec createFieldSpec() {
        FieldSpec result = new FieldSpec();
        result.setPropertyName(getPropertyName());
        result.setLabel(getLabel());
        result.setType(FieldTypeEnum.from(getType()));
        result.setIndex(getIndex());
        return result;
    }
}
