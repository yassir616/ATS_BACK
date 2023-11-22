package com.soa.vie.takaful.entitymodels;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BordereauxEncaissement extends AbstractBaseEntity{
    @Column
    private String refBordereau;
    @Column
    private String compteBancaire;

    @Column
    private String pointVente;
    @Column
    private String montantTotal;

    @OneToMany(mappedBy = "bordereauEncaissement", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Encaissement> listEncaissement; 
}
