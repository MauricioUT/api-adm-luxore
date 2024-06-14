package mx.luxore.controller;

import com.mysql.cj.Session;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class PruebaController {


    @Autowired
    SessionRegistry sessionRegistry;

    @Operation(summary = "test spring scurity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Server Error")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getColonyPageable() {
        String sessionId = "";
        User user = null;
        List<Object> sessions = sessionRegistry.getAllPrincipals(); // devuelve las sesiones de todas la personas logeadas
        for (Object session : sessions) {
            if (session instanceof User) {
                user = (User) session;
            }
            List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(session, false);
            for (SessionInformation sessionInformation : sessionInformations) {
                sessionId = sessionInformation.getSessionId();
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", sessionId);
        response.put("user", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
