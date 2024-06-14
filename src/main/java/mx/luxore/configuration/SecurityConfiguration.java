package mx.luxore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    /**
     * configuration one
     */
  /*  @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //.csrf().disable(); al recibir formularios debe estar activo
                .authorizeHttpRequests() //indica que url estan protegidas y cuales no, por defecto todas estan protegidas
                    .antMatchers("/api/catalogs/**")// indicas que end-points pueden acceder sin ningun tipó de autorizacion
                    .permitAll()// indica que permita el acceso a todos los que esten agregados en el requestMatchers
                    .anyRequest().authenticated() // indica que todos los demás tienen que estar atuenticados
                .and()//se usa para agregar mas config
                .formLogin().permitAll();//se le indica que el formulario login por default pueda ser visto por todos
        return http.build();
    }*/

    /**
     * configuration two
     * Al trabajar con sesiones podemos guardar informacion del usuario del lado del servidor
     * ALWAYS       Crea una sesión de usuario, siempre y cuando no exista ninguna, si ya existe una la reutiliza la sesión del usuario
     * IF_REQUIRED  Crea una nueva sesión si es necesario, si no existe la crea, o la reuza, valida siempre si existe una
     * NEVER        No crea nunguna sesión, si ya existe una la usa, si no existe tramita la solicitud sin crear una sesión
     * STATELESS    No trabaja con sesiones
     */
    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(a -> {
                    a.antMatchers("/api/catalogs/**").permitAll();
                    a.anyRequest().authenticated();
                })
                .formLogin()
                    .successHandler(authenticationSuccessHandler())// si la autenticacion es correcta, redirije a  un endpoint, se tiene que definir un handler
                    .permitAll()
                .and()
                .sessionManagement()// indica el comportamiento de la sesión en spring
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)//ALWAYS, IF_REQUIRED, NEVER, STATELESS:
                    .invalidSessionUrl("/login")//si la sesion es invalida, o se creo mal redirige a una url
                    .maximumSessions(1)// numero maximo de sesiones por usuario, ideal es 1, > 1 es para apps multiplataforma que tengan muchos flujos de ejecucion
                    .expiredUrl("/login")// url de redireccion para cuanddo haya tiempo de inactividad
                    .sessionRegistry(sessionRegistry())//metodo que nos permite administrar la informacion de la sesion
                .and()
                .sessionFixation()// vulnerabilidad al trabajar con sesiones una sesion genera un id de sesion, un atacate puede obtener el id de sesion y atacar la aplicacion,
                    .migrateSession()// metod de sessionFixation, que detecta cuando se trata de hacer un ataque de fijacion de sesion, spring fenera otro id de sesion, los datos guardados en sesion se copian a la nueva sesion
                    //.newSession()// crea una nueva sesion cuando detectan un ataque por fijacion, pero se pierde la informacion gguardada en la sesion
                    //.none()// no recomendada, inabilita la seguridad en contra de un ataque de fijacion de sesion
                .and()
                .build();
    }*/

    /**
     * Basic autentication
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http

                .authorizeHttpRequests()
                .antMatchers("/api/catalogs/**")
                    .permitAll()
                    .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .httpBasic()//da soporte al protocolo de autenticacion basica y acceder a las apis protegidas, el usuario y contraseña se envian  en el header de la aplicacion, además del formulario
                .and()
                .build();
    }

    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("/api/test");
        };
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}

