package com.bcc.grp.generic.helpers.validators.implementations;

import com.bcc.grp.generic.helpers.dataclass.GenericSummary;
import com.bcc.grp.generic.helpers.validators.annotations.ValidGenericSummary;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class GenericSummaryValidityChecker implements ConstraintValidator<ValidGenericSummary, GenericSummary> {

    @Override
    public boolean isValid(GenericSummary genericSummary, ConstraintValidatorContext context) {
        return /*!(((genericSummary.getState() == 2 || genericSummary.getState() == 3)
                && (genericSummary.getType() == 1 || genericSummary.getDesignation() == 1))
                || (genericSummary.getType() == 1 && genericSummary.getDesignation() != 1)
                || (genericSummary.getType() != 1 && genericSummary.getDesignation() == 1));*/ true;
    }
}
