package com.EventPlanner.controller;

import com.EventPlanner.config.security.JwtUtil;
import com.EventPlanner.dto.UserDto;
import com.EventPlanner.dto.AuthenticationResponse;
import com.EventPlanner.dto.LoginCredentials;
import com.EventPlanner.service.UserService;
import com.EventPlanner.service.impl.MyUserDetailServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final MyUserDetailServiceImplementation myUserDetailService;
    private final UserService userService;

    public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, MyUserDetailServiceImplementation myUserDetailService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.myUserDetailService = myUserDetailService;
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody LoginCredentials loginCredentials) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginCredentials.getName(), loginCredentials.getPassword())
            );
        } catch (Exception e) {
            throw new BadCredentialsException("Incorrect Username or Password! ", e);
        }

        UserDetails userDetails = myUserDetailService.loadUserByUsername(loginCredentials.getName());
        String jwtToken = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDto userdto) {
        userService.registerUser(userdto);
        return ResponseEntity.ok("User registered successfully.");
    }
}
