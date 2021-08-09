package com.angrysurfer.social.shrapnel;

import com.angrysurfer.social.shrapnel.services.model.DataSource;
import com.angrysurfer.social.shrapnel.services.model.Export;
import com.angrysurfer.social.shrapnel.services.model.FieldSpec;
import com.angrysurfer.social.shrapnel.services.model.FieldType;
import com.angrysurfer.social.shrapnel.services.repository.DataSourceModelRepository;
import com.angrysurfer.social.shrapnel.services.repository.ExportModelRepository;
import com.angrysurfer.social.shrapnel.services.repository.FieldSpecModelRepository;
import com.angrysurfer.social.shrapnel.services.repository.FieldTypeModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

@Slf4j
@Component
class SpringConfig implements CommandLineRunner {

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

        Arrays.stream(com.angrysurfer.social.shrapnel.component.FieldSpec.FieldTypeEnum.values())
                .forEach(fieldType -> {
                    FieldType ft = new FieldType();
                    ft.setName(fieldType.name());
                    ft.setCode(fieldType.getCode());

                    fieldTypeModelRepository.save(ft);
                });

        DataSource forumData = new DataSource();
        forumData.setQuery("get-forums");
        forumData.setName("forum-list");
        dataSourceModelRepository.save(forumData);

        FieldSpec idSpec1 = new FieldSpec();
        idSpec1.setName("id");
        idSpec1.setPropertyName("id");
        idSpec1.setLabel("id");
        idSpec1.setIndex(1);
        idSpec1.setFieldType(fieldTypeModelRepository
                .findById(Integer.valueOf(com.angrysurfer.social.shrapnel.component.FieldSpec.FieldTypeEnum.STRING.getCode()))
                .orElseThrow(() -> new IllegalArgumentException()));

        idSpec1 = fieldSpecModelRepository.save(idSpec1);

        FieldSpec nameSpec = new FieldSpec();
        nameSpec.setName("name");
        nameSpec.setPropertyName("name");
        nameSpec.setLabel("name");
        nameSpec.setIndex(2);
        nameSpec.setFieldType(fieldTypeModelRepository
                .findById(Integer.valueOf(com.angrysurfer.social.shrapnel.component.FieldSpec.FieldTypeEnum.STRING.getCode()))
                .orElseThrow(() -> new IllegalArgumentException()));
        nameSpec = fieldSpecModelRepository.save(nameSpec);

        Export forumExport = new Export();
        forumExport.setName("forum-list");
        forumExport.getFieldSpecs().add(idSpec1);
        forumExport.getFieldSpecs().add(nameSpec);
        forumExport.setDataSource(forumData);
        exportModelRepository.save(forumExport);

        DataSource userData = new DataSource();
        userData.setQuery("get-users");
        userData.setName("user-list");
        dataSourceModelRepository.save(userData);

        FieldSpec idSpec2 = new FieldSpec();
        idSpec2.setName("id");
        idSpec2.setPropertyName("id");
        idSpec2.setLabel("id");
        idSpec2.setIndex(0);
        idSpec2.setFieldType(fieldTypeModelRepository
                .findById(Integer.valueOf(com.angrysurfer.social.shrapnel.component.FieldSpec.FieldTypeEnum.STRING.getCode()))
                .orElseThrow(() -> new IllegalArgumentException()));
        idSpec2 = fieldSpecModelRepository.save(idSpec2);

        FieldSpec emailSpec = new FieldSpec();
        emailSpec.setName("email");
        emailSpec.setFieldType(fieldTypeModelRepository
                .findById(Integer.valueOf(com.angrysurfer.social.shrapnel.component.FieldSpec.FieldTypeEnum.STRING.getCode()))
                .orElseThrow(() -> new IllegalArgumentException()));
        emailSpec.setPropertyName("email");
        emailSpec.setLabel("email");
        emailSpec.setIndex(3);
        emailSpec = fieldSpecModelRepository.save(emailSpec);

        FieldSpec aliasSpec = new FieldSpec();
        aliasSpec.setName("alias");
        aliasSpec.setFieldType(fieldTypeModelRepository
                .findById(Integer.valueOf(com.angrysurfer.social.shrapnel.component.FieldSpec.FieldTypeEnum.STRING.getCode()))
                .orElseThrow(() -> new IllegalArgumentException()));
        aliasSpec.setPropertyName("alias");
        aliasSpec.setLabel("alias");
        aliasSpec.setIndex(2);
        aliasSpec = fieldSpecModelRepository.save(aliasSpec);

        Export userExport = new Export();
        userExport.setName("user-list");
        userExport.getFieldSpecs().add(idSpec2);
        userExport.getFieldSpecs().add(aliasSpec);
        userExport.getFieldSpecs().add(emailSpec);
        userExport.setDataSource(userData);
        exportModelRepository.save(userExport);
    }
}