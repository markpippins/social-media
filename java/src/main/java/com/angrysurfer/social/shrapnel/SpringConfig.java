package com.angrysurfer.social.shrapnel;

import com.angrysurfer.social.shrapnel.services.model.DataSource;
import com.angrysurfer.social.shrapnel.services.model.Export;
import com.angrysurfer.social.shrapnel.services.model.FieldSpec;
import com.angrysurfer.social.shrapnel.services.model.PdfPageSize;
import com.angrysurfer.social.shrapnel.services.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

@Slf4j
@Component
class SpringConfig implements CommandLineRunner {

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

    @Override
    public void run(String... args) throws Exception {

        pdfPageSizeRepository.save(new PdfPageSize("A0", 2384, 3370));
        pdfPageSizeRepository.save(new PdfPageSize("A1", 1684, 2384));
        pdfPageSizeRepository.save(new PdfPageSize("A2", 1190, 1684));
        pdfPageSizeRepository.save(new PdfPageSize("A3", 842, 1190));
        pdfPageSizeRepository.save(new PdfPageSize("A4", 595, 842));
        pdfPageSizeRepository.save(new PdfPageSize("A5", 420, 595));
        pdfPageSizeRepository.save(new PdfPageSize("A6", 298, 420));
        pdfPageSizeRepository.save(new PdfPageSize("A7", 210, 298));
        pdfPageSizeRepository.save(new PdfPageSize("A8", 148, 210));
        pdfPageSizeRepository.save(new PdfPageSize("A9", 105, 547));
        pdfPageSizeRepository.save(new PdfPageSize("A10", 74, 105));

        pdfPageSizeRepository.save(new PdfPageSize("B0", 2834, 4008));
        pdfPageSizeRepository.save(new PdfPageSize("B1", 2004, 2834));
        pdfPageSizeRepository.save(new PdfPageSize("B2", 1417, 2004));
        pdfPageSizeRepository.save(new PdfPageSize("B3", 1000, 1417));
        pdfPageSizeRepository.save(new PdfPageSize("B4", 708, 1000));
        pdfPageSizeRepository.save(new PdfPageSize("B5", 498, 708));
        pdfPageSizeRepository.save(new PdfPageSize("B6", 354, 498));
        pdfPageSizeRepository.save(new PdfPageSize("B7", 249, 354));
        pdfPageSizeRepository.save(new PdfPageSize("B8", 175, 249));
        pdfPageSizeRepository.save(new PdfPageSize("B9", 124, 175));
        pdfPageSizeRepository.save(new PdfPageSize("B10", 88, 124));

        pdfPageSizeRepository.save(new PdfPageSize("LETTER", 612, 792));
        pdfPageSizeRepository.save(new PdfPageSize("LEGAL", 612, 1008));
        pdfPageSizeRepository.save(new PdfPageSize("TABLOID", 792, 1224));
        pdfPageSizeRepository.save(new PdfPageSize("LEDGER", 1224, 792));
        pdfPageSizeRepository.save(new PdfPageSize("EXECUTIVE", 522, 756));

        Arrays.stream(com.angrysurfer.social.shrapnel.component.FieldSpec.FieldTypeEnum.values())
                .forEach(fieldType -> fieldTypeRepository.save(ComponentCreator.createFieldType(fieldType)));

        DataSource forumData = new DataSource();
        forumData.setQueryName("get-forums");
        forumData.setName("forum-list");
        dataSourceRepository.save(forumData);

        FieldSpec idSpec1 = new FieldSpec();
        idSpec1.setName("id");
        idSpec1.setPropertyName("id");
        idSpec1.setLabel("id");
        idSpec1.setIndex(1);
        idSpec1.setFieldType(fieldTypeRepository
                .findById(Integer.valueOf(com.angrysurfer.social.shrapnel.component.FieldSpec.FieldTypeEnum.STRING.getCode()))
                .orElseThrow(() -> new IllegalArgumentException()));

        idSpec1 = fieldSpecRepository.save(idSpec1);

        FieldSpec nameSpec = new FieldSpec();
        nameSpec.setName("name");
        nameSpec.setPropertyName("name");
        nameSpec.setLabel("name");
        nameSpec.setIndex(2);
        nameSpec.setFieldType(fieldTypeRepository
                .findById(Integer.valueOf(com.angrysurfer.social.shrapnel.component.FieldSpec.FieldTypeEnum.STRING.getCode()))
                .orElseThrow(() -> new IllegalArgumentException()));
        nameSpec = fieldSpecRepository.save(nameSpec);

        Export forumExport = new Export();
        forumExport.setName("forum-list");
        forumExport.getFieldSpecs().add(idSpec1);
        forumExport.getFieldSpecs().add(nameSpec);
        forumExport.setDataSource(forumData);
        forumExport.setPdfPageSize(pdfPageSizeRepository.findByName("LEDGER"));
        exportRepository.save(forumExport);

        DataSource userData = new DataSource();
        userData.setQueryName("get-users");
        userData.setName("user-list");
        dataSourceRepository.save(userData);

        FieldSpec idSpec2 = new FieldSpec();
        idSpec2.setName("id");
        idSpec2.setPropertyName("id");
        idSpec2.setLabel("id");
        idSpec2.setIndex(0);
        idSpec2.setFieldType(fieldTypeRepository
                .findById(Integer.valueOf(com.angrysurfer.social.shrapnel.component.FieldSpec.FieldTypeEnum.STRING.getCode()))
                .orElseThrow(() -> new IllegalArgumentException()));
        idSpec2 = fieldSpecRepository.save(idSpec2);

        FieldSpec emailSpec = new FieldSpec();
        emailSpec.setName("email");
        emailSpec.setFieldType(fieldTypeRepository
                .findById(Integer.valueOf(com.angrysurfer.social.shrapnel.component.FieldSpec.FieldTypeEnum.STRING.getCode()))
                .orElseThrow(() -> new IllegalArgumentException()));
        emailSpec.setPropertyName("email");
        emailSpec.setLabel("email");
        emailSpec.setIndex(3);
        emailSpec = fieldSpecRepository.save(emailSpec);

        FieldSpec aliasSpec = new FieldSpec();
        aliasSpec.setName("alias");
        aliasSpec.setFieldType(fieldTypeRepository
                .findById(Integer.valueOf(com.angrysurfer.social.shrapnel.component.FieldSpec.FieldTypeEnum.STRING.getCode()))
                .orElseThrow(() -> new IllegalArgumentException()));
        aliasSpec.setPropertyName("alias");
        aliasSpec.setLabel("alias");
        aliasSpec.setIndex(2);
        aliasSpec = fieldSpecRepository.save(aliasSpec);

        Export userExport = new Export();
        userExport.setName("user-list");
        userExport.getFieldSpecs().add(idSpec2);
        userExport.getFieldSpecs().add(aliasSpec);
        userExport.getFieldSpecs().add(emailSpec);
        userExport.setDataSource(userData);
        userExport.setPdfPageSize(pdfPageSizeRepository.findByName("LEGAL"));
        exportRepository.save(userExport);
    }
}