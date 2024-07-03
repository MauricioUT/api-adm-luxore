package mx.luxore.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.luxore.entity.TUsers;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private JWTUtils jwtUtils;

    public JWTAuthenticationFilter(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * se ejecuta cuando el usuario se intenta autenticar
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        TUsers user;
        String usrname;
        String password;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), TUsers.class);
            usrname = user.getUsername();
            password = user.getPassword();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usrname, password);
        return getAuthenticationManager().authenticate(auth);
    }

    /**
     * genera el token cuando la autenticacion es correcta
     *
     * @param authResult objeto que contiene todos los detalles del usuario
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String token = jwtUtils.generateAccessToken(user.getUsername());
        response.addHeader("Authorization", token);
        Map<String, Object> httResponse = new HashMap<>();
        httResponse.put("token", token);
        httResponse.put("user", user.getUsername());
        httResponse.put("Message", "Autenticación correcta");
        response.getWriter().write(new ObjectMapper().writeValueAsString(httResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
