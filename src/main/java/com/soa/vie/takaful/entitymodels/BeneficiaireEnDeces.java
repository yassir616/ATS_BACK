package com.soa.vie.takaful.entitymodels;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class BeneficiaireEnDeces extends AbstractBaseEntity{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column
    private String nom;

    @Column
    private String prenom;

    @Column
    private String rib;

    @Column
    private String cin;

}
