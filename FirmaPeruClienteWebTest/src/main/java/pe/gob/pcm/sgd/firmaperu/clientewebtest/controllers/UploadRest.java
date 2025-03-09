package pe.gob.pcm.sgd.firmaperu.clientewebtest.controllers;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import pe.gob.pcm.sgd.firmaperu.clientewebtest.constants.AppConstants;

@Path("/")
public class UploadRest {

    @POST
    @Path("/upload/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(
            @PathParam("id") String id,
            @FormDataParam("signed_file") InputStream inputStream,
            @FormDataParam("signed_file") FormDataContentDisposition formDataContentDisposition) {
        ResponseBuilder response = null;
        try {
            if (id.equals("1626497352")) {
                String name = formDataContentDisposition.getFileName();     
                File uploadDir = new File(AppConstants.DOCUMENTS);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                //String filePath = uploadDir.getPath() + File.separator + "document_to_sign[FP].pdf";
                String filePath = uploadDir.getPath() + File.separator + name;
                File storeFile = new File(filePath);
                if (storeFile.exists()) {
                    storeFile.delete();
                }
                File file = new File(filePath);
                Files.copy(
                        inputStream,
                        file.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
                inputStream.close();
                response = Response.status(Response.Status.OK);
            } else {
                response = Response.status(Response.Status.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage());
        }
        return response.build();
    }
}
