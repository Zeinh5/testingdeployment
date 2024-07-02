package mobi.foo.LoginAPI2.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mobi.foo.LoginAPI2.config.AuthenticationService;
import mobi.foo.LoginAPI2.dto.request.AuthenticationRequest;
import mobi.foo.LoginAPI2.dto.request.RegisterRequest;
import mobi.foo.LoginAPI2.dto.response.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request){

        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request){

        return ResponseEntity.ok(service.authenticate(request));
    }
}
