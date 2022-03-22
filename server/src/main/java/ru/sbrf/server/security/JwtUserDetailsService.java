package ru.sbrf.server.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sbrf.server.model.ATM;
import ru.sbrf.server.service.AuthenticationService;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final AuthenticationService authenticationService;

    public JwtUserDetailsService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ATM atm = authenticationService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return JwtUserDetails.fromUserToJwtUserDetails(atm);
    }
}
