package com.bcc.grp.generic.controllers;

import com.bcc.grp.generic.database.entities.Generic;
import com.bcc.grp.generic.helpers.dataclass.IdHolder;
import com.bcc.grp.generic.helpers.dataclass.GenericSummary;
import com.bcc.grp.generic.services.GenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("generic")
public class GenericController {

    private final GenericService genericService;

    @PostMapping("get-list")
    public List<Generic> getUsers() {
        return genericService.getUsers();
    }

    @PostMapping("get-by-oid")
    public Generic getUser(@RequestBody IdHolder holder) {
        return genericService.findById(holder.getOid());
    }

    // TODO: Fix API Naming Convention & Payload
    @PostMapping("get-by")
    public Iterable<Generic> getUsersByName(
            @RequestParam(value = "firstname", required = false) String firstname,
            @RequestParam(value = "lastname", required = false) String lastname,
            @RequestParam(value = "name", required = false) String name
    ) {
        if (!StringUtils.isEmpty(firstname)) return genericService.getUsersByFirstname(firstname);
        if (!StringUtils.isEmpty(lastname)) return genericService.getUsersByLastname(lastname);
        if (!StringUtils.isEmpty(name)) return genericService.getUsersByName(name);
        return new ArrayList<>();
    }

    @PostMapping
    public void addUser(@RequestBody @Valid GenericSummary genericSummary) throws Exception {
        genericService.addGenericSummary(genericSummary);
    }

}
