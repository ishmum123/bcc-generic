package com.synesis.bcc.structure.helpers.validators.implementations;

import com.synesis.bcc.structure.helpers.dataclass.UomSummary;
import com.synesis.bcc.structure.helpers.validators.annotations.ValidUomSummary;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UomSummaryValidityChecker implements ConstraintValidator<ValidUomSummary, UomSummary> {

    @Override
    public boolean isValid(UomSummary uomSummary, ConstraintValidatorContext context) {
        return /*!(((uomSummary.getState() == 2 || uomSummary.getState() == 3)
                && (uomSummary.getType() == 1 || uomSummary.getDesignation() == 1))
                || (uomSummary.getType() == 1 && uomSummary.getDesignation() != 1)
                || (uomSummary.getType() != 1 && uomSummary.getDesignation() == 1));*/ true;
    }
}
