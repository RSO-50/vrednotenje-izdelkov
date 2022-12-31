
package si.rsvo.ocene.api.v1;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Vrednotenje izdelkov API", version = "v1",
        contact = @Contact(email = "rso.skupina50@gmail.com"),
        license = @License(name = "dev"), description = "API for managing user's favourites."),
        servers = @Server(url = "http://localhost:8080/"))
@ApplicationPath("/v1")
public class OceneApplication extends Application {

}
