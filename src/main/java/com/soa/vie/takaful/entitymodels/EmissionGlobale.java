package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmissionGlobale extends AbstractBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	private String numeroLot;

    @Column
    private  Float montantTTC;

    @Column
    private  Float montantCotisation;

    @Column
    private  Float montantTaxe;

    @Column
    private  Float montantTaxeParaFiscale;
    
    @Column
    private  Float solde;
    
    @Column
    private  Float fraisAcquisitionTTC;
    
    @Column
    private  Float fraisGestionTTC;
    
    @Column
    private String reference;

    @Column
    private boolean etat=false;

}
