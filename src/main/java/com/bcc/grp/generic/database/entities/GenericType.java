package com.bcc.grp.generic.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "generic_types")
public class GenericType {

    @Id
    private String oid;

    private String nameEn;
    private String nameBn;
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "type")
    @Where(clause = "isDeleted = FALSE")
    private List<Generic> generic;

    private Boolean isDeleted;

    @CreatedBy
    private String createdBy;

    @CreationTimestamp
    private Date createdOn;

    // TODO: Implement @UpdatedBy Somehow
    // IDEA: Existing Annotation, PrePersist
    private String updatedBy;

    @UpdateTimestamp
    private Date updatedOn;

}
