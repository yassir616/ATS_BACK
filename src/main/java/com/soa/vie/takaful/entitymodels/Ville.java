package com.soa.vie.takaful.entitymodels;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ville {
    

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToOne
    private Region region;
}
