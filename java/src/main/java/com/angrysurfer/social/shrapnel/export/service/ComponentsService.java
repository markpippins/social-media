package com.angrysurfer.social.shrapnel.export.service;

import com.angrysurfer.social.shrapnel.export.component.field.FieldTypeEnum;
import com.angrysurfer.social.shrapnel.export.service.model.export.DataSource;
import com.angrysurfer.social.shrapnel.export.service.model.export.Export;
import com.angrysurfer.social.shrapnel.export.service.model.export.Field;
import com.angrysurfer.social.shrapnel.export.service.model.export.FieldType;
import com.angrysurfer.social.shrapnel.export.service.model.qbe.Query;
import com.angrysurfer.social.shrapnel.export.service.repository.export.*;
import com.angrysurfer.social.shrapnel.export.service.repository.qbe.ColumnRepository;
import com.angrysurfer.social.shrapnel.export.service.repository.qbe.JoinRepository;
import com.angrysurfer.social.shrapnel.export.service.repository.qbe.QueryRepository;
import com.angrysurfer.social.shrapnel.export.service.repository.qbe.TableRepository;
import com.angrysurfer.social.shrapnel.export.service.repository.style.PdfPageSizeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
public class ComponentsService {

	@Resource
	FieldRepository fieldRepository;

	@Resource
	DataSourceRepository dataSourceRepository;

	@Resource
	ExportRepository exportRepository;

	@Resource
	FieldTypeRepository fieldTypeRepository;

	@Resource
	PdfPageSizeRepository pdfPageSizeRepository;

	@Resource
	TableRepository tableRepository;

	@Resource
	ColumnRepository columnRepository;

	@Resource
	QueryRepository queryRepository;

	@Resource
	JoinRepository joinRepository;

	@Resource
	ComponentsService componentsService;

	public DataSource createDataSource(Query query) {
		DataSource ds = new DataSource();
		ds.setName(query.getName());
		ds.setQuery(query);
		dataSourceRepository.save(ds);
		return ds;
	}

	public Export createExport(Query query) {
		Export export = new Export();
		export.setName(query.getName());
		export.setFields(createFields(query));
		export.setDataSource(createDataSource(query));
		exportRepository.save(export);
		return export;
	}

	public Field createField(String name, String propertyName, String label, Integer index) {
		Field field = new Field();
		field.setName(name);
		field.setPropertyName(propertyName);
		field.setLabel(label);
		field.setIndex(index);
		field.setFieldType(fieldTypeRepository
				.findById(Integer.valueOf(FieldTypeEnum.STRING.getCode()))
				.orElseThrow(() -> new IllegalArgumentException()));
		fieldRepository.save(field);
		return field;
	}

	public Set< Field > createFields(Query query) {
		Set< Field > fields = new HashSet<>();
		query.getColumns().forEach(column -> fields.add(createField(column.getName(), column.getName(),
				column.getName().toUpperCase(Locale.ROOT), column.getIndex())));
		return fields;
	}

	public FieldType createFieldType(FieldTypeEnum fieldType) {
		FieldType ft = new FieldType();
		ft.setName(fieldType.name());
		ft.setCode(fieldType.getCode());
		fieldTypeRepository.save(ft);
		return ft;
	}
}
