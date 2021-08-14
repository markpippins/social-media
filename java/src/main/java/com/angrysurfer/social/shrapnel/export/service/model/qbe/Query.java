package com.angrysurfer.social.shrapnel.export.service.model.qbe;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@javax.persistence.Table(name = "qbe_query_model")//, schema = "shrapnel")
public class Query {

	public static String SELECT = "\n\tSELECT \n\t";

	public static String FROM = " \t\n\tFROM \n\t";

	public static String WHERE = " \t\n\tWHERE \n\t";

	public static String AND = " AND ";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@javax.persistence.Column(name = "id", nullable = false)
	private Long id;

	@javax.persistence.Column(name = "name", nullable = false)
	private String name;

	@Column(name = "schema_name", nullable = false)
	private String schema;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "query_column", joinColumns = { @JoinColumn(name = "query_id") }, inverseJoinColumns = {
			@JoinColumn(name = "column_id") })
	@Getter
	private Set< com.angrysurfer.social.shrapnel.export.service.model.qbe.Column > columns = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "join_column", joinColumns = { @JoinColumn(name = "join_id") }, inverseJoinColumns = {
			@JoinColumn(name = "column_id") })
	@Getter
	private Set< com.angrysurfer.social.shrapnel.export.service.model.qbe.Join > joins = new HashSet<>();

	@Transient
	public String getSQL() {
		return getSelect() + getFrom();
	}

	private String getSelect() {
		StringBuffer select = new StringBuffer(SELECT);
		select.append("\t");
		select.append(String.join(",\n\t\t", getColumns().stream()
				.sorted((c1, c2) -> c1.getTable().getName().compareTo(c2.getTable().getName()))
				.map(c -> getTableName(c, false) + "." + c.getName()).collect(Collectors.toList())));
		return select.toString();
	}


	private String getFrom() {
		StringBuffer from = new StringBuffer(FROM);
		from.append("\t");
		from.append(String.join(",\n\t\t", getColumns().stream()
				.sorted((c1, c2) -> c1.getTable().getName().compareTo(c2.getTable().getName()))
				.map(c -> getTableName(c, true))
				.distinct()
				.collect(Collectors.toList())));

		return from.toString();
	}

	private String getTableName(com.angrysurfer.social.shrapnel.export.service.model.qbe.Column column, boolean prefix) {

		return prefix ? column.getTable().getSchema() + "." + column.getTable().getName() + " " +
				       column.getTable().getName().replace("_", "") + "_" :
				       column.getTable().getName().replace("_", "") + "_";
	}
}

