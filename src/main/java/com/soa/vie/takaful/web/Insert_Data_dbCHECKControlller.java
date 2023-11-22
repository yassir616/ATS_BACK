package com.soa.vie.takaful.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Random;
import com.soa.vie.takaful.requestmodels.LettreReglementRequest;
import java.time.LocalDate;

@RestController
@RequestMapping("api/private")
public class Insert_Data_dbCHECKControlller {

    private final JdbcTemplate secondJdbcTemplate;

    @Autowired
    public Insert_Data_dbCHECKControlller(JdbcTemplate secondJdbcTemplate) {
        this.secondJdbcTemplate = secondJdbcTemplate;
    }

    @PostMapping("virement2")
    public void insertData_virementIntoSecondDatabase(@RequestBody List<LettreReglementRequest> requestModel) {
        LocalDate currentDate = LocalDate.now();
        Random random = new Random();
        for (int i = 0; i < requestModel.size(); i++) {
            int objet_reglement = random.nextInt(9000) + 1000;
            String identifiant_reglement = "TTT00" + objet_reglement;
            String nom_bénificaire = requestModel.get(i).getCode_part() + "/DECES (Convention MCMA/"
                    + requestModel.get(i).getCode_part() + ")";
            String zone_libre1 = "20A0" + objet_reglement;
            String sql = "INSERT INTO i_virement2  (code_societe,code_agence,code_nature,num_lot,num_cheque, " +
                    "nom_beneficiaire,complement_beneficiaire,adresse_beneficiaire2,adresse_beneficiaire3, " +
                    "adresse_beneficiaire4,objet_reglement,devise,date_echeance,mode_reglement,adresse_banque1, " +
                    "adresse_banque2,adresse_banque4,num_serie,adresse_beneficiaire1,code_fournisseur,zone_libre1, " +
                    "zone_libre2,montant_facture,identification_reglement,RIB_beneficiare,adresse_banque3,Etat, " +
                    "Date_Creation,Signature1,Signature2,Pre_Signature) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            Object[] params = { "003", "M5", "£8", requestModel.get(i).getNum_lot(), "", nom_bénificaire, "",
                    "", "", "", "", "", "", "VIR", "", "", "", "", "", "", zone_libre1, "",
                    requestModel.get(i).getMontantNet(), identifiant_reglement, "", "", "", currentDate, "08", "08",
                    "N" };
            secondJdbcTemplate.update(sql, params);
        }

    }

    @PostMapping("cheque2")
    public void insertData_chequeIntoSecondDatabase(@RequestBody List<LettreReglementRequest> requestModel) {
        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < requestModel.size(); i++) {
            String zone_libre1 = requestModel.get(i).getNum_sinistre() + "|0000000000|0000000001|0000000000|"
                    + requestModel.get(i).getDate().toString();
            String sql = "INSERT INTO i_cheque2 (code_societe,code_agence,code_nature,num_lot,num_cheque, " +
                    "nom_beneficiaire,complement_beneficiaire,adresse_beneficiaire2,adresse_beneficiaire3, " +
                    "adresse_beneficiaire4,objet_reglement,devise,date_echeance,mode_reglement,adresse_banque1, " +
                    "adresse_banque2,adresse_banque4,num_serie,adresse_beneficiaire1,code_fournisseur,zone_libre1, " +
                    "zone_libre2,montant_facture,identification_reglement,RIB_beneficiare,adresse_banque3,Etat, " +
                    "Date_Creation,Num_compte,Singature1,Signature2,Pre_Signature,Code_bureau) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
            Object[] params = { "003", "M5", "54", requestModel.get(i).getNum_lot(), "",
                    requestModel.get(i).getBenificiaire(), "",
                    "", "", "", "Honoraire", "", "", "CHQ", "", "", "", "", "", "", zone_libre1, "",
                    requestModel.get(i).getMontantNet(), requestModel.get(i).getNum_sinistre(), "", "", "", currentDate,
                    "M5", "08", "08", "N", "000" };
            secondJdbcTemplate.update(sql, params);

        }

    }

}