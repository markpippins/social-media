package com.angrysurfer.social.shrapnel.services.service.validation;

import com.angrysurfer.social.shrapnel.services.service.ExportsService;
import com.angrysurfer.social.shrapnel.services.service.Request;
import com.angrysurfer.social.shrapnel.services.service.exception.InvalidExportRequestException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;

@Service
public class RequestValidator implements IRequestValidator {

    @Resource
    ExportsService exportsService;

    private Validator validator;

    @PostConstruct
    private void initialize() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public void validate(Request request) {

        Set<ConstraintViolation> violations = new HashSet<>();
        violations.addAll(this.validator.validate(request, IRequestValidation.RequestExport.class));

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            violations.forEach(v -> sb.append(v.getPropertyPath()).append(" ").append(v.getMessage()).append("\n"));
            throw new InvalidExportRequestException(String.format("Invalid Export Request:\n%s", sb.toString()));
        }

        if (!Arrays.asList(ExportsService.CSV, ExportsService.PDF, ExportsService.XLSX).contains(request.getFileType().toLowerCase(Locale.ROOT)))
            throw new InvalidExportRequestException(String.format("Unknown file extension: %s.", request.getFileType()));

        if (Objects.isNull(exportsService.getFactory(request)))
            throw new InvalidExportRequestException(String.format("No factory found for %s.", request.getName()));
    }
}
