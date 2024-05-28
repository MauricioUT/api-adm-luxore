package mx.luxore;

import io.swagger.v3.oas.models.OpenAPI;
import lombok.extern.slf4j.Slf4j;
import mx.luxore.configuration.OpenAPIConfiguration;
import mx.luxore.configuration.YAMLConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.net.InetAddress;


@SpringBootApplication
@Slf4j
public class AdmLuxoreApplication implements CommandLineRunner {

    @Autowired
    private YAMLConfig myConfig;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private OpenAPIConfiguration openAPI;


    public static void main(String[] args) {
        SpringApplication.run(AdmLuxoreApplication.class, args);
    }

    public void run(String... args) throws Exception {
        log.info("using environment: " + myConfig.getEnvironment());
        log.info("name: " + myConfig.getName());
        log.info("enabled:" + myConfig.isEnabled());
        log.info("servers: " + myConfig.getServers());
        log.info("Db coneection: " + dataSource.getConnection().getMetaData().getURL());
        log.info("swagger " +  openAPI.getServer().getUrl()+"/swagger-ui/index.html");
    }

    
}
