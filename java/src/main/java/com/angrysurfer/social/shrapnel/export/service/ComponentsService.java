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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
public class ComponentsService {

    @Resource
    FieldSpecRepository fieldSpecRepository;

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

    public Export createExport(Query query) {
        Export export = new Export();
        export.setName(query.getName());
        export.setFields(createFields(query));
        export.setDataSource(createDataSource(query));
        exportRepository.save(export);
        return export;
    }

    private Set< Field> createFields(Query query) {
        Set<Field> fields = new HashSet<>();

        query.getColumns().forEach(column -> {
            Field field = new Field();

            field.setName(column.getName());
            field.setPropertyName(column.getName());
            field.setLabel(column.getName().toUpperCase(Locale.ROOT));
            field.setIndex(column.getIndex());
            field.setFieldType(fieldTypeRepository
                    .findById(Integer.valueOf(FieldTypeEnum.STRING.getCode()))
                    .orElseThrow(() -> new IllegalArgumentException()));

            fieldSpecRepository.save(field);
            fields.add(field);
        });

        return fields;
    }

    private DataSource createDataSource(Query query) {
        DataSource ds = new DataSource();
        ds.setName(query.getName());
        ds.setQuery(query);
        dataSourceRepository.save(ds);
        return ds;
    }

    public FieldType createFieldType(FieldTypeEnum fieldType) {
        FieldType ft = new FieldType();
        ft.setName(fieldType.name());
        ft.setCode(fieldType.getCode());
        return ft;
    }
}