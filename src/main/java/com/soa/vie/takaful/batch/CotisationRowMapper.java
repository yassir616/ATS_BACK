package com.soa.vie.takaful.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.soa.vie.takaful.entitymodels.Contract;
import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.util.EtatCotisation;

import org.springframework.jdbc.core.RowMapper;

public class CotisationRowMapper implements RowMapper<Cotisation> {
    @Override
    public Cotisation mapRow(ResultSet rs, int rowNum) throws SQLException {
        LocalDate currentDate = LocalDate.now();
        

        Cotisation cotisation = new Cotisation();
        Contract contrat = new Contract();
        cotisation.setSolde(rs.getInt("solde"));
        cotisation.setContributionPure(rs.getFloat("contribution_pure"));
        cotisation.setDateEtablissement(rs.getDate("date_etablissement"));
        cotisation.setMontantAccessoire(rs.getFloat("montant_accessoire"));
        cotisation.setNumQuittance(rs.getInt("num_quittance"));
        cotisation.setCotisationType(rs.getString("cotisation_type"));
        cotisation.setExercice(rs.getInt("exercice"));
        cotisation.setFraisAcquisitionTTC(rs.getFloat("frais_acquisitionttc"));
        cotisation.setFraisGestionTTC(rs.getFloat("frais_gestionttc"));
        cotisation.setMontantCotisation(rs.getFloat("montant_cotisation"));
        cotisation.setMontantTTC(rs.getFloat("montantttc"));
        cotisation.setMontantTaxe(rs.getFloat("montant_taxe"));
        cotisation.setDatePrelevement(java.sql.Date.valueOf(currentDate));
        contrat.setId(rs.getString("contrat_id"));
        cotisation.setFlagBatch(rs.getInt("flag_batch"));
        EtatCotisation etatCotisation = EtatCotisation.valueOf(rs.getString("etat_cotisation"));
        cotisation.setDateEmission(java.sql.Date.valueOf(currentDate));
        cotisation.setEtatCotisation(etatCotisation);
        cotisation.setContrat(contrat);
        return cotisation;

    }
}