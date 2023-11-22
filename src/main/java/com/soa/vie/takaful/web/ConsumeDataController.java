package com.soa.vie.takaful.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import com.soa.vie.takaful.responsemodels.ProduitModelResponse;
import com.soa.vie.takaful.services.IProduitService;

import com.soa.vie.takaful.util.Constants;
import com.soa.vie.takaful.util.bankingService.AkhdarData;
import com.soa.vie.takaful.util.bankingService.Output;
import com.soa.vie.takaful.util.bankingService.ResponseWS;
import com.soa.vie.takaful.util.bankingService.SOAPYousrClient;
import com.soa.vie.takaful.util.bankingService.SOAPYousrClientHttps;
import com.soa.vie.takaful.util.bankingService.SSLCertificateValidation;
import com.soa.vie.takaful.util.bankingService.ServiceWS;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
// import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

@RestController
@RequestMapping("/api/private")
public class ConsumeDataController {

        @Autowired
        private IProduitService serviceProduit;

        @Autowired
        private ServiceWS serviceWs;

        @Autowired
        private SOAPYousrClient soapYousrClient;

        @Autowired
        private RestTemplate xRestTemplate;

        @Autowired
        private SOAPYousrClientHttps soapYousrClientHttps;
        @Autowired
        Environment env;

        @RequestMapping(value = "/akhdar/rest/api/auth", method = RequestMethod.GET)
        public ResponseEntity<Output> readAkhdarApi()
                        throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

                SSLCertificateValidation.disable();

                String url = Constants.ALAKHDAR_URL_AUTHENTIFICATION;
                //RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                headers.add("user-agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

                Map<String, Object> map = new HashMap<>();
                map.put("clientId", "takaful-client");
                map.put("grantType", "password");
                map.put("username", "takaful_user");
                map.put("password", "a123*123");

                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
                ResponseEntity<Output> response = xRestTemplate.postForEntity(url, entity, Output.class);
                ResponseWS responseData = new ResponseWS();
                responseData.setStatus(1);
                return response;

        }

        @RequestMapping(value = "/akhdar/rest/api/data/{numeroCompte}", method = RequestMethod.GET)
        public ResponseWS readAkhdarData(@PathVariable String numeroCompte)
                        throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, ParseException {

                SSLCertificateValidation.disable();
                String url = Constants.ALAKHDAR_URL + numeroCompte;
                // RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                headers.set("Authorization", "Bearer " +
                                readAkhdarApi().getBody().getAccessToken());
                headers.add("user-agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

                HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);

                ResponseEntity<AkhdarData> response = xRestTemplate.exchange(url, HttpMethod.GET, jwtEntity,
                                AkhdarData.class);

                if (response.getStatusCode().equals(HttpStatus.OK)) {
                        System.out.println("response alAkhdar" + response.getBody());
                        ResponseWS responseData = new ResponseWS();
                        // todo : remove when all dta is present in partner ws
                        responseData.setStatus(1);
                        responseData = serviceWs.getDataOfWebService(response.getBody());
                        return responseData;
                } else {

                        return null;
                }

        }

        @RequestMapping(value = "/soap/yousre", method = RequestMethod.GET)
        public ResponseWS elYousreWS(@RequestParam("ref") String refDossier)
                        throws IOException, SOAPException, SAXException,
                        ParserConfigurationException, ParseException {
                ResponseWS result = new ResponseWS();
                SSLCertificateValidation.disableSslVerification();
                String soapAction = "";
                SOAPMessage soapMessage = this.soapYousrClientHttps.createSOAPRequest(soapAction, refDossier);
                if (env.getProperty("production").equals("true")) {
                        String soapEndpointUrl = Constants.ALYOUS_URL_PROD;
                        SOAPMessage soapMessagereturn = this.soapYousrClientHttps.sendSoapRequest(soapEndpointUrl,
                                        soapMessage);
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        System.out.println("soapMessagereturn : " + soapMessagereturn);

                        soapMessagereturn.writeTo(out);
                        String strMsg = new String(out.toByteArray());
                        String indecator = StringUtils.substringBetween(strMsg, "<successIndicator>",
                                        "</successIndicator>");

                        if (!Constants.SUCCESS.equals(indecator)) {
                                System.out.println("error");
                                result = null;
                        } else {
                                result = this.soapYousrClient.parserResponse(strMsg);
                        }
                        return result;
                } else {
                        String soapEndpointUrl = Constants.ALYOUS_URL_TEST;
                        SOAPMessage soapMessagereturn = this.soapYousrClientHttps.sendSoapRequest(soapEndpointUrl,
                                        soapMessage);
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        System.out.println("soapMessagereturn : " + soapMessagereturn);

                        soapMessagereturn.writeTo(out);
                        String strMsg = new String(out.toByteArray());
                        String indecator = StringUtils.substringBetween(strMsg, "<successIndicator>",
                                        "</successIndicator>");

                        if (!Constants.SUCCESS.equals(indecator)) {
                                System.out.println("error");
                                result = null;
                        } else {
                                result = this.soapYousrClient.parserResponse(strMsg);
                        }
                        return result;
                }

        }

        @GetMapping("getDataByPartenaire")
        public ResponseWS getDataByPartenaire(@RequestParam("partenaireCode") String partenaireCode,
                        @RequestParam("referenceDossier") String referenceDossier)
                        throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException,
                        IOException,
                        SOAPException, SAXException, ParserConfigurationException, ParseException, InterruptedException,
                        ExecutionException {
                ResponseWS data = new ResponseWS();
                if (Constants.CODE_PARTENAIRE_ALKHDAR.equals(partenaireCode)) {
                        // Thread.sleep(1000L);
                        System.out.println("*****************Akhdar API***********");
                        Thread.sleep(2000L);
                        data = this.readAkhdarData(referenceDossier);

                }
                if (Constants.CODE_PARTENAIRE_ALYOUSER.equals(partenaireCode)) {
                        System.out.println("*****************alyousr API***********");
                        data = this.elYousreWS(referenceDossier);
                }
                return data;
        }

        @GetMapping("produitByCode")
        public ProduitModelResponse getProduitByCode(@RequestParam("code") String code)
                        throws InterruptedException, ExecutionException {
                return this.serviceProduit.getProduitByCode(code);

        }

}
