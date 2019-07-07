package com.synesis.bcc.structure.helpers.dataclass;

import com.synesis.bcc.structure.helpers.validators.annotations.ValidUomSummary;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ValidUomSummary
public class UomSummary {

    @NotBlank
    @Size(max = 50)
    private String nameEn;

    @Size(max = 50)
    private String nameBn;

    @NotBlank
    @Size(max = 50)
    private String description;

}
