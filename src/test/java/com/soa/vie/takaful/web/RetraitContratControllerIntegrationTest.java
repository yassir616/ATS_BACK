package com.soa.vie.takaful.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.soa.vie.takaful.entitymodels.Categorie;
import com.soa.vie.takaful.entitymodels.Partenaire;
import com.soa.vie.takaful.entitymodels.Periodicite;
import com.soa.vie.takaful.entitymodels.PersonnePhysique;
import com.soa.vie.takaful.entitymodels.PointVente;
import com.soa.vie.takaful.entitymodels.PoolInvestissment;
import com.soa.vie.takaful.entitymodels.RetraiteProduit;
import com.soa.vie.takaful.entitymodels.Risque;
import com.soa.vie.takaful.entitymodels.SecteurActivite;
import com.soa.vie.takaful.entitymodels.TypePointVente;
import com.soa.vie.takaful.repositories.ICategorieRepository;
import com.soa.vie.takaful.repositories.IPartenaireRepository;
import com.soa.vie.takaful.repositories.IPeriodiciteRepository;
import com.soa.vie.takaful.repositories.IPersonnePhysiqueRepository;
import com.soa.vie.takaful.repositories.IPointVenteRepository;
import com.soa.vie.takaful.repositories.IRetraiteContratRepository;
import com.soa.vie.takaful.repositories.IRetraiteProduitRepository;
import com.soa.vie.takaful.repositories.IRisqueRepository;
import com.soa.vie.takaful.repositories.ISecteurActiviteRepository;
import com.soa.vie.takaful.repositories.ITypePointVenteRepository;
import com.soa.vie.takaful.requestmodels.CreateUpdateRetraiteContrat;
import com.soa.vie.takaful.requestmodels.UserSingInRequestModel;
import com.soa.vie.takaful.responsemodels.RetraiteContratResponseModel;
import com.soa.vie.takaful.util.BenificiareCasDeces;
import com.soa.vie.takaful.util.ContratStatus;
import com.soa.vie.takaful.util.ModeCalculCapitalConstitue;
import com.soa.vie.takaful.util.ModeGestion;
import com.soa.vie.takaful.util.NatureConditionDisciplinaire;
import com.soa.vie.takaful.util.NatureFiscale;
import com.soa.vie.takaful.util.ProfessionEnum;
import com.soa.vie.takaful.util.RegimeFiscal;
import com.soa.vie.takaful.util.SexeEnum;
import com.soa.vie.takaful.util.SituationFamilialeEnum;
import com.soa.vie.takaful.util.VoisEnum;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RetraitContratControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private WebApplicationContext webApplicationContext;

        @Autowired
        private IRetraiteContratRepository iRetraiteContratRepository;

        @Autowired
        private IPartenaireRepository iPartenaireRepository;

        @Autowired
        private ISecteurActiviteRepository iSecteurActiviteRepository;

        @Autowired
        private IPointVenteRepository iPointVenteRepository;

        @Autowired
        private ITypePointVenteRepository iTypePointVenteRepository;

        @Autowired
        private IPeriodiciteRepository iPeriodiciteRepository;

        @Autowired
        private IPersonnePhysiqueRepository iPersonnePhysiqueRepository;

        @Autowired
        private IRetraiteProduitRepository retraiteProduitRepository;

        @Autowired
        private ICategorieRepository iCategorieRepository;

        @Autowired
        private IRisqueRepository risqueRepository;

        private static CreateUpdateRetraiteContrat createAndUpdateRetraiteContrat;
        private static RetraiteProduit retraiteProduit;
        private static PersonnePhysique souscripteur;
        private static PersonnePhysique assuree;
        private static PointVente pointVente;
        private static Periodicite periodicite;
        private static String retraitContratId;
        private static String token;
        private static PoolInvestissment poolInvestissment;
        private static Categorie categorie;
        private static Risque risque;
        private static Partenaire partenaire;
        private static TypePointVente typePointVente;
        private static SecteurActivite secteurActivite;

        @BeforeAll
        void setUp() throws Exception {

                // iPersonneRepository.deleteAll();
                // iPersonnePhysiqueRepository.deleteAll();
                // iRetraiteContratRepository.deleteAll();
                // retraiteProduitRepository.deleteAll();

                partenaire = new Partenaire();
                partenaire.setId("partenaireId");
                partenaire.setCode("codePartenaire");

                periodicite = new Periodicite();
                periodicite.setId("periodiciteId");
                periodicite.setAbb("Abb");
                periodicite.setLibelle("periodiciteLibelle");

                typePointVente = new TypePointVente();
                typePointVente.setId("typePointVenteId");
                typePointVente.setCode("typePointVenteCode");
                typePointVente.setLibelle("typePointVenteLibelle");

                secteurActivite = new SecteurActivite();
                secteurActivite.setId("secteurActiviteId");
                secteurActivite.setLibelle("secteurActiviteLibelle");

                iSecteurActiviteRepository.save(secteurActivite);
                iTypePointVenteRepository.save(typePointVente);
                iPartenaireRepository.save(partenaire);
                iPeriodiciteRepository.save(periodicite);

                pointVente = new PointVente();
                pointVente.setId("pointVenteId");
                pointVente.setAbb("pointVenteAbb");
                pointVente.setTypePointVente(typePointVente);
                pointVente.setSecteurActivite(secteurActivite);
                pointVente.setPartenairepv(partenaire);

                iPointVenteRepository.save(pointVente);

                souscripteur = new PersonnePhysique();
                souscripteur.setId("souscripteurId");
                souscripteur.setCin("CIN3");
                souscripteur.setAdressPays("MAROC");
                souscripteur.setAdressVille("CASABLANCA");
                souscripteur.setNationalite("MAROCAIN");
                // souscripteur.setProfession(ProfessionEnum.INGENIEUR_SI);
                souscripteur.setSituationFamiliale(SituationFamilialeEnum.CELIBATAIRE);
                souscripteur.setAdressVois(VoisEnum.QUARTIER);
                souscripteur.setSexe(SexeEnum.HOMME);

                assuree = new PersonnePhysique();
                assuree.setNom("Said");
                assuree.setId("assureeId");
                assuree.setCin("CIN2");
                assuree.setAdressPays("MAROC");
                assuree.setAdressVille("CASABLANCA");
                assuree.setNationalite("MAROCAIN");
                // assuree.setProfession(ProfessionEnum.INGENIEUR_SI);
                assuree.setSituationFamiliale(SituationFamilialeEnum.CELIBATAIRE);
                assuree.setAdressVois(VoisEnum.QUARTIER);
                assuree.setSexe(SexeEnum.HOMME);

                iPersonnePhysiqueRepository.save(souscripteur);
                iPersonnePhysiqueRepository.save(assuree);

                poolInvestissment = new PoolInvestissment("pool");
                List<PoolInvestissment> poolInvestissmentList = new ArrayList<>();
                poolInvestissmentList.add(poolInvestissment);
                retraiteProduit = new RetraiteProduit(poolInvestissmentList, NatureFiscale.EPARGNE, 120, 3, true, 100,
                                1.5f, ModeGestion.MOUDARABA, 50, 50, 50, ModeCalculCapitalConstitue.METHOD1, false, 3,
                                true, NatureConditionDisciplinaire.FIXE, 20, true, false, 3,
                                NatureConditionDisciplinaire.ASAISIR, 50, 500, 6, RegimeFiscal.RETRAITE);
                poolInvestissment.setId("4");

                categorie = new Categorie();
                categorie.setId("b1d05fd4-1ac4-4e44-9055-ba58960d91d4");
                categorie.setLibelle("myCategory");
                categorie.setCode("code myCategory");

                partenaire = new Partenaire();
                partenaire.setId("cfe9b7be-5ab1-4deb-b59f-7423def5c1e1");

                risque = new Risque();
                risque.setId("c6d2d89b-196d-4969-a27d-f86f3f496450");
                risque.setBundle("bundle");
                risque.setCode("code");
                risque.setIcon("icone");
                risque.setLibelle("my risque libelle");

                iCategorieRepository.save(categorie);
                risqueRepository.save(risque);
                iPartenaireRepository.save(partenaire);

                retraiteProduit.setCategorie(categorie);
                retraiteProduit.setPartenaire(partenaire);
                retraiteProduit.setRisque(risque);

                UserSingInRequestModel userSingInRequestModel = new UserSingInRequestModel("moaiim@email.com",
                                "123456");

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/public/login").with(csrf())
                                .content(objectMapper.writeValueAsString(userSingInRequestModel))
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
                token = result.getResponse().getHeader("Authorization");

                MvcResult resultRetrait = mockMvc.perform(MockMvcRequestBuilders.post("/api/private/retraitproduit")

                                .content(objectMapper.writeValueAsString(retraiteProduit))
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", token)).andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();
                retraiteProduit.setId(JsonPath.read(resultRetrait.getResponse().getContentAsString(), "$.id"));

                createAndUpdateRetraiteContrat = new CreateUpdateRetraiteContrat(true, true, true,
                                BenificiareCasDeces.HERITIERS, 450, 450, null, 3, "452", new Date());

                createAndUpdateRetraiteContrat.setProduit(retraiteProduit);
                createAndUpdateRetraiteContrat.setSouscripteur(souscripteur);
                createAndUpdateRetraiteContrat.setAssure(assuree);
                createAndUpdateRetraiteContrat.setPointVente(pointVente);
                createAndUpdateRetraiteContrat.setPeriodicite(periodicite);
                createAndUpdateRetraiteContrat.setNumeroContrat("123456");
                createAndUpdateRetraiteContrat.setStatus(ContratStatus.ACCEPTED);

        }

        @BeforeEach
        void setupMockMvc() {
                mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        }

        @Test

        // Create
        void a() throws Exception {
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/private/retraitcontrat")

                                .content(objectMapper.writeValueAsString(createAndUpdateRetraiteContrat))
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", token)).andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                retraitContratId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

                Assert.assertTrue(iRetraiteContratRepository.findById(retraitContratId).isPresent());
        }

        @Test

        // Update

        void b() throws Exception {
                CreateUpdateRetraiteContrat createAndUpdateRetraiteContrat1 = new CreateUpdateRetraiteContrat();
                BeanUtils.copyProperties(createAndUpdateRetraiteContrat, createAndUpdateRetraiteContrat1);
                createAndUpdateRetraiteContrat1.setMontantContributionPeriodique(1500);
                MvcResult result1 = mockMvc
                                .perform(MockMvcRequestBuilders.put("/api/private/retraitcontrat/" + retraitContratId)
                                                .content(objectMapper
                                                                .writeValueAsString(createAndUpdateRetraiteContrat1))
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.APPLICATION_JSON).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                String actual_json_response = result1.getResponse().getContentAsString();

                RetraiteContratResponseModel retraiteContratResponseModel = new RetraiteContratResponseModel();
                BeanUtils.copyProperties(createAndUpdateRetraiteContrat1, retraiteContratResponseModel);

                String expected_json_response = objectMapper.writeValueAsString(retraiteContratResponseModel);
                Double actual_Value = JsonPath.read(actual_json_response, "$.montantContributionPeriodique");
                Double expected_Value = JsonPath.read(expected_json_response, "$.montantContributionPeriodique");
                Assert.assertEquals(expected_Value, actual_Value);

        }

        @Test

        // getRetraitContratsByStatus
        void c() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/api/private/retraitcontrat/status/" + ContratStatus.ACCEPTED).param("page", "0")
                                .param("limit", "5").with(csrf()).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1));
        }

        @Test
        // getRetraitContratsByStatusFalse
        void d() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/api/private/retraitcontrat/status/" + ContratStatus.EN_COURS_RESILIATION)
                                .param("page", "0").param("limit", "5").with(csrf()).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(0));
        }

        @Test

        // getContratSearchByNom
        void e() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/private/retraitcontrat/search").param("page", "0")
                                .param("limit", "5").param("by", "nom").param("for", assuree.getNom()).with(csrf())
                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1));
        }

        @Test
        // getContratSearchByCin
        void e1() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/private/retraitcontrat/search").param("page", "0")
                                .param("limit", "5").param("by", "cin").param("for", assuree.getCin()).with(csrf())
                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1));
        }

        @Test
        // getContratSearchByNumeroContrat
        void e2() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/private/retraitcontrat/search").param("page", "0")
                                .param("limit", "5").param("by", "numeroContrat")
                                .param("for", createAndUpdateRetraiteContrat.getNumeroContrat()).with(csrf())
                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1));
        }

        @Test
        void f() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/private/retraitcontrat/search").param("page", "0")
                                .param("limit", "5").param("by", "nom").param("for", "numeroContrat").with(csrf())
                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(0));
        }

        @Test
        void g() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/api/private/retraitcontrat?page=0&" + "limit=5&sort=dureeContrat&direction=asc")
                                .with(csrf()).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1));
        }

}
