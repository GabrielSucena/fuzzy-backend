package com.fuzzy.courses.controller;

import com.fuzzy.courses.controller.dto.LoginRequest;
import com.fuzzy.courses.controller.dto.LoginResponse;
import com.fuzzy.courses.domain.role.Role;
import com.fuzzy.courses.repository.CollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        var collaborator = collaboratorRepository.findByRegister(loginRequest.register());

        if (collaborator.isEmpty() || !collaborator.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("Register or password is invalid!");
        }

        var now = Instant.now();
        var expiresIn = 28800L;

        var scopes = collaborator.get().getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("fuzzy")
                .subject(collaborator.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        var roles = collaborator.get().getRoles().stream().map(Role::getName).toList();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn, roles.toString()));
    }

}
