package com.synesis.bcc.structure.database.repositories;

import com.synesis.bcc.structure.database.entities.Uom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UomRepository extends JpaRepository<Uom, String> {
    Iterable<Uom> findByNameEn(String nameEn);
    Iterable<Uom> findByNameBn(String nameBn);
    @Query("SELECT u FROM Uom u WHERE nameEn LIKE CONCAT('%',:name,'%') OR nameBn LIKE CONCAT('%',:name,'%')")
    Iterable<Uom> findByName(@Param("name") String name);
}
