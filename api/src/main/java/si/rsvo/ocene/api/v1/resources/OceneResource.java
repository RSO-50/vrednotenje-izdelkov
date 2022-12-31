package si.rsvo.ocene.api.v1.resources;
import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.rsvo.ocene.services.beans.OcenaBean;
import si.rsvo.uporabniki.lib.Ocena;
import si.rsvo.ocene.services.config.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;


@Log
@ApplicationScoped
@Path("/ocene")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OceneResource {

    private Logger log = Logger.getLogger(OceneResource.class.getName());
    @Inject
    private RestProperties restProperties;
    @Inject
    private OcenaBean ocenaBean;

    @Operation(description = "Get all ocene", summary = "Get all ocene")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of uporabniki",
                    content = @Content(schema = @Schema(implementation = Ocena.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    @Path("/")
    public Response getOcene() {

        List<Ocena> ocene = ocenaBean.getOcene();

        return Response.status(Response.Status.OK).entity(ocene).build();
    }

    @Operation(description = "Get ocena by id", summary = "Get ocena by id")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of uporabniki",
                    content = @Content(schema = @Schema(implementation = Ocena.class)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    @Path("/{id}")
    public Response getOcenaById(@Parameter(description = "Ocena ID.", required = true)
                                         @PathParam("id") Integer id) {

        Ocena ocena = ocenaBean.getOcenaById(id);

        return Response.status(Response.Status.OK).entity(ocena).build();
    }

    @Operation(description = "Add ocena.", summary = "Add ocena")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Ocena successfully added."
            )
    })
    @POST
    @Path("/")
    public Response createOcena(@RequestBody(
            description = "DTO object containing ocena.",
            required = true, content = @Content(
            schema = @Schema(implementation = Ocena.class))) Ocena ocena) {

        ocena = ocenaBean.createOcena(ocena);

        return Response.status(Response.Status.OK).entity(ocena).build();

    }

    /*
    @GET
    @Path("/byUsername/{username}")

    public Integer getUporabnikByUsername(@Parameter(description = "Get uporabnik by username.", required = true)
                                     @PathParam("username") String username) {

        List<Uporabnik> uporabnik = uporabnikBean.getUporabnikByUsername(username);

        if(uporabnik.size() == 0) {
            return -1;
        }

        Integer id = uporabnik.get(0).getId();

        return id;
    }
    */

    @POST
    @Path("break")
    public Response makeUnhealthy() {

        restProperties.setBroken(true);

        return Response.status(Response.Status.OK).build();
    }

    /*
    @Operation(description = "Get metadata for an id.", summary = "Get metadata for an id")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Shramba metadata",
                    content = @Content(
                            schema = @Schema(implementation = UporabnikoviIzdelkiMetadata.class))
            )})
    @GET
    @Path("/{id}")
    public Response getImageMetadata(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("id") Integer id) {

        UporabnikoviIzdelkiMetadata shrambaMetadata = uporabnikoviIzdelkiMetadataBean.getUporabnikoviIzdelkiMetadata(id);

        if (shrambaMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(shrambaMetadata).build();
    }

     */

    /*
    @Operation(description = "Add image metadata.", summary = "Add metadata")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Metadata successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error .")
    })
    @POST
    public Response createImageMetadata(@RequestBody(
            description = "DTO object with image metadata.",
            required = true, content = @Content(
            schema = @Schema(implementation = ImageMetadata.class))) ImageMetadata imageMetadata) {

        if ((imageMetadata.getTitle() == null || imageMetadata.getDescription() == null || imageMetadata.getUri() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            imageMetadata = uporabnikoviIzdelkiMetadata.createImageMetadata(imageMetadata);
        }

        return Response.status(Response.Status.CONFLICT).entity(imageMetadata).build();

    }


    @Operation(description = "Update metadata for an image.", summary = "Update metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully updated."
            )
    })
    @PUT
    @Path("{imageMetadataId}")
    public Response putImageMetadata(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("imageMetadataId") Integer imageMetadataId,
                                     @RequestBody(
                                             description = "DTO object with image metadata.",
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = ImageMetadata.class)))
                                             ImageMetadata imageMetadata){

        imageMetadata = uporabnikoviIzdelkiMetadata.putImageMetadata(imageMetadataId, imageMetadata);

        if (imageMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @Operation(description = "Delete metadata for an image.", summary = "Delete metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("{imageMetadataId}")
    public Response deleteImageMetadata(@Parameter(description = "Metadata ID.", required = true)
                                        @PathParam("imageMetadataId") Integer imageMetadataId){

        boolean deleted = uporabnikoviIzdelkiMetadata.deleteImageMetadata(imageMetadataId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response uploadImage(InputStream uploadedInputStream) {

        String imageId = UUID.randomUUID().toString();
        String imageLocation = UUID.randomUUID().toString();

        byte[] bytes = new byte[0];
        try (uploadedInputStream) {
            bytes = uploadedInputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UploadImageResponse uploadImageResponse = new UploadImageResponse();

        Integer numberOfFaces = amazonRekognitionClient.countFaces(bytes);
        uploadImageResponse.setNumberOfFaces(numberOfFaces);

        if (numberOfFaces != 1) {
            uploadImageResponse.setMessage("Image must contain one face.");
            return Response.status(Response.Status.BAD_REQUEST).entity(uploadImageResponse).build();

        }

        List<String> detectedCelebrities = amazonRekognitionClient.checkForCelebrities(bytes);

        if (!detectedCelebrities.isEmpty()) {
            uploadImageResponse.setMessage("Image must not contain celebrities. Detected celebrities: "
                    + detectedCelebrities.stream().collect(Collectors.joining(", ")));
            return Response.status(Response.Status.BAD_REQUEST).entity(uploadImageResponse).build();
        }

        uploadImageResponse.setMessage("Success.");

        // Upload image to storage

        // Generate event for image processing


        return Response.status(Response.Status.CREATED).entity(uploadImageResponse).build();
    }


     */


}
