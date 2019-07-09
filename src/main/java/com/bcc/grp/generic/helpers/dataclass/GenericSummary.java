package com.bcc.grp.generic.helpers.dataclass;

import com.bcc.grp.generic.helpers.validators.annotations.ValidGenericSummary;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ValidGenericSummary
public class GenericSummary {

    @NotBlank
    @Size(max = 50)
    private String nameEn;

    @Size(max = 50)
    private String nameBn;

    @NotBlank
    @Size(max = 50)
    private String description;

}
