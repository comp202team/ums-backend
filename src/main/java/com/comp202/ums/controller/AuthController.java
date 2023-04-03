package com.comp202.ums.controller;
import com.comp202.ums.Dto.JwtResponse;
import com.comp202.ums.Dto.user.UserDto;
import com.comp202.ums.Dto.user.UserLoginRequestDto;
import com.comp202.ums.Dto.user.UserRegisterRequestDto;
import com.comp202.ums.security.JwtService;
import com.comp202.ums.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final UserService userService;


    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto userLoginRequestDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequestDto.getUsername(), userLoginRequestDto.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginRequestDto.getUsername());
        final String jwtToken = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(jwtToken, userService.getByUsername(userLoginRequestDto.getUsername())));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(
            @Valid @RequestBody UserRegisterRequestDto userRegisterRequestDto){
        UserDto userDto = userService.register(userRegisterRequestDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping("/getcurrentuser")
    public ResponseEntity<UserDto> getCurrentUser(){
        return ResponseEntity.ok(userService.getCurrentUserDto());
    }
}