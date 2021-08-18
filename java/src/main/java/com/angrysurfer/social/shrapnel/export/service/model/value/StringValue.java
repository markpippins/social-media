package com.angrysurfer.social.shrapnel.export.service.model.value;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Setter
@Entity
@Table(name = "value_string", schema = "shrapnel")
public class StringValue {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "value", nullable = false)
	private String value;
}
