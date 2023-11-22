package com.soa.vie.takaful.entitymodels;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ElementCollection;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.util.ContratStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "seq", initialValue = 15)
public class Contract extends AbstractBaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Produit produit;

    @ManyToOne
    private Personne souscripteur;

    @ManyToOne
    private PersonnePhysique assure;

    @ManyToOne
    private PointVente pointVente;

    @ManyToOne
    private Periodicite periodicite;

    @Column
    private String numeroDossierFinancement;

    @Column
    private boolean compteJoint = false;

    @Column
    private Long numeroDeSimulation;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateEffet;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateEcheance;

    @Column
    private Integer dureeContrat;

    @Column(unique = true)
    private String numeroContrat;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateResiliation;

    @Column
    private String contratType;

    @Column
    private int optionEmission;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateEtablissement;

    @Column
    @Enumerated(EnumType.STRING)
    private ContratStatus status;

    @Column
    private Date dateDeSignature;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateLimiteCoverture;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date datePremierecheance;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateDernierEcheance;

    // @OneToOne(mappedBy = "contrat")
    // private FileToUpload file;
    @Column
    private int numOrdrePrelevement = 0;

    @OneToOne(mappedBy = "contrat")
    @JsonIgnore
    private FileToUpload file;

    @Column(name="flag" ,columnDefinition = "int default '0'")
    private int flag; 

    @Transient
    @ElementCollection
    private List<String> honoraires;
}
