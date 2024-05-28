package mx.luxore.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.util.List;

@Configuration
@Setter
@Getter
public class OpenAPIConfiguration {

    @Autowired
    private Environment environment;
    private Server server;
    @Autowired
    private YAMLConfig yamlConfig;

    @Bean
    public OpenAPI defineOpenApi() {
        this.server = new Server();
        server.setUrl("http://" + InetAddress.getLoopbackAddress().getHostAddress() + ":" + environment.getProperty("server.port"));
        server.setDescription(yamlConfig.getEnvironment());

        Contact myContact = new Contact();
        myContact.setName("Mauricio Utrilla");
        myContact.setEmail("mauriciodanuelud@gmail.com");

        Info information = new Info()
                .title("Luxore Management System API")
                .version("1.0")
                .description("This API exposes endpoints to manage luxore.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
