package pe.gob.pcm.sgd.firmaperu.clientewebtest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import pe.gob.pcm.sgd.firmaperu.clientewebtest.constants.AppConstants;

public class Token {

    public static String getToken() {
        String token = null;        
        Client client = null;
        Response response = null;
        try {
            Map<String, String> map = loadCredentials();
            //
            URL url = new URL(map.get("token_url"));
            client = getClientBuilder(String.class, url);
            WebTarget target = client.target(url.toURI());
            Invocation.Builder builder = target.request();
            MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
            formData.add("client_id", map.get("client_id"));
            formData.add("client_secret", map.get("client_secret"));
            response = builder.post(Entity.form(formData));
            int responseCode = response.getStatus();
            if (responseCode == 200) {
                token = response.readEntity(String.class);
            } else {
                System.err.println("No autorizado, código de error: " + responseCode + " " + url.toURI().toString());
            }
        } catch (MalformedURLException | URISyntaxException ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return token;
    }

    private static Map<String, String> loadCredentials() {
        Map<String, String> map = null;
        File file = new File(AppConstants.CREDENTIALS);
        try {           
            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(file, Map.class);            
        } catch (IOException e) {
            System.err.println(e.getMessage());            
        }
        return map;
    }

    private static Client getClientBuilder(Class<?> register, URL url) {
        Client result = null;
        try {
            if (url.getProtocol().equalsIgnoreCase("https")) {
                TrustManager[] trustManager = new X509TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }};
                SSLContext context = SSLContext.getInstance("SSL");
                context.init(null, trustManager, null);
                result = ClientBuilder.newBuilder()
                        .hostnameVerifier(new HostnameVerifier() {
                            @Override
                            public boolean verify(String arg0, SSLSession arg1) {
                                return false;
                            }
                        })
                        .sslContext(context)
                        .register(register)
                        .build();
            } else if (url.getProtocol().equalsIgnoreCase("http")) {
                result = ClientBuilder.newClient().register(register);
            }
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}
