package com.angrysurfer.shrapnel;

import com.angrysurfer.shrapnel.export.service.model.sqlgen.Column;
import com.angrysurfer.shrapnel.export.service.model.sqlgen.Join;
import com.angrysurfer.shrapnel.export.service.model.sqlgen.Query;
import com.angrysurfer.shrapnel.export.service.model.sqlgen.Table;
import com.angrysurfer.shrapnel.export.service.model.style.Style;
import com.angrysurfer.shrapnel.export.service.model.style.StyleTypeEnum;
import com.angrysurfer.shrapnel.export.service.repository.sqlgen.ColumnRepository;
import com.angrysurfer.shrapnel.export.service.repository.sqlgen.JoinRepository;
import com.angrysurfer.shrapnel.export.service.repository.sqlgen.QueryRepository;
import com.angrysurfer.shrapnel.export.service.repository.sqlgen.TableRepository;
import com.angrysurfer.shrapnel.export.service.repository.style.StyleRepository;
import com.angrysurfer.shrapnel.export.service.repository.style.StyleTypeRepository;
import com.angrysurfer.shrapnel.export.component.field.FieldTypeEnum;
import com.angrysurfer.shrapnel.export.service.ComponentsService;
import com.angrysurfer.shrapnel.export.service.model.export.DBDataSource;
import com.angrysurfer.shrapnel.export.service.model.export.DBExport;
import com.angrysurfer.shrapnel.export.service.model.export.DBField;
import com.angrysurfer.shrapnel.export.service.model.sqlgen.JoinTypeEnum;
import com.angrysurfer.shrapnel.export.service.model.style.PdfPageSize;
import com.angrysurfer.shrapnel.export.service.repository.export.DataSourceRepository;
import com.angrysurfer.shrapnel.export.service.repository.export.ExportRepository;
import com.angrysurfer.shrapnel.export.service.repository.export.FieldRepository;
import com.angrysurfer.shrapnel.export.service.repository.export.FieldTypeRepository;
import com.angrysurfer.shrapnel.export.service.repository.style.PdfPageSizeRepository;
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
	FieldRepository fieldRepository;

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
	StyleRepository styleRepository;

	@Resource
	StyleTypeRepository styleTypeRepository;

	@Resource
	ComponentsService componentsService;

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

		Arrays.stream(StyleTypeEnum.values())
				.forEach(styleType -> componentsService.createStyleType(styleType));

		Style style = new Style();
		style.setName("Default");
		style.setStyleType(styleTypeRepository.findByName("FONT"));
		style.setValue("Calibri");
		styleRepository.save(style);

		Arrays.stream(FieldTypeEnum.values())
				.forEach(fieldType -> componentsService.createFieldType(fieldType));

		Arrays.stream(JoinTypeEnum.values())
				.forEach(joinType -> componentsService.createJoinType(joinType));

		DBDataSource forumData = new DBDataSource();
		forumData.setScriptName("get-forums");
		forumData.setName("forum-list");
		dataSourceRepository.save(forumData);

		DBField idSpec1 = new DBField();
		idSpec1.setName("id");
		idSpec1.setPropertyName("id");
		idSpec1.setLabel("id");
		idSpec1.setIndex(1);
		idSpec1.setFieldType(fieldTypeRepository
				.findById(Integer.valueOf(FieldTypeEnum.STRING.getCode()))
				.orElseThrow(() -> new IllegalArgumentException()));

		idSpec1 = fieldRepository.save(idSpec1);

		DBField nameSpec = new DBField();
		nameSpec.setName("name");
		nameSpec.setPropertyName("name");
		nameSpec.setLabel("name");
		nameSpec.setIndex(2);
		nameSpec.setFieldType(fieldTypeRepository
				.findById(Integer.valueOf(FieldTypeEnum.STRING.getCode()))
				.orElseThrow(() -> new IllegalArgumentException()));
		nameSpec = fieldRepository.save(nameSpec);

		DBExport forumDBExport = new DBExport();
		forumDBExport.setName("forum-list");
		forumDBExport.getFields().add(idSpec1);
		forumDBExport.getFields().add(nameSpec);
		forumDBExport.setDataSource(forumData);
		forumDBExport.setPdfPageSize(pdfPageSizeRepository.findByName("A0"));
		forumDBExport.getStyles().add(style);
		exportRepository.save(forumDBExport);

		DBDataSource userData = new DBDataSource();
		userData.setScriptName("get-users");
		userData.setName("user-list");
		dataSourceRepository.save(userData);

		DBField idSpec2 = new DBField();
		idSpec2.setName("id");
		idSpec2.setPropertyName("id");
		idSpec2.setLabel("id");
		idSpec2.setIndex(0);
		idSpec2.setFieldType(fieldTypeRepository
				.findById(Integer.valueOf(FieldTypeEnum.STRING.getCode()))
				.orElseThrow(() -> new IllegalArgumentException()));
		idSpec2 = fieldRepository.save(idSpec2);

		DBField emailSpec = new DBField();
		emailSpec.setName("email");
		emailSpec.setFieldType(fieldTypeRepository
				.findById(Integer.valueOf(FieldTypeEnum.STRING.getCode()))
				.orElseThrow(() -> new IllegalArgumentException()));
		emailSpec.setPropertyName("email");
		emailSpec.setLabel("email");
		emailSpec.setIndex(3);
		emailSpec = fieldRepository.save(emailSpec);

		DBField aliasSpec = new DBField();
		aliasSpec.setName("alias");
		aliasSpec.setFieldType(fieldTypeRepository
				.findById(Integer.valueOf(FieldTypeEnum.STRING.getCode()))
				.orElseThrow(() -> new IllegalArgumentException()));
		aliasSpec.setPropertyName("alias");
		aliasSpec.setLabel("alias");
		aliasSpec.setIndex(2);
		aliasSpec = fieldRepository.save(aliasSpec);

		DBExport userDBExport = new DBExport();
		userDBExport.setName("user-list");
		userDBExport.getFields().add(idSpec2);
		userDBExport.getFields().add(aliasSpec);
		userDBExport.getFields().add(emailSpec);
		userDBExport.setDataSource(userData);
		userDBExport.setPdfPageSize(pdfPageSizeRepository.findByName("A4"));
		userDBExport.getStyles().add(style);
		exportRepository.save(userDBExport);

		Table people = new Table();
		people.setSchema("social");
		people.setName("user");
		tableRepository.save(people);

		Column personId = new Column();
		personId.setName("id");
		personId.setTable(people);
		personId.setIndex(0);
		columnRepository.save(personId);

		Column alias = new Column();
		alias.setName("alias");
		alias.setTable(people);
		alias.setIndex(1);
		columnRepository.save(alias);

		Column email = new Column();
		email.setName("email");
		email.setTable(people);
		email.setIndex(3);
		columnRepository.save(email);

		people.getColumns().add(personId);
		people.getColumns().add(alias);
		people.getColumns().add(email);
		tableRepository.save(people);

		Table posts = new Table();
		posts.setSchema("social");
		posts.setName("post");
		tableRepository.save(posts);

		Column postId = new Column();
		postId.setName("id");
		postId.setTable(posts);
		postId.setIndex(0);
		columnRepository.save(postId);

		Column postedById = new Column();
		postedById.setName("posted_by_id");
		postedById.setTable(posts);
		postedById.setIndex(1);
		columnRepository.save(postedById);

		Column text = new Column();
		text.setName("text");
		text.setTable(posts);
		text.setIndex(2);
		columnRepository.save(text);

		Join join = new Join();
		join.setJoinColumnA(personId);
		join.setJoinColumnB(postedById);
		joinRepository.save(join);

		Query query = new Query();
		query.setName("get-posts");
		query.setSchema("sample");
		query.getColumns().add(personId);
		query.getColumns().add(alias);
		query.getColumns().add(email);
		query.getColumns().add(postId);
		query.getColumns().add(postedById);
		query.getColumns().add(text);
		query.getJoins().add(join);

		queryRepository.save(query);

		log.info(query.getSQL());
		DBExport export = componentsService.createExport(query);
	}
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
//    public DBDataSource shrapnelDataSource() {
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
