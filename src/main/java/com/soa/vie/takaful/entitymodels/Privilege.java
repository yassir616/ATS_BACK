package com.soa.vie.takaful.entitymodels;

import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Privilege extends AbstractBaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = -8148870523575970028L;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "privileges")
    @JsonIgnore
    private List<Role> roles;
}