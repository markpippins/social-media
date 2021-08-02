package com.angrysurfer.social.shrapnel.service.impl;

import com.angrysurfer.social.shrapnel.Export;
import com.angrysurfer.social.shrapnel.ExportFactory;
import com.angrysurfer.social.shrapnel.TabularExport;
import com.angrysurfer.social.shrapnel.component.ColumnSpec;
import com.angrysurfer.social.shrapnel.service.ExportRequest;
import com.angrysurfer.social.shrapnel.service.ExportsFactoryService;
import com.angrysurfer.social.shrapnel.service.mapping.ColumnSpecMapper;
import com.angrysurfer.social.shrapnel.service.model.DataSourceModel;
import com.angrysurfer.social.shrapnel.service.model.ExportModel;
import com.angrysurfer.social.shrapnel.service.repository.ColumnSpecModelRepository;
import com.angrysurfer.social.shrapnel.service.repository.DataSourceModelRepository;
import com.angrysurfer.social.shrapnel.service.repository.ExportModelRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExportsFactoryServiceImpl implements ExportsFactoryService {

    @Resource
    ColumnSpecModelRepository columnSpecModelRepository;

    @Resource
    DataSourceModelRepository dataSourceModelRepository;

    @Resource
    ExportModelRepository exportModelRepository;

    @Override
    public boolean canExport(ExportRequest request) {
        return Objects.nonNull(exportModelRepository.findByName(request.getExportName()));
    }

    @Override
    public ExportFactory getFactory(ExportRequest request) {

        DataSourceModel dataSource = dataSourceModelRepository.findByName(request.getExportName());

        return new ExportFactory() {
            @Override
            public Collection<Object> getData() {
                return Objects.nonNull(dataSource) ? dataSource.getData() : Collections.EMPTY_LIST;
            }

            @Override
            public String getExportName() {
                return request.getExportName();
            }

            @Override
            public Class getExportClass() {
                return TabularExport.class;
            }

            @Override
            public Export newInstance() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

                ExportModel export = exportModelRepository.findByName(request.getExportName());
                List<ColumnSpec> columns = export.getColumnSpecs().stream().map(cs -> ColumnSpecMapper.create(cs)).collect(Collectors.toList());
                return new TabularExport(getExportName(), columns);
            }
        };
    }
}
