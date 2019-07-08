package com.bcc.grp.uom.controllers;

import com.bcc.grp.uom.database.entities.Uom;
import com.bcc.grp.uom.helpers.dataclass.IdHolder;
import com.bcc.grp.uom.helpers.dataclass.UomSummary;
import com.bcc.grp.uom.services.UomService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("users")
public class UomController {

    private final UomService uomService;

    @PostMapping("get-list")
    public List<Uom> getUsers() {
        return uomService.getUsers();
    }

    @PostMapping("get-by-oid")
    public Uom getUser(@RequestBody IdHolder holder) {
        return uomService.findById(holder.getOid());
    }

    // TODO: Fix API Naming Convention & Payload
    @PostMapping("get-by")
    public Iterable<Uom> getUsersByName(
            @RequestParam(value = "firstname", required = false) String firstname,
            @RequestParam(value = "lastname", required = false) String lastname,
            @RequestParam(value = "name", required = false) String name
    ) {
        if (!StringUtils.isEmpty(firstname)) return uomService.getUsersByFirstname(firstname);
        if (!StringUtils.isEmpty(lastname)) return uomService.getUsersByLastname(lastname);
        if (!StringUtils.isEmpty(name)) return uomService.getUsersByName(name);
        return new ArrayList<>();
    }

    @PostMapping
    public void addUser(@RequestBody @Valid UomSummary uomSummary) throws Exception {
        uomService.addUserSummary(uomSummary);
    }

}
