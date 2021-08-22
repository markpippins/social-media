package com.angrysurfer.shrapnel.export.service.model.export;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "data_source", schema = "shrapnel")
public class DBDataSource {

	@ManyToOne
	@JoinColumn(name = "query_id", nullable = true)
	public com.angrysurfer.shrapnel.export.service.model.sqlgen.Query query;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "scriptName", nullable = true)
	private String scriptName;
}
