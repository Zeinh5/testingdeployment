package mobi.foo.LoginAPI2.config;

import lombok.RequiredArgsConstructor;
import mobi.foo.LoginAPI2.dto.request.AuthenticationRequest;
import mobi.foo.LoginAPI2.dto.request.RegisterRequest;
import mobi.foo.LoginAPI2.dto.response.AuthenticationResponse;
import mobi.foo.LoginAPI2.repository.UserRepository;
import mobi.foo.LoginAPI2.user.Role;
import mobi.foo.LoginAPI2.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){

        if(repository.findByEmail(request.getEmail()).isPresent()) {
            return AuthenticationResponse.builder().token(null).message("Email already registered").build();
        }
        var user= User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).message("Email registered success.").build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){


        var user = repository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return AuthenticationResponse.builder()
                    .token(null)
                    .message("User not found")
                    .build();
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .message("User found")
                .build();
    }


}
