package com.angrysurfer.social.shrapnel.export.service.model;

import com.angrysurfer.social.shrapnel.export.component.field.FieldTypeEnum;
import com.angrysurfer.social.shrapnel.export.component.field.IField;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table( name = "field_spec_model" )//, schema = "shrapnel")
public class Field implements IField {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "id", nullable = false )
	private Long id;

	@ManyToOne
	@JoinColumn( name = "field_type_id" )
	public FieldType fieldType;

	@Column( name = "name", nullable = false )
	private String name;

	@Column( name = "property_name", nullable = false )
	private String propertyName;

	@Column( name = "label", nullable = false )
	private String label;

	@Column( name = "field_index", nullable = false )
	private Integer index;

	@Column( name = "is_calculated", nullable = false )
	private Boolean calculated = false;

	@Override
	public FieldTypeEnum getType( ) {
		return Objects.isNull( this.fieldType ) ? null : FieldTypeEnum.from( this.fieldType.getCode( ) );
	}
}