package com.bcc.grp.uom.database.repositories;

import com.bcc.grp.uom.database.entities.Uom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UomRepository extends JpaRepository<Uom, String> {
    Iterable<Uom> findByNameEn(String nameEn);
    Iterable<Uom> findByNameBn(String nameBn);
    @Query("SELECT u FROM Uom u WHERE nameEn LIKE CONCAT('%',:name,'%') OR nameBn LIKE CONCAT('%',:name,'%')")
    Iterable<Uom> findByName(@Param("name") String name);
}
