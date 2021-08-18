package com.angrysurfer.social.shrapnel.export.service.model.value;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "value_type", schema = "shrapnel")
public class ValueType {

	@Id
	@Column(name = "code", nullable = false)
	private Integer code;

	@Column(name = "table_name", nullable = false)
	private String tableName;
}
