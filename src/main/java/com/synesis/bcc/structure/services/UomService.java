package com.synesis.bcc.structure.services;

import com.synesis.bcc.structure.database.entities.Uom;
import com.synesis.bcc.structure.database.repositories.UomRepository;
import com.synesis.bcc.structure.helpers.dataclass.UomSummary;
import com.synesis.bcc.structure.helpers.exceptions.ServiceExceptionHolder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UomService {

    private final UomRepository uomRepository;
    private final ModelMapper modelMapper;

    public List<Uom> getUsers() {
        return uomRepository.findAll();
    }

    public void addUserSummary(UomSummary summary) {
        uomRepository.save(modelMapper.map(summary, Uom.class));
    }

    public Iterable<Uom> getUsersByFirstname(String firstname) {
        return uomRepository.findByNameEn(firstname);
    }

    public Iterable<Uom> getUsersByLastname(String lastname) {
        return uomRepository.findByNameBn(lastname);
    }

    public Iterable<Uom> getUsersByName(String name) {
        return uomRepository.findByName(name);
    }

    public Uom findById(String uuid) {
        return uomRepository.findById(uuid).orElseThrow(() -> new ServiceExceptionHolder.UomNotFoundException(uuid));
    }
}
