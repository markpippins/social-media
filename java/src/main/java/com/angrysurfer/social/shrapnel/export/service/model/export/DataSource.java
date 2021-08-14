package com.angrysurfer.social.shrapnel.export.service.model.export;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "data_source_model")//, schema = "shrapnel")
public class DataSource {

	@ManyToOne
	@JoinColumn(name = "query_id", nullable = true)
	public com.angrysurfer.social.shrapnel.export.service.model.qbe.Query query;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "query", nullable = false)
	private String queryName;
}
