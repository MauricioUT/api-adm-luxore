package mx.luxore.security.configuration;

import mx.luxore.security.jwt.JWTAuthenticationFilter;
import mx.luxore.security.jwt.JWTAuthorizationFilter;
import mx.luxore.security.jwt.JWTUtils;
import mx.luxore.security.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)// habilita las anotaciones de spring security en los controladores
public class SecurityConfiguration {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private JWTAuthorizationFilter authorizationFilter;

    /**
     * objeto que controla el acceso a los endpoints
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");// si puede cambiar por lo que uno quiera
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            new AntPathRequestMatcher("/swagger-ui/**"),
                            new AntPathRequestMatcher("/v3/api-docs/**")).permitAll();
                    //auth.requestMatchers(new AntPathRequestMatcher("/api/catalogs/**")).hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(jwtAuthenticationFilter) // filtro para autenticar el usuario y password
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class) // filtro para obtenrr el token
                .build();

    }

    /**
     * create user in memory
     */
   /* @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("Mauricio")
                .password("1234")
                .roles()
                .build());
        return manager;
    }*/


    /**
     * objeto que se encarga de la administracion de lo autenticacion de los usuarios
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    /**
     * objeto que nos ayuda a encriptar las contraseñas
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

