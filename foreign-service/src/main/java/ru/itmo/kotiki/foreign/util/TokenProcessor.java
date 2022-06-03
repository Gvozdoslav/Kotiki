package ru.itmo.kotiki.foreign.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import ru.itmo.kotiki.data.enums.AppUserRole;
import ru.itmo.kotiki.data.model.AppUser;
import ru.itmo.kotiki.data.model.Role;
import ru.itmo.kotiki.foreign.service.AppUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class TokenProcessor {

    public static void refreshTokens(HttpServletRequest request, HttpServletResponse response, AppUserService service) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String refresh_token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refresh_token);
        String username = decodedJWT.getSubject();
        AppUser user = service.findUserByUsername(username).getContent();

        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + getMinutesInMillis(15)))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getRoles().stream().map(Role::getRoleName).map(AppUserRole::toString).toList())
                .sign(algorithm);


        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    public static void unsuccessfulProcessingTokens(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {

        response.setHeader("error", exception.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, String> error = new HashMap<>();
        error.put("error_message", exception.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }

    public static long getMinutesInMillis(long minutes) {
        return minutes * 60 * 1000;
    }
}
