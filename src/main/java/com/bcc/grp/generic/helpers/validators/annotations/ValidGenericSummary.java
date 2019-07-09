package com.bcc.grp.generic.helpers.validators.annotations;


import com.bcc.grp.generic.helpers.validators.implementations.GenericSummaryValidityChecker;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = GenericSummaryValidityChecker.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGenericSummary {
    String message() default "generic data {invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
