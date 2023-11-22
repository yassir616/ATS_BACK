package com.soa.vie.takaful.util.bankingService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import com.soa.vie.takaful.entitymodels.DecesProduit;
import com.soa.vie.takaful.entitymodels.Periodicite;
import com.soa.vie.takaful.entitymodels.Profession;
import com.soa.vie.takaful.repositories.IPeriodiciteRepository;
import com.soa.vie.takaful.repositories.IProfessionRepository;
import com.soa.vie.takaful.repositories.ISecteurActiviteRepository;
import com.soa.vie.takaful.repositories.ITypePersonneMoraleRepository;
import com.soa.vie.takaful.services.IDecesProduitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceWS {

    @Autowired
    private IProfessionRepository professionRepository;

    @Autowired
    private IPeriodiciteRepository periodiciteRepository;

    @Autowired
    private ISecteurActiviteRepository secteurActiviteRepos;

    @Autowired
    private ITypePersonneMoraleRepository typePersonneMoraleRepos;

    @Autowired
    private IDecesProduitService serviceProduit;

    public ResponseWS getDataOfWebService(AkhdarData akhdarData) throws ParseException {

        ResponseWS destination = new ResponseWS();

        if (akhdarData.getProfession() != null) {
            Optional<Profession> professionByLibelleOption = this.professionRepository
                    .findByLibelle(akhdarData.getProfession());
            if (professionByLibelleOption.isPresent()) {
                destination.setProfession(professionByLibelleOption.get());
            } else {
                Profession profession = new Profession();
                profession.setLibelle(akhdarData.getProfession());
                destination.setProfession(this.professionRepository.save(profession));
            }
        }
        if (akhdarData.getPeriodicite() != null) {
            Optional<Periodicite> periodociteByAbbOption = this.periodiciteRepository
                    .findByAbb(akhdarData.getPeriodicite());
            if (periodociteByAbbOption.isPresent()) {
                destination.setPreiodicite(periodociteByAbbOption.get());
            } else {
                Periodicite periodicite = new Periodicite();
                periodicite.setAbb(akhdarData.getPeriodicite());
                periodicite.setLibelle(akhdarData.getPeriodicite());
                destination.setPreiodicite(this.periodiciteRepository.save(periodicite));
            }
        }
        if (akhdarData.getCodeProduit() != null) {
            Optional<DecesProduit> produitByCodeOption = this.serviceProduit
                    .getDecesProduitByCode(akhdarData.getCodeProduit());
            if (produitByCodeOption.isPresent()) {
                destination.setProduit(produitByCodeOption.get());
            }
        } else if (akhdarData.getCodeProduit() == null) {
            Optional<DecesProduit> produitByCode = this.serviceProduit
                    .getDecesProduitByCode("DF02022022");
            destination.setProduit(produitByCode.get());
        }
        if (akhdarData.getDealId() != null) {
            destination.setRefDossierFinnancement(String.valueOf(akhdarData.getDealId()));
        }
        if (akhdarData.getCapitalAssure() != 0 && akhdarData.getCapitalAssure() != null) {
            destination.setCapitalAssure(String.valueOf(akhdarData.getCapitalAssure()));
        }
        if (akhdarData.getDateNaisssance() != null) {
            destination.setDateNaissance(new SimpleDateFormat("dd-MM-yyyy").parse(akhdarData.getDateNaisssance()));
        }
        if (akhdarData.getDatePrelevement() != null) {
            destination.setDatePrelevement(new SimpleDateFormat("dd-MM-yyyy").parse(akhdarData.getDatePrelevement()));
        }
        if (akhdarData.getRib() != null) {
            destination.setRib(akhdarData.getRib());
        }
        if (akhdarData.getPrenomPersonne() != null) {
            destination.setPrenom(akhdarData.getPrenomPersonne());
        }
        if (akhdarData.getNomPersonne() != null) {
            destination.setNom(akhdarData.getNomPersonne());
        }
        if (akhdarData.getTypePersonne() != null) {
            destination.setTypePersonne(akhdarData.getTypePersonne());
        }
        if (akhdarData.getTypePersonne().equals("morale")) {
            destination.setAbb("abb");
            destination.setRaisonSociale("raison sociale");
            destination.setSecteurActivite(secteurActiviteRepos.findByLibelle("defaultSecteurActivite").get());
            destination.setTypePersonneMorale(typePersonneMoraleRepos.findByLibelle("defaultTypePersonneMorale").get());
        }
        if (akhdarData.getNationalite() != null) {
            destination.setNationalite(akhdarData.getNationalite());
        }
        if (akhdarData.getSituationFamiale() != null) {
            destination.setSituationFamiliale(akhdarData.getSituationFamiale());
        }

        if (akhdarData.getCin() != null) {
            destination.setCin(akhdarData.getCin());
        }
        if (akhdarData.getAdressePays() != null) {
            destination.setAdressPays(akhdarData.getAdressePays());
        }
        if (akhdarData.getAdresseVille() != null) {
            destination.setAdressVille(akhdarData.getAdresseVille());
        }
        if (akhdarData.getEncours() != 0 && akhdarData.getEncours() != null) {
            destination.setEncours(Float.parseFloat(akhdarData.getEncours().toString()));
        }
        if (akhdarData.getDureeContrat() != 0 && akhdarData.getDureeContrat() != null) {
            destination.setDureeContrat(String.valueOf(akhdarData.getDureeContrat()));
        }

        if (akhdarData.getSexe() != null) {
            destination.setSexe(akhdarData.getSexe());
        }
        if (akhdarData.getDateEcheance() != null) {
            destination.setDateEcheance(new SimpleDateFormat("dd-MM-yyyy").parse(akhdarData.getDateEcheance()));
        }
        if (akhdarData.getDateEffet() != null) {
            destination.setDateEffet(new SimpleDateFormat("dd-MM-yyyy").parse(akhdarData.getDateEffet()));
        }
        destination.setCodeClient("codeClient");

        if (akhdarData.getDiffere() > 0) {
            destination.setDiffere(Float.valueOf(akhdarData.getDiffere()));
        }

        return destination;
    }
}
