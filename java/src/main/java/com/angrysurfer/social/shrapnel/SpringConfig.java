package com.angrysurfer.social.shrapnel;

import com.angrysurfer.social.shrapnel.component.field.FieldTypeEnum;
import com.angrysurfer.social.shrapnel.services.model.ComponentCreator;
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
//@Configuration
//@PropertySource({"classpath:persistence-multiple-db.properties"})
//@EnableJpaRepositories(
//        basePackages = "com.angrysurfer.social.shrapnel.services.model",
//        entityManagerFactoryRef = "shrapnelEntityManager",
//        transactionManagerRef = "shrapnelTransactionManager"
//)
class SpringConfig implements CommandLineRunner {

    private static final String SHRAPNEL_MODEL_PACKAGE = "com.angrysurfer.social.shrapnel.services.model";
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
//    @Autowired
//    private Environment env;

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

        Arrays.stream(FieldTypeEnum.values())
                .forEach(fieldType -> fieldTypeRepository.save(ComponentCreator.createFieldType(fieldType)));

        com.angrysurfer.social.shrapnel.services.model.DataSource forumData = new com.angrysurfer.social.shrapnel.services.model.DataSource();
        forumData.setQueryName("get-forums");
        forumData.setName("forum-list");
        dataSourceRepository.save(forumData);

        FieldSpec idSpec1 = new FieldSpec();
        idSpec1.setName("id");
        idSpec1.setPropertyName("id");
        idSpec1.setLabel("id");
        idSpec1.setIndex(1);
        idSpec1.setFieldType(fieldTypeRepository
                .findById(Integer.valueOf(FieldTypeEnum.STRING.getCode()))
                .orElseThrow(() -> new IllegalArgumentException()));

        idSpec1 = fieldSpecRepository.save(idSpec1);

        FieldSpec nameSpec = new FieldSpec();
        nameSpec.setName("name");
        nameSpec.setPropertyName("name");
        nameSpec.setLabel("name");
        nameSpec.setIndex(2);
        nameSpec.setFieldType(fieldTypeRepository
                .findById(Integer.valueOf(FieldTypeEnum.STRING.getCode()))
                .orElseThrow(() -> new IllegalArgumentException()));
        nameSpec = fieldSpecRepository.save(nameSpec);

        Export forumExport = new Export();
        forumExport.setName("forum-list");
        forumExport.getFieldSpecs().add(idSpec1);
        forumExport.getFieldSpecs().add(nameSpec);
        forumExport.setDataSource(forumData);
        forumExport.setPdfPageSize(pdfPageSizeRepository.findByName("A0"));
        exportRepository.save(forumExport);

        com.angrysurfer.social.shrapnel.services.model.DataSource userData = new com.angrysurfer.social.shrapnel.services.model.DataSource();
        userData.setQueryName("get-users");
        userData.setName("user-list");
        dataSourceRepository.save(userData);

        FieldSpec idSpec2 = new FieldSpec();
        idSpec2.setName("id");
        idSpec2.setPropertyName("id");
        idSpec2.setLabel("id");
        idSpec2.setIndex(0);
        idSpec2.setFieldType(fieldTypeRepository
                .findById(Integer.valueOf(FieldTypeEnum.STRING.getCode()))
                .orElseThrow(() -> new IllegalArgumentException()));
        idSpec2 = fieldSpecRepository.save(idSpec2);

        FieldSpec emailSpec = new FieldSpec();
        emailSpec.setName("email");
        emailSpec.setFieldType(fieldTypeRepository
                .findById(Integer.valueOf(FieldTypeEnum.STRING.getCode()))
                .orElseThrow(() -> new IllegalArgumentException()));
        emailSpec.setPropertyName("email");
        emailSpec.setLabel("email");
        emailSpec.setIndex(3);
        emailSpec = fieldSpecRepository.save(emailSpec);

        FieldSpec aliasSpec = new FieldSpec();
        aliasSpec.setName("alias");
        aliasSpec.setFieldType(fieldTypeRepository
                .findById(Integer.valueOf(FieldTypeEnum.STRING.getCode()))
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
        userExport.setPdfPageSize(pdfPageSizeRepository.findByName("A0"));
        exportRepository.save(userExport);

//        em.name as "export",
//                fs.property_name as property,
//        fs.label as label,
//                ft.name as field_type
    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean shrapnelEntityManager() {
//        LocalContainerEntityManagerFactoryBean em
//                = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(shrapnelDataSource());
//        em.setPackagesToScan(
//                new String[]{SHRAPNEL_MODEL_PACKAGE});
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto",
//                env.getProperty("hibernate.hbm2ddl.auto"));
//        properties.put("hibernate.dialect",
//                env.getProperty("hibernate.dialect"));
//        em.setJpaPropertyMap(properties);
//
//        return em;
//    }
//
//    @Bean
//    public DataSource shrapnelDataSource() {
//
//        DriverManagerDataSource dataSource
//                = new DriverManagerDataSource();
//        dataSource.setDriverClassName(
//                env.getProperty("jdbc.driverClassName"));
//        dataSource.setUrl(env.getProperty("shrapnel.jdbc.url"));
//        dataSource.setUsername(env.getProperty("jdbc.user"));
//        dataSource.setPassword(env.getProperty("jdbc.pass"));
//
//        return dataSource;
//    }
//
//    @Bean
//    public PlatformTransactionManager shrapnelTransactionManager() {
//
//        JpaTransactionManager transactionManager
//                = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(
//                shrapnelEntityManager().getObject());
//        return transactionManager;
//    }
}