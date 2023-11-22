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
import com.soa.vie.takaful.entitymodels.RetraiteContrat;
import com.soa.vie.takaful.entitymodels.RetraiteProduit;
import com.soa.vie.takaful.entitymodels.Risque;
import com.soa.vie.takaful.entitymodels.SecteurActivite;
import com.soa.vie.takaful.entitymodels.TypePointVente;
import com.soa.vie.takaful.repositories.ICotisationRepository;
import com.soa.vie.takaful.repositories.IPartenaireRepository;
import com.soa.vie.takaful.repositories.IPeriodiciteRepository;
import com.soa.vie.takaful.repositories.IPersonnePhysiqueRepository;
import com.soa.vie.takaful.repositories.IPersonneRepository;
import com.soa.vie.takaful.repositories.IPointVenteRepository;
import com.soa.vie.takaful.repositories.IPrestationRachatTotalRepository;
import com.soa.vie.takaful.repositories.IProduitRepository;
import com.soa.vie.takaful.repositories.IRetraiteContratRepository;
import com.soa.vie.takaful.repositories.IRetraiteProduitRepository;
import com.soa.vie.takaful.repositories.ISecteurActiviteRepository;
import com.soa.vie.takaful.repositories.ITypePointVenteRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateCotisation;
import com.soa.vie.takaful.requestmodels.UserSingInRequestModel;
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
public class RetraitCotisationControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private WebApplicationContext webApplicationContext;

        @Autowired
        private IRetraiteContratRepository iRetraiteContratRepository;

        @Autowired
        private IPrestationRachatTotalRepository prestationRachatTotalRepository;

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
        private IRetraiteProduitRepository iRetraiteProduitRepository;

        @Autowired
        private IPersonnePhysiqueRepository iPersonnePhysiqueRepository;

        @Autowired
        private IPersonneRepository iPersonneRepository;

        @Autowired
        private ICotisationRepository iCotisationRepository;

        @Autowired
        private IProduitRepository iProduitRepository;

        private static RetraiteContrat retraiteContrat;
        private static RetraiteProduit retraiteProduit;
        private static CreateAndUpdateCotisation createAndUpdateCotisation;
        private static PersonnePhysique souscripteur;
        private static PointVente pointVente;
        private static Periodicite periodicite;
        private static String retraitContratId;
        private static String cotisationId;
        private static String token;
        private static PoolInvestissment poolInvestissment;
        private static Categorie categorie;
        private static Risque risque;
        private static Partenaire partenaire;
        private static TypePointVente typePointVente;
        private static SecteurActivite secteurActivite;

        @BeforeAll
        void setUp() throws Exception {

                iPersonnePhysiqueRepository.deleteAll();
                iPersonneRepository.deleteAll();
                iRetraiteProduitRepository.deleteAll();
                iProduitRepository.deleteAll();
                prestationRachatTotalRepository.deleteAll();
                iRetraiteContratRepository.deleteAll();
                iCotisationRepository.deleteAll();

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

                iPersonnePhysiqueRepository.save(souscripteur);

                poolInvestissment = new PoolInvestissment("pool");
                List<PoolInvestissment> poolInvestissmentList = new ArrayList<>();
                poolInvestissmentList.add(poolInvestissment);

                retraiteProduit = new RetraiteProduit(poolInvestissmentList, NatureFiscale.EPARGNE, 120, 3, true, 100,
                                1.5f,
                                ModeGestion.MOUDARABA, 50, 50, 50, ModeCalculCapitalConstitue.METHOD1, false, 3, true,
                                NatureConditionDisciplinaire.FIXE, 20, true, false, 3,
                                NatureConditionDisciplinaire.ASAISIR, 50, 500, 6,
                                RegimeFiscal.RETRAITE);
                poolInvestissment.setId("4");
                categorie = new Categorie();
                partenaire = new Partenaire();
                risque = new Risque();
                categorie.setId("b1d05fd4-1ac4-4e44-9055-ba58960d91d4");
                partenaire.setId("cfe9b7be-5ab1-4deb-b59f-7423def5c1e1");
                risque.setId("c6d2d89b-196d-4969-a27d-f86f3f496450");
                retraiteProduit.setCategorie(categorie);
                retraiteProduit.setPartenaire(partenaire);
                retraiteProduit.setRisque(risque);
                retraiteProduit.setId("retraitProduitId");

                retraiteContrat = new RetraiteContrat();
                retraiteContrat.setSouscripteurIsAssure(true);
                retraiteContrat.setSouscripteur(souscripteur);
                retraiteContrat.setAssure(souscripteur);
                retraiteContrat.setPointVente(pointVente);
                retraiteContrat.setPeriodicite(periodicite);
                retraiteContrat.setNumeroContrat(" numeroContrat1");
                retraiteContrat.setStatus(ContratStatus.ACCEPTED);

                createAndUpdateCotisation = new CreateAndUpdateCotisation();
                createAndUpdateCotisation.setDatePrelevement(new Date());
                createAndUpdateCotisation.setContrat(retraiteContrat.getId());
                createAndUpdateCotisation.setMontantCotisation(200);
                createAndUpdateCotisation.setContributionPure(200);
                createAndUpdateCotisation.setMontantTTC(200);
                createAndUpdateCotisation.setSolde(30000);

                UserSingInRequestModel userSingInRequestModel = new UserSingInRequestModel("moaiim@email.com",
                                "123456");

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.post("/api/public/login").with(csrf())
                                                .content(objectMapper.writeValueAsString(userSingInRequestModel))
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
                token = result.getResponse().getHeader("Authorization");

                MvcResult resultRetrait = mockMvc.perform(MockMvcRequestBuilders.post("/api/private/retraitproduit")

                                .content(objectMapper.writeValueAsString(retraiteProduit))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
                retraiteProduit.setId(JsonPath.read(resultRetrait.getResponse().getContentAsString(), "$.id"));

                retraiteContrat.setProduit(retraiteProduit);

                MvcResult resultContrat = mockMvc.perform(MockMvcRequestBuilders.post("/api/private/retraitcontrat")

                                .content(objectMapper.writeValueAsString(retraiteContrat))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                retraitContratId = JsonPath.read(resultContrat.getResponse().getContentAsString(), "$.id");

        }

        @BeforeEach
        void setupMockMvc() {
                mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        }

        // Create
        @Test
        void a() throws Exception {
                Double solde;
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/private/addCotisation")
                                .content(objectMapper.writeValueAsString(createAndUpdateCotisation))
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", token)).andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn();

                cotisationId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
                solde = JsonPath.read(result.getResponse().getContentAsString(), "$.solde");

                boolean checkSolde = solde == 200;

                Assert.assertTrue(iCotisationRepository.findById(cotisationId).isPresent());
                Assert.assertTrue(checkSolde);
        }

        // getCotisationsByIdContratExist
        @Test
        void b() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/private/cotisation/contrat/" + retraitContratId + "")
                                .with(csrf()).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
        }

        // getCotisationsByIdContratNotExist
        @Test
        void c() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/private/cotisation/contrat/3453").with(csrf())
                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
        }

        // getAllCotisations
        @Test
        void d() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/private/cotisations?page=0&" + "limit=5").with(csrf())
                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements")
                                                .value(1));
        }

}
