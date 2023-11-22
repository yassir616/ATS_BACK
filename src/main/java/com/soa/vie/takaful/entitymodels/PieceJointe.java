package com.soa.vie.takaful.entitymodels;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PieceJointe extends AbstractBaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column
    private String code;

    @Column
    private String libelle;

    @Column
    private String necessity;

    @ManyToMany(mappedBy = "pointVentes")
    @JsonIgnore
    private List<TakafulUser> users;

    @ManyToMany(mappedBy = "pieceJointes")
    @JsonIgnore
    private List<DecesProduitCauseRestitution> decesProduitCauseRestitutions;

    @ManyToMany(mappedBy = "pieceJointe")
    @JsonIgnore
    private List<TypePrestationProduit> typePrestationProduit;

    @OneToMany(mappedBy = "pieceJointe")
    @JsonIgnore
    private List<FileToUploadSinistre> filesSinistre;

}