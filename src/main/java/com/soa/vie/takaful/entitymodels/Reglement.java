package com.soa.vie.takaful.entitymodels;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soa.vie.takaful.util.ReglementStatut;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Reglement extends AbstractBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private String libelle;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ReglementStatut statut = ReglementStatut.EN_COURS;

	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dateStatut;

	@Column
	private String reglementType;

	@Column
	private Integer nomFichier;

	@ManyToMany
	@JoinTable(name = "reglement_prestations", joinColumns = @JoinColumn(name = "reglement_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "prestation_id", referencedColumnName = "prestationId"))
	private List<PrestationSinistre> prestations;

	@ManyToMany
	@JoinTable(name = "reglement_honoraires", joinColumns = @JoinColumn(name = "reglement_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "honoraire_id", referencedColumnName = "prestationId"))
	private List<PrestationHonoraire> honoraires;

	@Column
	private String num_lot;
}
