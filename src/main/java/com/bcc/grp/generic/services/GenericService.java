package com.bcc.grp.generic.services;

import com.bcc.grp.generic.database.entities.Generic;
import com.bcc.grp.generic.database.repositories.GenericRepository;
import com.bcc.grp.generic.helpers.dataclass.GenericSummary;
import com.bcc.grp.generic.helpers.exceptions.ServiceExceptionHolder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenericService {

    private final GenericRepository genericRepository;
    private final ModelMapper modelMapper;

    public List<Generic> getUsers() {
        return genericRepository.findAll();
    }

    public void addGenericSummary(GenericSummary summary) {
        genericRepository.save(modelMapper.map(summary, Generic.class));
    }

    public Iterable<Generic> getUsersByFirstname(String firstname) {
        return genericRepository.findByNameEn(firstname);
    }

    public Iterable<Generic> getUsersByLastname(String lastname) {
        return genericRepository.findByNameBn(lastname);
    }

    public Iterable<Generic> getUsersByName(String name) {
        return genericRepository.findByName(name);
    }

    public Generic findById(String uuid) {
        return genericRepository.findById(uuid).orElseThrow(() -> new ServiceExceptionHolder.GenericNotFoundException(uuid));
    }
}
