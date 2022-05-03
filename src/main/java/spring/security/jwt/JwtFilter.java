package spring.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
    private static final String BACKEND_HOST = "http://localhost:8080";
    private static final String OUR_DOMAIN = "http://f0643001.xsph.ru";
    private static final String FRONTEND_HOST = "http://localhost:63342";

    private void setAccessControlAllowOrigin(HttpServletResponse response,
                                             HttpServletRequest request) {
        if (BACKEND_HOST.equals(request.getHeader("Origin"))) {
            response.setHeader("Access-Control-Allow-Origin", BACKEND_HOST);
        } else if (OUR_DOMAIN.equals(request.getHeader("Origin"))) {
            response.setHeader("Access-Control-Allow-Origin", OUR_DOMAIN);
        } else if (FRONTEND_HOST.equals(request.getHeader("Origin"))) {
            response.setHeader("Access-Control-Allow-Origin", FRONTEND_HOST);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            ((HttpServletResponse) servletResponse).setHeader("Access-Control-Max-Age", "3600");
            ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, "
                    + "Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
            ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Credentials", "true");
            setAccessControlAllowOrigin((HttpServletResponse) servletResponse, (HttpServletRequest) servletRequest);
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);

            if (((HttpServletRequest) servletRequest).getMethod().equals("OPTIONS")) {
                ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_OK);
                return;
            }
            if (token != null && jwtTokenProvider.validateToken(token)) {
                System.out.println("token is valid and not null (from Filter)");
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ExpiredJwtException eje) {
            LOGGER.info("Security exception for user {} - {}", eje.getClaims().getSubject(), eje.getMessage());
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            LOGGER.debug("Exception " + eje.getMessage(), eje);
        }
    }
}
