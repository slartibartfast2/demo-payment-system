package ea.slartibartfast.cardservice.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationFilter extends BasicAuthenticationFilter {

    private final ApiKeyAuthenticationManager authenticationManager;

    public AuthenticationFilter(final ApiKeyAuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String apiKey = request.getHeader("api-key");

        if (apiKey == null) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(apiKey, apiKey, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(authentication));
        chain.doFilter(request, response);
    }

}
