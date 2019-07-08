package com.bcc.grp.uom.helpers.validators.annotations;


import com.bcc.grp.uom.helpers.validators.implementations.UomSummaryValidityChecker;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UomSummaryValidityChecker.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUomSummary {
    String message() default "uom data {invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
