package pe.gob.pcm.sgd.firmaperu.clientewebtest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import pe.gob.pcm.sgd.firmaperu.clientewebtest.dto.SignatureParameters;
import pe.gob.pcm.sgd.firmaperu.clientewebtest.util.Token;

@Path("/")
public class ParamRest {

    @POST
    @Path("/param")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)//x-www-form-urlencoded	
    @Produces(MediaType.TEXT_PLAIN)
    //@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response param(@FormParam("param_token") String token) {
        ResponseBuilder response = null;
        try {
            if (token != null && !token.equals("") && token.equals("1626476967")) {
                String host = "http://localhost:8080/firmaperuclientewebtest";                
                SignatureParameters signatureParameters = new SignatureParameters();
                signatureParameters.setSignatureFormat("PAdES");
                signatureParameters.setSignatureLevel("B");
                signatureParameters.setSignaturePackaging("");
                signatureParameters.setDocumentToSign(host + "/doc/demo.pdf");
                signatureParameters.setCertificateFilter(".*");
                signatureParameters.setWebTsa("");
                signatureParameters.setUserTsa("");
                signatureParameters.setPasswordTsa("");
                signatureParameters.setTheme("claro");
                signatureParameters.setVisiblePosition(false);
                signatureParameters.setContactInfo("");
                signatureParameters.setSignatureReason("Soy el autor del documento");
                signatureParameters.setBachtOperation(false);
                signatureParameters.setOneByOne(true);
                signatureParameters.setSignatureStyle(1);
                signatureParameters.setImageToStamp(host + "/doc/stamp.png");
                signatureParameters.setStampTextSize(14);
                signatureParameters.setStampWordWrap(37);
                signatureParameters.setRole("Analista de servicios");
                signatureParameters.setStampPage(1);//*//
                signatureParameters.setPositionx(300);//*//
                signatureParameters.setPositiony(300);//*//              
                signatureParameters.setUploadDocumentSigned(host + "/api/upload/1626497352");
                signatureParameters.setToken(Token.getToken());
                
                ObjectMapper mapper = new ObjectMapper();
                String param = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(signatureParameters);
                param = Base64.getEncoder().encodeToString(param.getBytes(StandardCharsets.UTF_8));
                response = Response.status(Response.Status.OK).entity(param);
            } else {
                response = Response.status(Response.Status.UNAUTHORIZED);
            }
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage());
        }
        return response.build();
    }
}
