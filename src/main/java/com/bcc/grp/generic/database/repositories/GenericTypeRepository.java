package com.bcc.grp.generic.database.repositories;

import com.bcc.grp.generic.database.entities.GenericType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GenericTypeRepository extends JpaRepository<GenericType, String> {
    Iterable<GenericType> findByNameEn(String nameEn);

    Iterable<GenericType> findByNameBn(String nameBn);

    @Query("SELECT u FROM GenericType u WHERE nameEn LIKE CONCAT('%',:name,'%') OR nameBn LIKE CONCAT('%',:name,'%')")
    Iterable<GenericType> findByName(@Param("name") String name);
}
