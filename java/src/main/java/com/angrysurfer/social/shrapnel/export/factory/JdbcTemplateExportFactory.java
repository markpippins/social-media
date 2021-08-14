package com.angrysurfer.social.shrapnel.export.factory;

import com.angrysurfer.social.shrapnel.export.Export;
import com.angrysurfer.social.shrapnel.export.IExport;
import com.angrysurfer.social.shrapnel.export.component.field.IField;
import com.angrysurfer.social.shrapnel.export.component.property.PropertyMapAccessor;
import com.angrysurfer.social.shrapnel.export.service.Request;
import com.itextpdf.kernel.geom.PageSize;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class JdbcTemplateExportFactory implements IExportFactory {

	private Request request;

	private com.angrysurfer.social.shrapnel.export.service.model.Export export;

	public JdbcTemplateExportFactory(Request request, com.angrysurfer.social.shrapnel.export.service.model.Export export) {
		this.request = request;
		this.export = export;
	}

	@Override
	public String getExportName() {
		return request.getName();
	}

	@Override
	public IExport newInstance() {
		PageSize pageSize = Objects.nonNull(getExport().getPdfPageSize()) ?
				                    new PageSize(getExport().getPdfPageSize().getWidth(), getExport().getPdfPageSize().getHeight()) :
				                    getExport().hasCustomHeight() ?
						                    new PageSize(getExport().getCustomWidth(), getExport().getCustomHeight()) :
						                    PageSize.LETTER;

		return new Export(getExportName(), getExport().getFields()
				.stream()
				.sorted(Comparator.comparing(IField::getIndex))
				.collect(Collectors.toList())) {

			@Override
			public PageSize getPdfPageSize() {
				return pageSize;
			}

			@Override
			public void init() {
				setPropertyAccessor(new PropertyMapAccessor());
			}
		};
	}
}