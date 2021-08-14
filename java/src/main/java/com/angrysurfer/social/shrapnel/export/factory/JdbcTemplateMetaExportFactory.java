package com.angrysurfer.social.shrapnel.export.factory;

import com.angrysurfer.social.shrapnel.export.service.Request;
import com.angrysurfer.social.shrapnel.export.service.model.export.DataSource;
import com.angrysurfer.social.shrapnel.export.service.model.export.Export;
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
		Export export = exportRepository.findByName(request.getName());
		return Objects.nonNull(export) && export.isConfigured();
	}

	@Override
	public IExportFactory newInstance(Request request) {
		final Export export = exportRepository.findByName(request.getName());
		final DataSource dataSource = dataSourceRepository.findByName(request.getName());

		return new JdbcTemplateExportFactory(request, export) {

			@Override
			public Collection getData() {
				Collection data = Collections.EMPTY_LIST;
				String sql = Objects.nonNull(dataSource.getQuery()) ?
						             dataSource.getQuery().getSQL() :
						             FileUtil.getSQL(dataSource.getQueryName());

				if (Objects.nonNull(sql))
					data = (Collection) jdbcTemplate.query(sql, new HashMapResultSetExtractor(export));

				return data;
			}
		};
	}
}
