package com.angrysurfer.shrapnel.export.service.model.value;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Setter
@Entity
@Table(name = "value_long", schema = "shrapnel")
public class LongValue {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "value", nullable = false)
	private Long value;
}
