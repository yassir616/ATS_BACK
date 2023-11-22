package com.soa.vie.takaful.web;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.soa.vie.takaful.entitymodels.Categorie;
import com.soa.vie.takaful.entitymodels.Partenaire;
import com.soa.vie.takaful.entitymodels.PoolInvestissment;
import com.soa.vie.takaful.entitymodels.Risque;
import com.soa.vie.takaful.repositories.ICategorieRepository;
import com.soa.vie.takaful.repositories.IPartenaireRepository;
import com.soa.vie.takaful.repositories.IRetraiteContratRepository;
import com.soa.vie.takaful.repositories.IRetraiteProduitRepository;
import com.soa.vie.takaful.repositories.IRisqueRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateRetraiteProduit;
import com.soa.vie.takaful.requestmodels.UserSingInRequestModel;
import com.soa.vie.takaful.util.ModeCalculCapitalConstitue;
import com.soa.vie.takaful.util.ModeGestion;
import com.soa.vie.takaful.util.NatureConditionDisciplinaire;
import com.soa.vie.takaful.util.NatureFiscale;
import com.soa.vie.takaful.util.RegimeFiscal;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
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
class RetraitProduitControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ICategorieRepository categorieRepository;

    @Mock
    private IRisqueRepository risqueRepository;

    @Mock
    private IPartenaireRepository partenaireRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Autowired
    private IRetraiteProduitRepository iRetraiteProduitRepository;

    @Autowired
    private IRetraiteContratRepository iRetraiteContratRepository;

  

    private static CreateAndUpdateRetraiteProduit createAndUpdateRetraiteProduit;
    private static PoolInvestissment poolInvestissment;
    private static Categorie categorie;
    private static Risque risque;
    private static Partenaire partenaire;
    private static String token;
    private static String retrait_produit_id;

    @BeforeAll
    void setUp() throws Exception {

        iRetraiteContratRepository.deleteAll();
        iRetraiteProduitRepository.deleteAll();

        poolInvestissment = new PoolInvestissment("pool");
        List<PoolInvestissment> poolInvestissmentList = new ArrayList<>();
        poolInvestissmentList.add(poolInvestissment);
        createAndUpdateRetraiteProduit = new CreateAndUpdateRetraiteProduit(poolInvestissmentList, NatureFiscale.EPARGNE,120,3,true,100,1.5f, ModeGestion.MOUDARABA,50,50,50, ModeCalculCapitalConstitue.METHOD1,false,3,true, NatureConditionDisciplinaire.FIXE,20,true,false,3,NatureConditionDisciplinaire.ASAISIR,50,500,6, RegimeFiscal.RETRAITE);
        createAndUpdateRetraiteProduit.setCategorie(categorie);
        createAndUpdateRetraiteProduit.setRisque(risque);
        createAndUpdateRetraiteProduit.setPartenaire(partenaire);
        poolInvestissmentList.get(0).setId("4");
        createAndUpdateRetraiteProduit = new CreateAndUpdateRetraiteProduit(poolInvestissmentList, NatureFiscale.EPARGNE,120,3,true,100,1.5f, ModeGestion.MOUDARABA,50,50,50, ModeCalculCapitalConstitue.METHOD1,false,3,true, NatureConditionDisciplinaire.FIXE,20,true,false,3,NatureConditionDisciplinaire.ASAISIR,50,500,6, RegimeFiscal.RETRAITE);
        categorie = new Categorie();
        partenaire = new Partenaire();
        risque= new Risque();
        categorie.setId("b1d05fd4-1ac4-4e44-9055-ba58960d91d4");
        partenaire.setId("cfe9b7be-5ab1-4deb-b59f-7423def5c1e1");
        risque.setId("c6d2d89b-196d-4969-a27d-f86f3f496450");
        createAndUpdateRetraiteProduit.setCategorie(categorie);
        createAndUpdateRetraiteProduit.setPartenaire(partenaire);
        createAndUpdateRetraiteProduit.setRisque(risque);

        UserSingInRequestModel userSingInRequestModel = new UserSingInRequestModel("moaiim@email.com","123456");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/public/login")
                .with(csrf())
                .content(objectMapper.writeValueAsString(userSingInRequestModel))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        token =result.getResponse().getHeader("Authorization");
    }

    @BeforeEach
    void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

    }


    @Test
    @Order(1)
    void createRetraitProduitHttpResponse() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/private/retraitproduit")

                                                .content(objectMapper.writeValueAsString(createAndUpdateRetraiteProduit))
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.APPLICATION_JSON)
                                                .header("Authorization", token)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        retrait_produit_id = JsonPath.read(result.getResponse().getContentAsString(),"$.id");

        Assert.assertTrue(iRetraiteProduitRepository.findById(retrait_produit_id).isPresent());


    }

    @Test
    void updateRetraitProduitHttpResponse() throws Exception {

        
        CreateAndUpdateRetraiteProduit createAndUpdateRetraiteProduit1 = new CreateAndUpdateRetraiteProduit();
        BeanUtils.copyProperties(createAndUpdateRetraiteProduit,createAndUpdateRetraiteProduit1);
        createAndUpdateRetraiteProduit1.setRevenuGlobal(300);
        createAndUpdateRetraiteProduit1.setNatureFiscale(NatureFiscale.RETRAITE);
        createAndUpdateRetraiteProduit1.getPoolInvestissment().get(0).setCreationDate(null);
        createAndUpdateRetraiteProduit1.getPoolInvestissment().get(0).setFluxId(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/private/retraitproduit/"+ retrait_produit_id)
                .content(objectMapper.writeValueAsString(createAndUpdateRetraiteProduit1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String actual_json_response=result.getResponse().getContentAsString();

        String expected_json_response=objectMapper.writeValueAsString(createAndUpdateRetraiteProduit1);
        Assert.assertEquals(expected_json_response,actual_json_response);
    }

    @Test
    void getAllRetraitProduitsHttpResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/private/retraitproduit?page=0&" +
                "limit=5&sort=revenuGlobal&direction=asc")
                .with(csrf())
                .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(1));
    }

    @Test
    void getRetraitProduitByIdHttpResponse() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/private/retraitproduit/" + retrait_produit_id)
                .content(objectMapper.writeValueAsString(createAndUpdateRetraiteProduit))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(retrait_produit_id,JsonPath.read(result.getResponse().getContentAsString(),"$.id"));
    }

}