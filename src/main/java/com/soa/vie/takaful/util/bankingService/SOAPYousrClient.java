package com.soa.vie.takaful.util.bankingService;

import javax.xml.parsers.ParserConfigurationException;

import com.soa.vie.takaful.entitymodels.DecesProduit;
import com.soa.vie.takaful.entitymodels.Periodicite;
import com.soa.vie.takaful.entitymodels.Profession;
import com.soa.vie.takaful.entitymodels.SecteurActivite;
import com.soa.vie.takaful.repositories.IPeriodiciteRepository;
import com.soa.vie.takaful.repositories.IProfessionRepository;
import com.soa.vie.takaful.repositories.ISecteurActiviteRepository;
import com.soa.vie.takaful.services.IDecesProduitService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
public class SOAPYousrClient {

    @Autowired
    private IPeriodiciteRepository periodiciteRepository;

    @Autowired
    private IProfessionRepository professionRepository;

    @Autowired
    private ISecteurActiviteRepository secteurActiviteRepository;

    @Autowired
    private IDecesProduitService serviceProduit;

    public ResponseWS parserResponse(String response)
            throws SAXException, IOException, ParserConfigurationException, ParseException {

        String[] champs = StringUtils.substringsBetween(response, "<ns2:CHAMPS>", "</ns2:CHAMPS>");
        String[] valeurs = StringUtils.substringsBetween(response, "<ns2:VALEUR>", "</ns2:VALEUR>");

        ResponseWS responseWs = new ResponseWS();

        for (int i = 0; i < champs.length; i++) {
            if (champs[i] != "") {
                responseWs.status = 2;
            }
            switch (champs[i]) {
                case "ribPrelevement":
                    responseWs.setRib(valeurs[i]);
                    break;
                case "capitalAssure":
                    responseWs.setCapitalAssure(valeurs[i]);
                    break;
                case "Code client":
                    responseWs.setCodeClient(valeurs[i]);
                    break;

                case "dureeContrat":
                    responseWs.setDureeContrat(valeurs[i]);
                    break;
                case "periodicite":
                    Optional<Periodicite> perioOpt = this.periodiciteRepository.findByLibelle(valeurs[i]);
                    if (perioOpt.isPresent()) {
                        perioOpt.get().setAbb(valeurs[i]);
                        responseWs.setPreiodicite(perioOpt.get());
                    } else {
                        Periodicite periodicite = new Periodicite();
                        periodicite.setLibelle(valeurs[i]);
                        periodicite.setAbb(valeurs[i]);
                        responseWs.setPreiodicite(this.periodiciteRepository.save(periodicite));
                    }
                    break;
                case "dateEcheance":
                    Date dateecheance = new SimpleDateFormat("dd/MM/yyyy").parse(valeurs[i]);
                    responseWs.setDateEcheance(dateecheance);
                    break;
                case "dateEffet":
                    Date dateeffet = new SimpleDateFormat("dd/MM/yyyy").parse(valeurs[i]);
                    responseWs.setDateEffet(dateeffet);
                    break;
                case "differe":
                    responseWs.setDiffere(Float.parseFloat(valeurs[i]));
                    break;
                case "Encours":
                    if (!"".equals(valeurs[i]))
                        responseWs.setEncours(Float.parseFloat(valeurs[i]));
                case "typePersonne":
                    responseWs.setTypePersonne(valeurs[i]);
                    break;
                case "nationalite":
                    responseWs.setNationalite(valeurs[i]);
                    break;
                case "profession":
                    Optional<Profession> profOpt = this.professionRepository.findByLibelle(valeurs[i]);
                    if (profOpt.isPresent()) {
                        responseWs.setProfession(profOpt.get());
                    } else {
                        Profession profession = new Profession();
                        profession.setLibelle(valeurs[i]);
                        responseWs.setProfession(this.professionRepository.save(profession));
                    }
                    break;
                case "sexe":
                    responseWs.setSexe(valeurs[i]);
                    break;
                case "situationFamiliale":
                    responseWs.setSituationFamiliale(valeurs[i]);
                    break;
                case "cin":
                    responseWs.setCin(valeurs[i]);
                    break;
                case "dateNaissance":
                    Date dateNaissance = new SimpleDateFormat("dd/MM/yyyy").parse(valeurs[i]);
                    responseWs.setDateNaissance(dateNaissance);
                    break;

                case "secteurActivite":
                    Optional<SecteurActivite> secteurOpt = this.secteurActiviteRepository.findByLibelle(valeurs[i]);
                    if (secteurOpt.isPresent()) {
                        responseWs.setSecteurActivite(secteurOpt.get());
                    } else {
                        SecteurActivite secteur = new SecteurActivite();
                        secteur.setLibelle(valeurs[i]);
                        responseWs.setSecteurActivite(this.secteurActiviteRepository.save(secteur));
                    }
                case "abreviationPersonneMorale":
                    responseWs.setAbb(valeurs[i]);
                    break;
                case "PrenomPersonne":
                    responseWs.setPrenom(valeurs[i]);
                    break;
                case "nomPersonne":
                    responseWs.setNom(valeurs[i]);
                    break;
                case "raisonSociale":
                    responseWs.setRaisonSociale(valeurs[i]);
                    break;
                case "patente":
                    responseWs.setPatente(valeurs[i]);
                    break;
                case "adressVille":
                    responseWs.setAdressVille(valeurs[i]);
                    break;
                case "adressPays":
                    responseWs.setAdressPays(valeurs[i]);
                    break;
                case "refDossierFinancement":
                    responseWs.setRefDossierFinnancement(valeurs[i]);
                    break;
                case "codeProduit":
                    Optional<DecesProduit> prod = this.serviceProduit.getDecesProduitByCode(valeurs[i]);
                    if (prod.isPresent()) {
                        responseWs.setProduit(prod.get());
                    }
                    break;
                default:
                    System.out.println("null");
                    break;
            }
            System.out.println(champs[i] + " : " + valeurs[i]);
        }

        return responseWs;

    }

}
