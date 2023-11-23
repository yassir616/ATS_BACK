package com.soa.vie.takaful.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.soa.vie.takaful.entitymodels.Auxiliaire;
import com.soa.vie.takaful.entitymodels.Encaissement;
import com.soa.vie.takaful.repositories.IAuxiliaireRepository;
import com.soa.vie.takaful.repositories.IBordereauRepository;
import com.soa.vie.takaful.repositories.IContractRepository;
import com.soa.vie.takaful.repositories.ICotisationRepository;
import com.soa.vie.takaful.requestmodels.Acceptation;
import com.soa.vie.takaful.requestmodels.BordereauEncaissementRequestModel;
import com.soa.vie.takaful.requestmodels.BordereauxEncaissementTableRequestModel;
import com.soa.vie.takaful.requestmodels.ConditionParticuliere;
import com.soa.vie.takaful.requestmodels.ConditionParticuliereMrb;
import com.soa.vie.takaful.requestmodels.LettreAcceptationAvecSurprime;
import com.soa.vie.takaful.requestmodels.LettreOrientation;
import com.soa.vie.takaful.requestmodels.LettreReglementRequest;
import com.soa.vie.takaful.requestmodels.RequestAcceptationAvecExclusion;
import com.soa.vie.takaful.requestmodels.RequestDevisMrb;
import com.soa.vie.takaful.requestmodels.RequestExamenComplementaireExaminateur;
import com.soa.vie.takaful.requestmodels.RequestExamenComplementaireMedecinSpecialiste;
import com.soa.vie.takaful.requestmodels.RequestLettreRejet;
import com.soa.vie.takaful.requestmodels.RequestLettreRenonciation;
import com.soa.vie.takaful.responsemodels.Devis;
import com.soa.vie.takaful.services.Impl.FileStorageService;
import com.soa.vie.takaful.util.Constants;

import lombok.extern.slf4j.Slf4j;

import com.soa.vie.takaful.requestmodels.LettreRelance;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;



@Slf4j
@RestController
@PropertySource("classpath:global.properties")
@RequestMapping("api/private")
public class jasperFile {
        @Autowired
        private IAuxiliaireRepository auxiliaireRepository;

        @Autowired
        private FileStorageService fileStorageServiceImpl;

        @Autowired
        private IBordereauRepository bordereauRepository;

        @Autowired
        private ICotisationRepository cotisationRepository;

        @Autowired
        private IContractRepository contratRepo;

        @Value("${production}")
        private String production;

        @RequestMapping(value = "jasper/pdf", method = RequestMethod.POST, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> downloadDevis(@RequestBody Devis requestModel)
                        throws IOException, SQLException, JRException {
                // Initialisation fichier PDF

                File file;
                ClassPathResource pathResource = new ClassPathResource("rapport/Blank.pdf");
                file = pathResource.getFile();

                List<Devis> employees = new ArrayList<>();

                employees.add(requestModel);

                // Fetching the .jrxml file from the resources folder.
                final InputStream stream = this.getClass().getResourceAsStream("/rapport/report.jrxml");

                final JasperDesign jasperDesign = JRXmlLoader.load(stream);
                // Compile the Jasper report from .jrxml to .japser
                final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                // Fetching the employees from the data source.
                final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(employees);

                // Adding the additional parameters to the pdf.
                final Map<String, Object> parameters = new HashMap<>();
                String path2 = getPATH();
                parameters.put("PATH", path2);
                // Filling the report with the employee data and additional parameters
                // information.
                final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

                File pdfFile = File.createTempFile(requestModel.getProduit(), ".pdf");

                JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdfFile));

                file = pdfFile;

                // Retourner fichier PDF
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                .contentType(MediaType.APPLICATION_PDF).body(resource);

        }

        // @RequestMapping(value = "bordereauEncaissement/pdf", method = RequestMethod.POST, produces = "application/pdf")
        // public ResponseEntity<InputStreamResource> getBordereauEncaissement(@RequestBody BordereauEncaissementRequestModel requestModel)
        //                 throws IOException, SQLException, JRException, ParseException {
        //         int numQuittance;
        //         String numeroDePolice;
        //         List<BordereauxEncaissementTableRequestModel> tableEncaissements=new ArrayList<BordereauxEncaissementTableRequestModel>();
        //         // Date dateCreation=requestModel.getCreationDate();
        //         // Date formattedDate = new SimpleDateFormat("MM/dd/yyyy").parse(new SimpleDateFormat("MM/dd/yyyy").format(dateCreation));
        //         // requestModel.setCreationDate(formattedDate);
        //         List<Encaissement> encaissements=bordereauRepository.findById(requestModel.getId()).get().getListEncaissement();
        //         for(Encaissement encaissement:encaissements){
        //                 numQuittance=cotisationRepository.findById(encaissement.getCotisation().getId()).get().getNumQuittance();
        //                 numeroDePolice= contratRepo.findById(cotisationRepository.findById(encaissement.getCotisation().getId()).get().getContrat().getId()).get().getNumeroContrat();
        //                 log.info("police : "+numeroDePolice);
        //                 log.info("num Quittance : "+numQuittance);
        //                 log.info("Id : "+encaissement.getId());
        //                 log.info("numero encaissement: "+encaissement.getNumEncaissement());
        //                 log.info("montantEncaissement: "+encaissement.getMontantEncaissement());
        //                 log.info("mode encaissement: "+encaissement.getModeEncaissement());
        //                 log.info("date encaissement: "+encaissement.getDateEncaissement());
        //                 BordereauxEncaissementTableRequestModel encTable=new BordereauxEncaissementTableRequestModel();
        //                 encTable.setNumeroEncaissement(encaissement.getNumEncaissement());
        //                 encTable.setRefEncaissement(encaissement.getNumReference());
        //                 encTable.setMontantEncaissement(String.valueOf(encaissement.getMontantEncaissement()));
        //                 encTable.setModeEncaissement(encaissement.getModeEncaissement());
        //                 //encTable.setDateEncaissement(encaissement.getDateEncaissement());
        //                 encTable.setNumeroQuittance(String.valueOf(numQuittance));
        //                 encTable.setNumeroContrat(numeroDePolice);
        //                 tableEncaissements.add(encTable);
        //         }
        //         requestModel.setTableEncaissements(tableEncaissements);
        //         for(BordereauxEncaissementTableRequestModel item : tableEncaissements){
        //                 log.info("test  :");
        //                 log.info("list : "+item.getRefEncaissement());
        //         }
        //         //Initialisation fichier PDF
        //         File file;
        //         ClassPathResource pathResource = new ClassPathResource("rapport/Blank.pdf");
        //         file = pathResource.getFile();

        //         // List<BordereauxEncaissementTableRequestModel> employees = new ArrayList<>();

        //         // employees.add(table);

        //         // Fetching the .jrxml file from the resources folder.
        //         final InputStream stream = this.getClass().getResourceAsStream("/rapport/BordoreauEncaissement.jrxml");

        //         final JasperDesign jasperDesign = JRXmlLoader.load(stream);
        //         // Compile the Jasper report from .jrxml to .japser
        //         final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

        //         // Fetching the employees from the data source.
        //         final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(requestModel.getTableEncaissements());

        //         // Adding the additional parameters to the pdf.
        //         final Map<String, Object> parameters = new HashMap<>();
        //         String path2 = getPATH();
        //         parameters.put("PATH", path2);
        //         parameters.put("CollectionBeanParam", source);
        //         log.info(""+parameters);
        //         // Filling the report with the employee data and additional parameters
        //         // information.
        //         final JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

        //         File pdfFile = File.createTempFile(requestModel.getRefBordereau(), ".pdf");

        //         JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdfFile));

        //         file = pdfFile;

        //         // Retourner fichier PDF
        //         InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        //         HttpHeaders headers = new HttpHeaders();
        //         headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        //         headers.add("Pragma", "no-cache");
        //         headers.add("Expires", "0");
        //         return ResponseEntity.ok().headers(headers).contentLength(file.length())
        //                         .contentType(MediaType.APPLICATION_PDF).body(resource);

        // }



        @RequestMapping(value = "lettreRequest/pdf", method = RequestMethod.POST, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> ReglementHonoraire(@RequestParam("path") String path,
                        @RequestParam("modeReglement") String modeReglement,
                        @RequestBody List<LettreReglementRequest> requestModel)
                        throws IOException, SQLException, JRException {

                File file;
                ClassPathResource pathResource = new ClassPathResource("rapport/Blank.pdf");
                file = pathResource.getFile();

                // Fetching the .jrxml file from the resources folder.
                final InputStream stream;
                if (modeReglement.equals("cheque")) {
                        stream = this.getClass().getResourceAsStream("/rapport/Reglement_honoraire_cheque.jrxml");
                } else {
                        stream = this.getClass().getResourceAsStream("/rapport/" + path + ".jrxml");
                }
                System.out.println("path =  " + path);

                final JasperDesign jasperDesign = JRXmlLoader.load(stream);

                // Compile the Jasper report from .jrxml to .japser
                final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                // Fetching the employees from the data source.
                final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(requestModel);

                // Adding the additional parameters to the pdf.
                final Map<String, Object> parameters = new HashMap<>();

                String path2 = getPATH();
                int index = 0;
                for (int i = 0; i < requestModel.size(); i++) {
                        index = i;
                }
                if (path.equals("Reglement_honoraire_Virement")) {
                        requestModel.get(index).setPartenaire(requestModel.get(index).getRib());
                }
                parameters.put("PATH", path2);
                parameters.put("CollectionBeanParam", source);
                parameters.put("Total", requestModel.get(index).getTotal());
                parameters.put("Partenaire", requestModel.get(index).getPartenaire());
                parameters.put("Agence", requestModel.get(index).getAgence());
                parameters.put("Adresse", requestModel.get(index).getAdresse());
                parameters.put("Benificiaire", requestModel.get(index).getBenificiaire());
                parameters.put("ModeReglement", modeReglement);
                parameters.put("adresseAuxiliaire", requestModel.get(index).getAdresseAuxiliaire());
                parameters.put("date", requestModel.get(index).getDate());
                parameters.put("referencePartenaire", requestModel.get(index).getReferencePartenaire());

                // Filling the report with the employee data and additional parameters
                // information.

                final JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

                File pdfFile = File.createTempFile("Honoraires", ".pdf");

                JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdfFile));
                file = pdfFile;
                // Retourner fichier PDF
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");

                return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                .contentType(MediaType.APPLICATION_PDF).body(resource);

        }

        @RequestMapping(value = "lettreOrientation/pdf", method = RequestMethod.POST, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> downloadLettreOrientation(
                        @RequestBody LettreOrientation requestModel) throws IOException, SQLException, JRException {

                Optional<Auxiliaire> auxiliaire = auxiliaireRepository.findById(requestModel.getExaminateurId());
                if (auxiliaire.isPresent()) {
                        requestModel.setNomExaminateur(auxiliaire.get().getNom());
                        requestModel.setAdresseExaminateur(auxiliaire.get().getAdressComplete());
                        requestModel.setTelephone(auxiliaire.get().getTel());
                        requestModel.setEmail(auxiliaire.get().getEmail());
                        // Initialisation fichier PDF
                        File file;
                        ClassPathResource pathResource = new ClassPathResource("rapport/Blank.pdf");
                        file = pathResource.getFile();

                        List<LettreOrientation> lettre = new ArrayList<>();
                        lettre.add(requestModel);

                        // Fetching the .jrxml file from the resources folder.
                        final InputStream stream = this.getClass()
                                        .getResourceAsStream("/rapport/LetterOrientation.jrxml");

                        final JasperDesign jasperDesign = JRXmlLoader.load(stream);
                        // Compile the Jasper report from .jrxml to .japser
                        final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                        // Fetching the employees from the data source.
                        final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(lettre);

                        // Adding the additional parameters to the pdf.
                        final Map<String, Object> parameters = new HashMap<>();
                        String path2 = getPATH();
                        parameters.put("PATH", path2);

                        // Filling the report with the employee data and additional parameters
                        // information.
                        final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

                        File pdfFile = File.createTempFile(requestModel.getNomPrenom(), ".pdf");

                        JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdfFile));

                        file = pdfFile;

                        // Retourner fichier PDF
                        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                        HttpHeaders headers = new HttpHeaders();
                        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                        headers.add("Pragma", "no-cache");
                        headers.add("Expires", "0");
                        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                        .contentType(MediaType.APPLICATION_PDF).body(resource);
                } else {
                        throw new NoSuchElementException("ListAcceptationReassurance : acceptation n'existe pas ");
                }

        }

        @RequestMapping(value = "lettreAcceptation/pdf", method = RequestMethod.POST, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> acceptation(@RequestBody Acceptation requestModel)
                        throws IOException, SQLException, JRException {
                // Initialisation fichier PDF

                File file;
                ClassPathResource pathResource = new ClassPathResource("rapport/Blank.pdf");
                file = pathResource.getFile();
                List<Acceptation> employees = new ArrayList<>();

                employees.add(requestModel);

                // Fetching the .jrxml file from the resources folder.
                final InputStream stream = this.getClass().getResourceAsStream("/rapport/Acceptation.jrxml");

                final JasperDesign jasperDesign = JRXmlLoader.load(stream);
                // Compile the Jasper report from .jrxml to .japser
                final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                // Fetching the employees from the data source.
                final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(employees);

                // Adding the additional parameters to the pdf.
                final Map<String, Object> parameters = new HashMap<>();

                // Filling the report with the employee data and additional parameters
                // information.
                final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

                File pdfFile = File.createTempFile(requestModel.getNom(), ".pdf");

                JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdfFile));

                file = pdfFile;

                // Retourner fichier PDF
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                .contentType(MediaType.APPLICATION_PDF).body(resource);

        }

        @RequestMapping(value = "lettreRelance/pdf", method = RequestMethod.POST, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> downloadLettreRelance(
                        @RequestBody LettreRelance requestModel) throws IOException, SQLException, JRException {

                // Initialisation fichier PDF

                File file;
                ClassPathResource pathResource = new ClassPathResource("rapport/Blank.pdf");
                file = pathResource.getFile();
                List<LettreRelance> lettre = new ArrayList<>();

                lettre.add(requestModel);

                // Fetching the .jrxml file from the resources folder.
                final InputStream stream = this.getClass().getResourceAsStream("/rapport/Lettre_Relance.jrxml");

                final JasperDesign jasperDesign = JRXmlLoader.load(stream);
                // Compile the Jasper report from .jrxml to .japser
                final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                // Fetching the employees from the data source.
                final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(lettre);

                // Adding the additional parameters to the pdf.
                final Map<String, Object> parameters = new HashMap<>();
                String path3 = getPATH();
                parameters.put("PATH", path3);

                // Filling the report with the employee data and additional parameters
                // information.
                final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

                File pdfFile = File.createTempFile(requestModel.getNomPrenom(), ".pdf");

                JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdfFile));

                file = pdfFile;

                // Retourner fichier PDF
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                .contentType(MediaType.APPLICATION_PDF).body(resource);

        }

        @RequestMapping(value = "conditionParticuliere/pdf", method = RequestMethod.POST, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> conditionParticuliere(
                        @RequestBody ConditionParticuliere requestModel) throws IOException, SQLException, JRException {
                // Initialisation fichier PDF

                File file;
                ClassPathResource pathResource = new ClassPathResource("rapport/Blank.pdf");
                file = pathResource.getFile();
                byte[] data = this.fileStorageServiceImpl.getData(requestModel.getId());
                if (data != null) {
                        FileUtils.writeByteArrayToFile(file, data);
                } else {

                        List<ConditionParticuliere> employees = new ArrayList<>();

                        employees.add(requestModel);
                        InputStream stream = null;
                        // Fetching the .jrxml file from the resources folder.
                        if (requestModel.getCodePartenaire().equals("BAY")) {
                                stream = this.getClass().getResourceAsStream("/rapport/CPBAY.jrxml");

                        } else if (requestModel.getCodePartenaire().equals("AAB")) {
                                stream = this.getClass().getResourceAsStream("/rapport/CPAAB.jrxml");
                        }

                        if (stream != null) {

                                final JasperDesign jasperDesign = JRXmlLoader.load(stream);
                                // Compile the Jasper report from .jrxml to .japser
                                final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                                // Fetching the employees from the data source.
                                final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(employees);

                                // Adding the additional parameters to the pdf.
                                final Map<String, Object> parameters = new HashMap<>();

                                // System.out.println("mode = " + production);
                                String path2 = getPATH();
                                parameters.put("PATH", path2);

                                // Filling the report with the employee data and additional parameters
                                // information.
                                final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

                                File pdfFile = File.createTempFile(requestModel.getNomPrenomAssure(), ".pdf");

                                JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdfFile));

                                file = pdfFile;
                        }
                }

                // Retourner fichier PDF
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                .contentType(MediaType.APPLICATION_PDF).body(resource);

        }

        private String getPATH() {
                String path2 = System.getProperty("user.dir") + "/src/main/resources/rapport";
                if (production.equals("true"))
                        path2 = System.getProperty("user.dir") + "/webapps/takaful/WEB-INF/classes/rapport";
                return path2;
        }

        @RequestMapping(value = "conditionGeneral/pdf/{partenaire}", method = RequestMethod.GET, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> downloadCG(@PathVariable String partenaire)
                        throws IOException, SQLException, JRException {
                // Initialisation fichier PDF
                /// work here
                File file = null;
                if (partenaire.equals("BAY")) {
                        ClassPathResource pathResource = new ClassPathResource("rapport/CGBAY.pdf");
                        file = pathResource.getFile();
                } else {
                        ClassPathResource pathResource = new ClassPathResource("rapport/CGAAB.pdf");
                        file = pathResource.getFile();
                }

                // Retourner fichier PDF
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                .contentType(MediaType.APPLICATION_PDF).body(resource);

        }

        @RequestMapping(value = "cpMrb/pdf", method = RequestMethod.POST, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> conditionParticuliereMrb(
                        @RequestBody ConditionParticuliereMrb requestModel)
                        throws IOException, SQLException, JRException {
                // Initialisation fichier PDF

                File file;
                ClassPathResource pathResource = new ClassPathResource("rapport/Blank.pdf");
                file = pathResource.getFile();

                List<ConditionParticuliereMrb> employees = new ArrayList<>();

                employees.add(requestModel);

                // Fetching the .jrxml file from the resources folder.
                final InputStream stream = this.getClass().getResourceAsStream("/rapport/CPMRB.jrxml");

                final JasperDesign jasperDesign = JRXmlLoader.load(stream);
                // Compile the Jasper report from .jrxml to .japser
                final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                // Fetching the employees from the data source.
                final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(employees);

                // Adding the additional parameters to the pdf.
                final Map<String, Object> parameters = new HashMap<>();

                // System.out.println("mode = " + production);
                String path2 = getPATH();
                parameters.put("PATH", path2);

                // Filling the report with the employee data and additional parameters
                // information.
                final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

                File pdfFile = File.createTempFile(requestModel.getNumeroSouscription(), ".pdf");

                JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdfFile));

                file = pdfFile;

                // Retourner fichier PDF
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                .contentType(MediaType.APPLICATION_PDF).body(resource);

        }

        @RequestMapping(value = "devisMrb/pdf", method = RequestMethod.POST, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> downloadDevisMrb(@RequestBody RequestDevisMrb requestModel)
                        throws IOException, SQLException, JRException {
                // Initialisation fichier PDF
                File file;
                ClassPathResource pathResource = new ClassPathResource("rapport/Blank.pdf");
                file = pathResource.getFile();

                List<RequestDevisMrb> employees = new ArrayList<>();

                employees.add(requestModel);

                // Fetching the .jrxml file from the resources folder.
                final InputStream stream = this.getClass().getResourceAsStream("/rapport/DevisMrb.jrxml");

                final JasperDesign jasperDesign = JRXmlLoader.load(stream);
                // Compile the Jasper report from .jrxml to .japser
                final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                // Fetching the employees from the data source.
                final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(employees);

                // Adding the additional parameters to the pdf.
                final Map<String, Object> parameters = new HashMap<>();
                String path2 = getPATH();
                parameters.put("PATH", path2);
                // Filling the report with the employee data and additional parameters
                // information.
                final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

                File pdfFile = File.createTempFile(requestModel.getNomPrenom(), ".pdf");

                JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdfFile));

                file = pdfFile;

                // Retourner fichier PDF
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                .contentType(MediaType.APPLICATION_PDF).body(resource);

        }

        @RequestMapping(value = "lettreRejet/pdf", method = RequestMethod.POST, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> lettreRejet(@RequestBody RequestLettreRejet requestModel)
                        throws IOException, SQLException, JRException {
                // Initialisation fichier PDF

                File file;
                ClassPathResource pathResource = new ClassPathResource("rapport/Blank.pdf");
                file = pathResource.getFile();

                List<RequestLettreRejet> employees = new ArrayList<>();

                employees.add(requestModel);

                // Fetching the .jrxml file from the resources folder.
                final InputStream stream = this.getClass().getResourceAsStream("/rapport/lettreRejet.jrxml");

                final JasperDesign jasperDesign = JRXmlLoader.load(stream);
                // Compile the Jasper report from .jrxml to .japser
                final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                // Fetching the employees from the data source.
                final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(employees);

                // Adding the additional parameters to the pdf.
                final Map<String, Object> parameters = new HashMap<>();
                String path2 = getPATH();
                parameters.put("PATH", path2);
                // Filling the report with the employee data and additional parameters
                // information.
                final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

                File pdfFile = File.createTempFile(requestModel.getNomParticipant(), ".pdf");

                JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdfFile));

                file = pdfFile;

                // Retourner fichier PDF
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                .contentType(MediaType.APPLICATION_PDF).body(resource);

        }

        @RequestMapping(value = "lettreRenonciation/pdf", method = RequestMethod.POST, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> lettreRenonciation(
                        @RequestBody RequestLettreRenonciation requestModel)
                        throws IOException, SQLException, JRException {
                // Initialisation fichier PDF

                File file;
                ClassPathResource pathResource = new ClassPathResource("rapport/Blank.pdf");
                file = pathResource.getFile();

                List<RequestLettreRenonciation> employees = new ArrayList<>();

                employees.add(requestModel);

                // Fetching the .jrxml file from the resources folder.
                final InputStream stream = this.getClass().getResourceAsStream("/rapport/lettreRenonciation.jrxml");

                final JasperDesign jasperDesign = JRXmlLoader.load(stream);
                // Compile the Jasper report from .jrxml to .japser
                final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                // Fetching the employees from the data source.
                final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(employees);

                // Adding the additional parameters to the pdf.
                final Map<String, Object> parameters = new HashMap<>();
                String path2 = getPATH();
                parameters.put("PATH", path2);
                // Filling the report with the employee data and additional parameters
                // information.
                final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

                File pdfFile = File.createTempFile(requestModel.getNomParticipant(), ".pdf");

                JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdfFile));

                file = pdfFile;

                // Retourner fichier PDF
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                .contentType(MediaType.APPLICATION_PDF).body(resource);

        }

        @RequestMapping(value = "lettreAcceptationExclusion/pdf", method = RequestMethod.POST, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> lettreRejet(
                        @RequestBody RequestAcceptationAvecExclusion requestModel)
                        throws IOException, SQLException, JRException {
                // Initialisation fichier PDF

                File file;
                ClassPathResource pathResource = new ClassPathResource("rapport/Blank.pdf");
                file = pathResource.getFile();

                List<RequestAcceptationAvecExclusion> employees = new ArrayList<>();

                employees.add(requestModel);

                // Fetching the .jrxml file from the resources folder.
                final InputStream stream = this.getClass()
                                .getResourceAsStream("/rapport/AcceptationAvecExclusion.jrxml");

                final JasperDesign jasperDesign = JRXmlLoader.load(stream);
                // Compile the Jasper report from .jrxml to .japser
                final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                // Fetching the employees from the data source.
                final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(employees);

                // Adding the additional parameters to the pdf.
                final Map<String, Object> parameters = new HashMap<>();
                String path2 = getPATH();
                parameters.put("PATH", path2);
                // Filling the report with the employee data and additional parameters
                // information.
                final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

                File pdfFile = File.createTempFile(requestModel.getNomParticipant(), ".pdf");

                JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdfFile));

                file = pdfFile;

                // Retourner fichier PDF
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                .contentType(MediaType.APPLICATION_PDF).body(resource);

        }

        @RequestMapping(value = "LettreDemandeExamenComplimentaireExaminateur/pdf", method = RequestMethod.POST, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> downloadLettreDemandeExamenComplimentaireExaminateur(
                        @RequestBody RequestExamenComplementaireExaminateur requestModel)
                        throws IOException, SQLException, JRException {

                Optional<Auxiliaire> auxiliaire = auxiliaireRepository.findById(requestModel.getExaminateurId());

                if (auxiliaire.isPresent()) {
                        requestModel.setExaminateur(auxiliaire.get().getNom());
                        requestModel.setAdresse(auxiliaire.get().getAdressComplete());
                        requestModel.setTelephone(auxiliaire.get().getTel());
                        // Initialisation fichier PDF
                        File file;
                        ClassPathResource pathResource = new ClassPathResource("rapport/Blank.pdf");
                        file = pathResource.getFile();

                        List<RequestExamenComplementaireExaminateur> lettre = new ArrayList<>();

                        lettre.add(requestModel);

                        // Fetching the .jrxml file from the resources folder.
                        final InputStream stream = this.getClass()
                                        .getResourceAsStream("/rapport/DemandeExamenComplementaireExaminateur.jrxml");

                        final JasperDesign jasperDesign = JRXmlLoader.load(stream);
                        // Compile the Jasper report from .jrxml to .japser
                        final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                        // Fetching the employees from the data source.
                        final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(lettre);

                        // Adding the additional parameters to the pdf.
                        final Map<String, Object> parameters = new HashMap<>();
                        String path2 = getPATH();
                        parameters.put("PATH", path2);

                        // Filling the report with the employee data and additional parameters
                        // information.
                        final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

                        File pdfFile = File.createTempFile(requestModel.getNomParticipant(), ".pdf");

                        JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdfFile));

                        file = pdfFile;

                        // Retourner fichier PDF
                        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                        HttpHeaders headers = new HttpHeaders();
                        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                        headers.add("Pragma", "no-cache");
                        headers.add("Expires", "0");
                        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                        .contentType(MediaType.APPLICATION_PDF).body(resource);
                } else {
                        throw new NoSuchElementException("ListAcceptationReassurance : acceptation n'existe pas ");
                }

        }

        @RequestMapping(value = "LettreDemandeExamenComplimentaireMedecinSpecialiste/pdf", method = RequestMethod.POST, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> downloadLettreDemandeExamenComplimentaireMedecinSpecialiste(
                        @RequestBody RequestExamenComplementaireMedecinSpecialiste requestModel)
                        throws IOException, SQLException, JRException {

                Auxiliaire auxiliaire = auxiliaireRepository.findRandomAuxilaire(requestModel.getPointVenteVille(),
                                Constants.MEDECIN_SPECIALISTE);
                requestModel.setSpecialiste(auxiliaire.getNom());
                requestModel.setAdresse(auxiliaire.getAdressComplete());
                requestModel.setTelephone(auxiliaire.getTel());
                // Initialisation fichier PDF
                File file;
                ClassPathResource pathResource = new ClassPathResource("rapport/Blank.pdf");
                file = pathResource.getFile();

                List<RequestExamenComplementaireMedecinSpecialiste> lettre = new ArrayList<>();

                lettre.add(requestModel);

                // Fetching the .jrxml file from the resources folder.
                final InputStream stream = this.getClass()
                                .getResourceAsStream("/rapport/ExamenComplementaireMedecinSpecialiste.jrxml");

                final JasperDesign jasperDesign = JRXmlLoader.load(stream);
                // Compile the Jasper report from .jrxml to .japser
                final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                // Fetching the employees from the data source.
                final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(lettre);

                // Adding the additional parameters to the pdf.
                final Map<String, Object> parameters = new HashMap<>();
                String path2 = getPATH();
                parameters.put("PATH", path2);

                // Filling the report with the employee data and additional parameters
                // information.
                final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

                File pdfFile = File.createTempFile(requestModel.getNomParticipant(), ".pdf");

                JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdfFile));

                file = pdfFile;

                // Retourner fichier PDF
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                .contentType(MediaType.APPLICATION_PDF).body(resource);

        }

        @RequestMapping(value = "reglementGestion/pdf/{partenaire}", method = RequestMethod.GET, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> downloadRG()
                        throws IOException, SQLException, JRException {
                // Initialisation fichier PDF

                File file;
                ClassPathResource pathResource = new ClassPathResource("rapport/TTTBAY.pdf");
                file = pathResource.getFile();

                // Retourner fichier PDF
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                .contentType(MediaType.APPLICATION_PDF).body(resource);

        }

        @RequestMapping(value = "LettreAcceptationAvecSurprime/pdf", method = RequestMethod.POST, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> downloadLettreAcceptationAvecSurprime(
                        @RequestBody LettreAcceptationAvecSurprime requestModel)
                        throws IOException, SQLException, JRException {

                File file;
                ClassPathResource pathResource = new ClassPathResource("rapport/Blank.pdf");
                file = pathResource.getFile();

                List<LettreAcceptationAvecSurprime> lettre = new ArrayList<>();

                lettre.add(requestModel);

                // Fetching the .jrxml file from the resources folder.
                final InputStream stream = this.getClass()
                                .getResourceAsStream("/rapport/acceptationAvecSurprime.jrxml");

                final JasperDesign jasperDesign = JRXmlLoader.load(stream);
                // Compile the Jasper report from .jrxml to .japser
                final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                // Fetching the employees from the data source.
                final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(lettre);

                // Adding the additional parameters to the pdf.
                final Map<String, Object> parameters = new HashMap<>();
                String path2 = getPATH();
                parameters.put("PATH", path2);

                // Filling the report with the employee data and additional parameters
                // information.
                final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

                File pdfFile = File.createTempFile(requestModel.getNomPrenom(), ".pdf");

                JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdfFile));

                file = pdfFile;

                // Retourner fichier PDF
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                .contentType(MediaType.APPLICATION_PDF).body(resource);

        }


         @RequestMapping(value = "bordereauEncaissementt/pdf", method = RequestMethod.POST, produces = "application/pdf")
        public ResponseEntity<InputStreamResource> getBordereauEncaissement(@RequestBody BordereauEncaissementRequestModel requestModel)
                        throws IOException, SQLException, JRException, ParseException {
                int numQuittance;
                String numeroDePolice;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateFormatedCreation=dateFormat.format(requestModel.getCreationDate());
                List<BordereauxEncaissementTableRequestModel> tableEncaissements=new ArrayList<BordereauxEncaissementTableRequestModel>();
                List<Encaissement> encaissements=bordereauRepository.findById(requestModel.getId()).get().getListEncaissement();
                for(Encaissement encaissement:encaissements){
                        numQuittance=cotisationRepository.findById(encaissement.getCotisation().getId()).get().getNumQuittance();
                        numeroDePolice= contratRepo.findById(cotisationRepository.findById(encaissement.getCotisation().getId()).get().getContrat().getId()).get().getNumeroContrat();
                        BordereauxEncaissementTableRequestModel encTable=new BordereauxEncaissementTableRequestModel();
                        encTable.setNumeroEncaissement(encaissement.getNumEncaissement());
                        encTable.setRefEncaissement(encaissement.getNumReference());
                        encTable.setMontantEncaissement(String.valueOf(encaissement.getMontantEncaissement()) );
                        encTable.setModeEncaissement(encaissement.getModeEncaissement());
                        encTable.setDateEncaissement(dateFormat.format(encaissement.getDateEncaissement()));
                        encTable.setNumeroQuittance(String.valueOf(numQuittance));
                        encTable.setNumeroContrat(numeroDePolice);
                        tableEncaissements.add(encTable);
                }
                requestModel.setTableEncaissements(tableEncaissements);
                //Initialisation fichier PDF
                File file;
                ClassPathResource pathResource = new ClassPathResource("rapport/Blank.pdf");
                file = pathResource.getFile();

                // List<BordereauxEncaissementTableRequestModel> employees = new ArrayList<>();

                // employees.add(table);

                // Fetching the .jrxml file from the resources folder.
                final InputStream stream = this.getClass().getResourceAsStream("/rapport/BordoV2.jrxml");

                final JasperDesign jasperDesign = JRXmlLoader.load(stream);
                // Compile the Jasper report from .jrxml to .japser
                final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                // Fetching the employees from the data source.
                final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(requestModel.getTableEncaissements());

                // Adding the additional parameters to the pdf.
                final Map<String, Object> parameters = new HashMap<>();
                String path2 = getPATH();
                parameters.put("PATH", path2);
                parameters.put("CollectionBeanParam", source);
                parameters.put("refBordereau",requestModel.getRefBordereau());
                parameters.put("compteBancaire",requestModel.getCompteBancaire());
                parameters.put("pointVente",requestModel.getPointVente());
                parameters.put("montantTotal",requestModel.getMontantTotal());
                parameters.put("creationDate",dateFormatedCreation);
                // Filling the report with the employee data and additional parameters
                // information.
                final JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

                File pdfFile = File.createTempFile(requestModel.getRefBordereau(), ".pdf");

                JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdfFile));

                file = pdfFile;
                log.info("downloading....");

                // Retourner fichier PDF
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return ResponseEntity.ok().headers(headers).contentLength(file.length())
                                .contentType(MediaType.APPLICATION_PDF).body(resource);

        }

}
