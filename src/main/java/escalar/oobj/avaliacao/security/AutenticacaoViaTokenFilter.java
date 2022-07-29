package escalar.oobj.avaliacao.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    public AutenticacaoViaTokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = recuperarToken(request);
        boolean valido = tokenService.isTokenValido(token);
        if(valido) {
            autenticarCliente();
        }
        filterChain.doFilter(request, response);
    }

    @Bean
    public FilterRegistrationBean<AutenticacaoViaTokenFilter> filter() {
        FilterRegistrationBean<AutenticacaoViaTokenFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new AutenticacaoViaTokenFilter(tokenService));
        return bean;
    }

    private void autenticarCliente() {
        Authentication authentication = new Authentication(AuthorityUtils.NO_AUTHORITIES);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7, token.length());
    }
}