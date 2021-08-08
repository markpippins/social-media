package com.angrysurfer.social.shrapnel;

import com.angrysurfer.social.shrapnel.component.FieldTypeEnum;
import com.angrysurfer.social.shrapnel.services.model.FieldSpecModel;
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
class ShrapnelConfig implements CommandLineRunner {

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
        idSpec1.setType(FieldTypeEnum.STRING.name());
        idSpec1.setPropertyName("id");
        idSpec1.setLabel("id");
        idSpec1.setIndex(1);
        idSpec1 = columnSpecModelRepository.save(idSpec1);

        FieldSpecModel nameSpec = new FieldSpecModel();
        nameSpec.setName("name");
        nameSpec.setType(FieldTypeEnum.STRING.name());
        nameSpec.setPropertyName("name");
        nameSpec.setLabel("name");
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
        idSpec2.setType(FieldTypeEnum.STRING.name());
        idSpec2.setPropertyName("id");
        idSpec2.setLabel("id");
        idSpec2.setIndex(0);
        idSpec2 = columnSpecModelRepository.save(idSpec2);

        FieldSpecModel emailSpec = new FieldSpecModel();
        emailSpec.setName("email");
        emailSpec.setType(FieldTypeEnum.STRING.name());
        emailSpec.setPropertyName("email");
        emailSpec.setLabel("email");
        emailSpec.setIndex(3);
        emailSpec = columnSpecModelRepository.save(emailSpec);

        FieldSpecModel aliasSpec = new FieldSpecModel();
        aliasSpec.setName("alias");
        aliasSpec.setType(FieldTypeEnum.STRING.name());
        aliasSpec.setPropertyName("alias");
        aliasSpec.setLabel("alias");
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