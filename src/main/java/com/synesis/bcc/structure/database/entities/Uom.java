package com.synesis.bcc.structure.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table
public class Uom {

    @Id
    @Column
    private String oid;

    @Column
    private String nameEn;

    @Column
    private String nameBn;

    @Column
    private String description;

    @Column
    private String categoryOid;

    @Column
    private Boolean isDeleted;

}
