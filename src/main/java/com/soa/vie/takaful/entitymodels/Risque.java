package com.soa.vie.takaful.entitymodels;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.soa.vie.takaful.util.BrancheTypeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Risque extends AbstractBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(nullable = false, length = 50)
	private String code;

	@Column(nullable = false)
	private String libelle;

	@Column(nullable = false)
	private String bundle;

	@Column(nullable = false, length = 50)
	private String icon;

	@Column(length = 50)
	private String theme;

	@Column(length = 50)
	private String name;

	@ManyToMany
	@JoinTable(name = "sous_categorie_risque", joinColumns = @JoinColumn(name = "risque_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "sous_categorie_id", referencedColumnName = "id"))
	private List<SousCategorie> souscats;

	@Column
    @Enumerated(EnumType.STRING)
    private BrancheTypeEnum brancheType;

}
