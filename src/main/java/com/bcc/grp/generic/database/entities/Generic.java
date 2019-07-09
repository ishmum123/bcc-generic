package com.bcc.grp.generic.database.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "generics")
public class Generic {

    @Id
    private String oid;

    private String nameEn;
    private String nameBn;
    private String description;

    @ManyToOne
    @Where(clause = "isDeleted = FALSE")
    private GenericType type;

    private Boolean isDeleted;

    @CreatedBy
    private String createdBy;

    @CreationTimestamp
    private Date createdOn;

    // TODO: Implement @UpdatedBy Somehow
    // IDEA: Existint Annotation, PrePersist
    private String updatedBy;

    @UpdateTimestamp
    private Date updatedOn;

}
