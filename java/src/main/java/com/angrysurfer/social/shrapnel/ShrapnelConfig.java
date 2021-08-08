package com.angrysurfer.social.shrapnel;

import com.angrysurfer.social.shrapnel.component.FieldTypeEnum;
import com.angrysurfer.social.shrapnel.services.model.FieldSpecModel;
import com.angrysurfer.social.shrapnel.services.model.DataSourceModel;
import com.angrysurfer.social.shrapnel.services.model.ExportModel;
import com.angrysurfer.social.shrapnel.services.model.FieldTypeModel;
import com.angrysurfer.social.shrapnel.services.repository.FieldSpecModelRepository;
import com.angrysurfer.social.shrapnel.services.repository.DataSourceModelRepository;
import com.angrysurfer.social.shrapnel.services.repository.ExportModelRepository;
import com.angrysurfer.social.shrapnel.services.repository.FieldTypeModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
class ShrapnelConfig implements CommandLineRunner {

    @Resource
    FieldSpecModelRepository fieldSpecModelRepository;

    @Resource
    DataSourceModelRepository dataSourceModelRepository;

    @Resource
    ExportModelRepository exportModelRepository;

    @Resource
    FieldTypeModelRepository fieldTypeModelRepository;

    @Override
    public void run(String... args) throws Exception {

        FieldTypeModel f1 = new FieldTypeModel();
        f1.setName(FieldTypeEnum.STRING.name());
        f1.setCode(FieldTypeEnum.STRING.getCode());
        fieldTypeModelRepository.save(f1);

        FieldTypeModel f2 = new FieldTypeModel();
        f2.setName(FieldTypeEnum.BOOLEAN.name());
        f2.setCode(FieldTypeEnum.BOOLEAN.getCode());
        fieldTypeModelRepository.save(f2);

        DataSourceModel forumData = new DataSourceModel();
        forumData.setQuery("get-forums");
        forumData.setName("forum-list");
        dataSourceModelRepository.save(forumData);

        FieldSpecModel idSpec1 = new FieldSpecModel();
        idSpec1.setName("id");
        idSpec1.setPropertyName("id");
        idSpec1.setLabel("id");
        idSpec1.setIndex(1);
        idSpec1.setFieldType(f1);
        idSpec1 = fieldSpecModelRepository.save(idSpec1);

        FieldSpecModel nameSpec = new FieldSpecModel();
        nameSpec.setName("name");
        nameSpec.setPropertyName("name");
        nameSpec.setLabel("name");
        nameSpec.setIndex(2);
        nameSpec.setFieldType(f1);
        nameSpec = fieldSpecModelRepository.save(nameSpec);

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
        idSpec2.setPropertyName("id");
        idSpec2.setLabel("id");
        idSpec2.setIndex(0);
        idSpec2.setFieldType(f1);
        idSpec2 = fieldSpecModelRepository.save(idSpec2);

        FieldSpecModel emailSpec = new FieldSpecModel();
        emailSpec.setName("email");
        emailSpec.setFieldType(f1);
        emailSpec.setPropertyName("email");
        emailSpec.setLabel("email");
        emailSpec.setIndex(3);
        emailSpec = fieldSpecModelRepository.save(emailSpec);

        FieldSpecModel aliasSpec = new FieldSpecModel();
        aliasSpec.setName("alias");
        aliasSpec.setFieldType(f1);
        aliasSpec.setPropertyName("alias");
        aliasSpec.setLabel("alias");
        aliasSpec.setIndex(2);
        aliasSpec = fieldSpecModelRepository.save(aliasSpec);

        ExportModel userExport = new ExportModel();
        userExport.setName("user-list");
        userExport.getFieldSpecs().add(idSpec2);
        userExport.getFieldSpecs().add(aliasSpec);
        userExport.getFieldSpecs().add(emailSpec);
        userExport.setDataSource(userData);
        exportModelRepository.save(userExport);
    }
}