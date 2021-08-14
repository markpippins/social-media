package com.angrysurfer.social.shrapnel.export.service.model.qbe;

import com.angrysurfer.social.shrapnel.export.service.model.FieldType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@javax.persistence.Table(name = "qbe_column_model")//, schema = "shrapnel")
public class Column {

	@ManyToOne
	@JoinColumn(name = "field_type_id")
	public FieldType fieldType;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@javax.persistence.Column(name = "id", nullable = false)
	private Long id;

	@javax.persistence.Column(name = "name", nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "table_id")
	private Table table;
}
