package com.pavmaxdav.mymess.controller;

import com.pavmaxdav.mymess.dto.ErrorDTO;
import com.pavmaxdav.mymess.dto.JwtRequest;
import com.pavmaxdav.mymess.dto.JwtResponse;
import com.pavmaxdav.mymess.dto.RegisterDTO;
import com.pavmaxdav.mymess.entity.User;
import com.pavmaxdav.mymess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.pavmaxdav.mymess.utils.JwtUtils;

@Controller
class LoginController {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(JwtUtils jwtUtils, AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

//    @GetMapping("/login")
//    String login() {
//        return "login";
//    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getLogin(), authRequest.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ErrorDTO(
                    HttpStatus.UNAUTHORIZED.value(),"Failed to authenticate"),
                    HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getLogin());
        String token = jwtUtils.generateJWT(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        if (userService.findUserByLogin(registerDTO.getLogin()).isPresent()) {
            return new ResponseEntity<>(new ErrorDTO(
                    HttpStatus.BAD_REQUEST.value(),"Login is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(
                registerDTO.getLogin(),
                passwordEncoder.encode(registerDTO.getPassword())
        );
        userService.addUser(user);

        return new ResponseEntity<>("User registration succesfull!", HttpStatus.OK);
    }
}