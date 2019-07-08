package com.bcc.grp.uom.helpers.dataclass;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class IdHolder {

    @NotBlank
    private String oid;

}
