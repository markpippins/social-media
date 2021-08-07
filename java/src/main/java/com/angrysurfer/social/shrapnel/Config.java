package com.angrysurfer.social.shrapnel;

import com.angrysurfer.social.shrapnel.component.property.Types;
import com.angrysurfer.social.shrapnel.service.model.ColumnSpecModel;
import com.angrysurfer.social.shrapnel.service.model.ExportModel;
import com.angrysurfer.social.shrapnel.service.repository.ColumnSpecModelRepository;
import com.angrysurfer.social.shrapnel.service.repository.DataSourceModelRepository;
import com.angrysurfer.social.shrapnel.service.repository.ExportModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
class Config implements CommandLineRunner {

    @Resource
    ColumnSpecModelRepository columnSpecModelRepository;

    @Resource
    DataSourceModelRepository dataSourceModelRepository;

    @Resource
    ExportModelRepository exportModelRepository;

    @Override
    public void run(String... args) throws Exception {

        ColumnSpecModel pathSpec = new ColumnSpecModel();
        pathSpec.setName("path");
        pathSpec.setType(Types.STRING);
        pathSpec.setPropertyName("path");
        pathSpec.setHeaderLabel("File path");
        pathSpec = columnSpecModelRepository.save(pathSpec);

        ColumnSpecModel nameSpec = new ColumnSpecModel();
        nameSpec.setName("name");
        nameSpec.setType(Types.STRING);
        nameSpec.setPropertyName("name");
        nameSpec.setHeaderLabel("File Name");
        nameSpec = columnSpecModelRepository.save(nameSpec);

        ExportModel export = new ExportModel();
        export.setName("test-export");
        export.getColumnSpecs().add(pathSpec);
        export.getColumnSpecs().add(nameSpec);

        exportModelRepository.save(export);
    }
}