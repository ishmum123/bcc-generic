package com.bcc.grp.uom.helpers.validators.implementations;

import com.bcc.grp.uom.helpers.dataclass.UomSummary;
import com.bcc.grp.uom.helpers.validators.annotations.ValidUomSummary;
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
