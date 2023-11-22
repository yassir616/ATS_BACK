package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.PreRemove;

import com.soa.vie.takaful.util.AccountStateEnum;
import com.soa.vie.takaful.util.VoisEnum;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@SQLDelete(sql = "UPDATE personne SET status = 'DELETED' WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "status <> 'DELETED'")
public class Personne extends AbstractBaseEntity {

    private static final long serialVersionUID = 1L;

    @Lob
    @Column
    private String logo;

    @Column
    private String adressPays;

    @Column
    private String adressVille;

    @Column
    @Enumerated(EnumType.STRING)
    private VoisEnum adressVois;

    @Column
    private String adressComplement;

    @Column
    private String adressCodePostal;

    @Column
    private String adressNumero;

    @Column
    private String adressComplete;

    @Column
    private String numeroDeTelephone;

    @Column()
    @Enumerated(EnumType.STRING)
    private AccountStateEnum status = AccountStateEnum.ACTIVE;

    @PreRemove
    public void deleteParticipant() {
        this.status = AccountStateEnum.DELETED;
    }
}