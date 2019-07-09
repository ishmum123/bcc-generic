package com.bcc.grp.generic.database.repositories;

import com.bcc.grp.generic.database.entities.Generic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GenericRepository extends JpaRepository<Generic, String> {
    Iterable<Generic> findByNameEn(String nameEn);
    Iterable<Generic> findByNameBn(String nameBn);
    @Query("SELECT u FROM Generic u WHERE nameEn LIKE CONCAT('%',:name,'%') OR nameBn LIKE CONCAT('%',:name,'%')")
    Iterable<Generic> findByName(@Param("name") String name);
}
