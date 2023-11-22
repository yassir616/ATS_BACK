package com.soa.vie.takaful.util.bankingService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.soap.*;

import org.springframework.stereotype.Component;

@Component
public class SOAPYousrClientHttps {

    public  SOAPMessage sendSoapRequest(String endpointUrl, SOAPMessage request) {
        try {
            final boolean isHttps = endpointUrl.toLowerCase().startsWith("https");
            HttpsURLConnection httpsConnection = null;
            // Open HTTPS connection
            if (isHttps) {
                // Create SSL context and trust all certificates
                SSLContext sslContext = SSLContext.getInstance("SSL");
                TrustManager[] trustAll = new TrustManager[] { new TrustAllCertificates() };
                sslContext.init(null, trustAll, new java.security.SecureRandom());
                // Set trust all certificates context to HttpsURLConnection
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                // Open HTTPS connection
                URL url = new URL(endpointUrl);
                httpsConnection = (HttpsURLConnection) url.openConnection();
                // Trust all hosts
                httpsConnection.setHostnameVerifier(new TrustAllHosts());
                // Connect
                httpsConnection.connect();
            }
            // Send HTTP SOAP request and get response
            SOAPConnection soapConnection = SOAPConnectionFactory.newInstance().createConnection();
            SOAPMessage response = soapConnection.call(request, endpointUrl);
            // Close connection
            soapConnection.close();
            // Close HTTPS connection
            if (isHttps) {
                httpsConnection.disconnect();
            }
            return response;
        } catch (SOAPException | IOException | NoSuchAlgorithmException | KeyManagementException ex) {
            // Do Something
        }
        return null;
    }

    private  class TrustAllCertificates implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    private  class TrustAllHosts implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public  SOAPMessage createSOAPRequest(String soapAction, String id) throws IOException, SOAPException {

        String sr = "<?xml version='1.0' encoding='utf-8'?>";
        sr += "<soapenv:Envelope xmlns:tak='http://temenos.com/TAKAFUL' xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'>";
        sr += "<soapenv:Header/>";
        sr += "<soapenv:Body>";
        sr += "<tak:WSCONS>";
        sr += "<!--Optional:-->";
        sr += "<WebRequestCommon>";
        sr += "<!--Optional:-->";
        sr += "<company>MA0010001</company>";
        sr += "<password>Aa123+123</password>";
        sr += "<userName>MAMDA1</userName>";
        sr += "</WebRequestCommon>";
        sr += "<!--Optional:-->";
        sr += "<TNAENQDYNLISTType>";
        sr += "<!--Zero or more repetitions:-->";
        sr += "<enquiryInputCollection>";
        sr += "<!--Optional:-->";
        sr += "<columnName>REF</columnName>";
        sr += "<!--Optional:-->";
        sr += "<criteriaValue>" + id + "</criteriaValue>";
        sr += "<!--Optional:-->";
        sr += "<operand>EQ</operand>";
        sr += "</enquiryInputCollection>";
        sr += "</TNAENQDYNLISTType>";
        sr += "</tak:WSCONS>";
        sr += "</soapenv:Body>";
        sr += "</soapenv:Envelope>";

        InputStream is = new ByteArrayInputStream(sr.getBytes());
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null, is);

        return soapMessage;
    }
}