package com.angrysurfer.social.shrapnel;

import com.angrysurfer.social.shrapnel.component.property.Types;
import com.angrysurfer.social.shrapnel.services.model.DataSourceModel;
import com.angrysurfer.social.shrapnel.services.model.ExportModel;
import com.angrysurfer.social.shrapnel.services.model.FieldSpecModel;
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

        DataSourceModel forumData = new DataSourceModel();
        forumData.setQuery("get-forums");
        forumData.setName("forum-list");
        dataSourceModelRepository.save(forumData);

        FieldSpecModel idSpec1 = new FieldSpecModel();
        idSpec1.setName("id");
        idSpec1.setType(Types.STRING);
        idSpec1.setPropertyName("id");
        idSpec1.setHeaderLabel("id");
        idSpec1.setIndex(1);
        idSpec1 = columnSpecModelRepository.save(idSpec1);

        FieldSpecModel nameSpec = new FieldSpecModel();
        nameSpec.setName("name");
        nameSpec.setType(Types.STRING);
        nameSpec.setPropertyName("name");
        nameSpec.setHeaderLabel("name");
        nameSpec.setIndex(2);
        nameSpec = columnSpecModelRepository.save(nameSpec);

        ExportModel forumExport = new ExportModel();
        forumExport.setName("forum-list");
        forumExport.getFieldSpecs().add(idSpec1);
        forumExport.getFieldSpecs().add(nameSpec);
        forumExport.setDataSource(forumData);
        exportModelRepository.save(forumExport);

        DataSourceModel userData = new DataSourceModel();
        userData.setQuery("get-users");
        userData.setName("user-list");
        dataSourceModelRepository.save(userData);

        FieldSpecModel idSpec2 = new FieldSpecModel();
        idSpec2.setName("id");
        idSpec2.setType(Types.STRING);
        idSpec2.setPropertyName("id");
        idSpec2.setHeaderLabel("id");
        idSpec2.setIndex(0);
        idSpec2 = columnSpecModelRepository.save(idSpec2);

        FieldSpecModel emailSpec = new FieldSpecModel();
        emailSpec.setName("email");
        emailSpec.setType(Types.STRING);
        emailSpec.setPropertyName("email");
        emailSpec.setHeaderLabel("email");
        emailSpec.setIndex(3);
        emailSpec = columnSpecModelRepository.save(emailSpec);

        FieldSpecModel aliasSpec = new FieldSpecModel();
        aliasSpec.setName("alias");
        aliasSpec.setType(Types.STRING);
        aliasSpec.setPropertyName("alias");
        aliasSpec.setHeaderLabel("alias");
        aliasSpec.setIndex(2);
        aliasSpec = columnSpecModelRepository.save(aliasSpec);

        ExportModel userExport = new ExportModel();
        userExport.setName("user-list");
        userExport.getFieldSpecs().add(idSpec2);
        userExport.getFieldSpecs().add(aliasSpec);
        userExport.getFieldSpecs().add(emailSpec);
        userExport.setDataSource(userData);
        exportModelRepository.save(userExport);
    }
}