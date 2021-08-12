package com.angrysurfer.social.shrapnel.services.service.validation;

import com.angrysurfer.social.shrapnel.services.exception.InvalidExportRequestException;
import com.angrysurfer.social.shrapnel.services.service.Request;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

@Service
public class RequestValidator implements IRequestValidator {

    private Validator validator;

    @PostConstruct
    private void initialize() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public void validate(Request request) {

        Set<ConstraintViolation> violations = new HashSet<>();
        violations.addAll(this.validator.validate(request, IRequestValidation.RequestExport.class));

        if (!violations.isEmpty())
            throw new InvalidExportRequestException();
    }
}
