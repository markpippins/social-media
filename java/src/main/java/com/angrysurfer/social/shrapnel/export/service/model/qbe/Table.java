package com.angrysurfer.social.shrapnel.export.service.model.qbe;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Column;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@javax.persistence.Table(name = "qbe_table")//, schema = "shrapnel")
public class Table {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "schema_name", nullable = false)
	private String schema;

	@OneToMany( fetch = FetchType.EAGER )
	@JoinTable( name = "qbe_table_columns", joinColumns = { @JoinColumn( name = "column_id" ) }, inverseJoinColumns = {
			@JoinColumn( name = "table_id" ) } )
	@Getter
	private Set< com.angrysurfer.social.shrapnel.export.service.model.qbe.Column> columns = new HashSet<>( );
}
