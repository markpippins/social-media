package com.angrysurfer.social.shrapnel.export.service.model.export;

import com.angrysurfer.social.shrapnel.Config;
import com.angrysurfer.social.shrapnel.export.component.field.IField;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.util.*;


@Getter
@Setter
@Entity
@Table(name = "export_model")//, schema = "shrapnel")
public class Export {

	@ManyToOne
	@JoinColumn(name = "data_source_id")
	public DataSource dataSource;

	@ManyToOne
	@JoinColumn(name = "page_size_id")
	public PdfPageSize pdfPageSize;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "export_model_field_spec", joinColumns = { @JoinColumn(name = "model_id") }, inverseJoinColumns = {
			@JoinColumn(name = "field_spec_id") })
	@Getter
	private Set< Field > fields = new HashSet<>();

	@Column(name = "custom_width", nullable = true)
	private Long customWidth;

	@Column(name = "custom_height", nullable = true)
	private Long customHeight;

	@Transient
	public boolean isConfigured() {

		final boolean[] isConfigured = { true };

		if (Objects.isNull(getName()) ||
				    (Objects.isNull(getDataSource()) && Objects.isNull(getDataSource().getQueryName())))
			isConfigured[ 0 ] = false;

		Map< Integer, IField > indexMap = new HashMap<>();
		getFields().forEach(field -> {
			if (Objects.isNull(field.getPropertyName())
//					    || indexMap.containsKey(field.getIndex())
					    || Objects.isNull(field.getType())
					    || Objects.isNull(field.getIndex()))
				isConfigured[ 0 ] = false;
//  			indexMap.put(field.getIndex(), field);
		});

		if (Objects.isNull(getDataSource().getQuery())) {
			File sql = new File(Config.getInstance().getProperty(Config.SQL_FOLDER) + getDataSource().getQueryName() + ".sql");
			if (!sql.exists())
				isConfigured[ 0 ] = false;
		} else if (Objects.isNull(getDataSource().getQuery().getSQL()))
			isConfigured[ 0 ] = false;

		return isConfigured[ 0 ];
	}

	@Transient
	public boolean hasCustomHeight() {

		return Objects.nonNull(customWidth) && Objects.nonNull(customHeight) && customWidth > 0 && customHeight > 0;
	}
}