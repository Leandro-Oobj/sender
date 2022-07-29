package escalar.oobj.avaliacao.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class DummyAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication == null) {
            throw new IllegalArgumentException("Token de autenticação não pode ser nulo!");
        }
        return authentication;
    }
}
