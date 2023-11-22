package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "personneId")
@SQLDelete(sql = "UPDATE personne SET status = 'DELETED' WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "status <> 'DELETED'")
public class PersonneMorale extends Personne {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 10)
    private String abb;

    @Column
    private String raisonSociale;

    @Column(unique = true)
    private String patente;

    @Column
    private String ice;

    @Column(nullable = true, length = 10)
    private String code;

    // (name = "type_personne_morale")
    @ManyToOne
    @JoinColumn
    private TypePersonneMorale typePersonneMorale;

    @ManyToOne
    @JoinColumn
    private SecteurActivite secteurActivite;

    @Column
    private String rib;

}