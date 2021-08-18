package com.angrysurfer.social.shrapnel.export.factory;

import com.angrysurfer.social.shrapnel.export.service.Request;
import com.angrysurfer.social.shrapnel.export.service.model.export.DBDataSource;
import com.angrysurfer.social.shrapnel.export.service.model.export.DBExport;
import com.angrysurfer.social.shrapnel.export.service.repository.export.DataSourceRepository;
import com.angrysurfer.social.shrapnel.export.service.repository.export.ExportRepository;
import com.angrysurfer.social.shrapnel.export.service.repository.mapping.HashMapResultSetExtractor;
import com.angrysurfer.social.shrapnel.export.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Slf4j
@Component
public class JdbcTemplateMetaExportFactory implements IMetaExportFactory {

	@Resource
	JdbcTemplate jdbcTemplate;

	@Resource
	DataSourceRepository dataSourceRepository;

	@Resource
	ExportRepository exportRepository;

	@Override
	public boolean hasFactory(Request request) {
		DBExport export = exportRepository.findByName(request.getName());
		return Objects.nonNull(export) && export.isConfigured();
	}

	@Override
	public IExportFactory newInstance(Request request) {
		final DBExport     dbExport   = exportRepository.findByName(request.getName());
		final DBDataSource dataSource = dataSourceRepository.findByName(request.getName());

		return new JdbcTemplateExportFactory(request, dbExport) {

			@Override
			public Collection getData() {
				String sql = Objects.nonNull(dataSource.getScriptName()) ?
						             // load query from sql folder
						             // TODO: implement refreshable caching scheme for queries
						             FileUtil.getSQL(dataSource.getScriptName()) :
						             Objects.nonNull(dataSource.getQuery()) ?
								             // build query from db definition
								             dataSource.getQuery().getSQL() :
								             null;

				return Objects.nonNull(sql) ?
						       (Collection) jdbcTemplate.query(sql, new HashMapResultSetExtractor(dbExport)) :
						       Collections.EMPTY_LIST;
			}
		};
	}
}
