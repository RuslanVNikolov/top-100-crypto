package io.ruslan.top100crypto.service;

import io.ruslan.top100crypto.exceptions.UserAlreadyExistsException;
import io.ruslan.top100crypto.model.document.Role;
import io.ruslan.top100crypto.model.document.User;
import io.ruslan.top100crypto.model.dto.request.AuthenticationRequest;
import io.ruslan.top100crypto.model.dto.request.RegisterRequest;
import io.ruslan.top100crypto.model.dto.response.AuthenticationResponse;
import io.ruslan.top100crypto.repository.UserRepository;
import io.ruslan.top100crypto.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                        request.getPassword()));
        User user = (User) authentication.getPrincipal();
        String jwtToken = jwtUtil.generateToken(user);

        return new AuthenticationResponse(jwtToken, "Successful login!");
    }

    public AuthenticationResponse register(RegisterRequest request) {
        userRepository.findByUsername(request.getUsername())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("User already exists!");
                });

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(String.valueOf(Role.USER))
                .build();

        return new AuthenticationResponse(jwtUtil.generateToken(userRepository.save(user)), "Successful registration!");
    }
}
