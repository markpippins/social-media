package com.angrysurfer.shrapnel.export.service.model.value;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Objects;

@Slf4j
@Getter
@Setter
@Entity
@Table(name = "value", schema = "shrapnel")
public class Value {

	@ManyToOne
	@JoinColumn(name = "value_type_code")
	public ValueType valueType;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	public ValueTypeEnum getType() {
		return Objects.isNull(this.valueType) ? null : ValueTypeEnum.from(this.valueType.getCode());
	}
}
