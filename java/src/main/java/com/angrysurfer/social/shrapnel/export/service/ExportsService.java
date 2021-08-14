package com.angrysurfer.social.shrapnel.export.service;

import com.angrysurfer.social.shrapnel.export.factory.IExportFactory;
import com.angrysurfer.social.shrapnel.export.factory.IMetaExportFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Getter
@Service
public class ExportsService implements IExportsService {

	@Resource
	List< IMetaExportFactory > metaFactories;

	@Resource
	List< IExportFactory > exporterFactories;

	private boolean factoryRegistered(Request request) {
		return metaFactories.stream().anyMatch(fac -> fac.hasFactory(request));
	}

	private IMetaExportFactory getMetaFactory(Request request) {
		return metaFactories.stream().filter(fac -> fac.hasFactory(request)).findFirst().orElseGet(() -> null);
	}

	public IExportFactory getFactory(Request request) {
		return getExporterFactories().stream().filter(fac -> fac.getExportName().equalsIgnoreCase(request.getName())).findFirst()
				.orElseGet(() -> factoryRegistered(request) ? getMetaFactory(request).newInstance(request) : null);
	}
}
