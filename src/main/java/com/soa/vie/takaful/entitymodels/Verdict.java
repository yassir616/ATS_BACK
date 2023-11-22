package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Verdict extends AbstractBaseEntity{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column
    private String status;

    @Column
    private String type;
}