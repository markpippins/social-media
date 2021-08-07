package com.angrysurfer.social.shrapnel;

import com.angrysurfer.social.shrapnel.component.property.Types;
import com.angrysurfer.social.shrapnel.services.model.ColumnSpecModel;
import com.angrysurfer.social.shrapnel.services.model.DataSourceModel;
import com.angrysurfer.social.shrapnel.services.model.ExportModel;
import com.angrysurfer.social.shrapnel.services.repository.ColumnSpecModelRepository;
import com.angrysurfer.social.shrapnel.services.repository.DataSourceModelRepository;
import com.angrysurfer.social.shrapnel.services.repository.ExportModelRepository;
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

        ColumnSpecModel idSpec = new ColumnSpecModel();
        idSpec.setName("id");
        idSpec.setType(Types.STRING);
        idSpec.setPropertyName("id");
        idSpec.setHeaderLabel("id");
        idSpec = columnSpecModelRepository.save(idSpec);

        ColumnSpecModel nameSpec = new ColumnSpecModel();
        nameSpec.setName("name");
        nameSpec.setType(Types.STRING);
        nameSpec.setPropertyName("name");
        nameSpec.setHeaderLabel("name");
        nameSpec = columnSpecModelRepository.save(nameSpec);

        ExportModel export = new ExportModel();
        export.setName("test-export");
        export.getColumnSpecs().add(idSpec);
        export.getColumnSpecs().add(nameSpec);
        exportModelRepository.save(export);

        DataSourceModel dataSourceModel = new DataSourceModel();
        dataSourceModel.setQuery("select * from forum;");
        dataSourceModel.setName("forum-list");
        dataSourceModelRepository.save(dataSourceModel);

        ColumnSpecModel idSpec2 = new ColumnSpecModel();
        idSpec2.setName("id");
        idSpec2.setType(Types.STRING);
        idSpec2.setPropertyName("id");
        idSpec2.setHeaderLabel("id");
        idSpec2 = columnSpecModelRepository.save(idSpec2);

        ColumnSpecModel nameSpec2 = new ColumnSpecModel();
        nameSpec2.setName("email");
        nameSpec2.setType(Types.STRING);
        nameSpec2.setPropertyName("email");
        nameSpec2.setHeaderLabel("email");
        nameSpec2 = columnSpecModelRepository.save(nameSpec2);

        ColumnSpecModel nameSpec3 = new ColumnSpecModel();
        nameSpec3.setName("alias");
        nameSpec3.setType(Types.STRING);
        nameSpec3.setPropertyName("alias");
        nameSpec3.setHeaderLabel("alias");
        nameSpec3 = columnSpecModelRepository.save(nameSpec3);

        ExportModel export2 = new ExportModel();
        export2.setName("user-list");
        export2.getColumnSpecs().add(idSpec2);
        export2.getColumnSpecs().add(nameSpec2);
        export2.getColumnSpecs().add(nameSpec3);
        exportModelRepository.save(export2);

        DataSourceModel dataSourceModel2 = new DataSourceModel();
        dataSourceModel2.setQuery("select * from user;");
        dataSourceModel2.setName("user-list");
        dataSourceModelRepository.save(dataSourceModel2);
    }
}