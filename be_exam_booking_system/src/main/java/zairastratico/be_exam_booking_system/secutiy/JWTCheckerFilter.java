package zairastratico.be_exam_booking_system.secutiy;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import zairastratico.be_exam_booking_system.exceptions.UnauthorizedException;
import zairastratico.be_exam_booking_system.tools.JWTTools;

import java.io.IOException;
import java.util.Collections;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Invalid Token");
        }

        String accessToken = authHeader.substring(7);

        jwtTools.verifyToken(accessToken);

        String userId = jwtTools.extractId(accessToken);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userId,
                null,
                Collections.emptyList()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();

        return path.startsWith("/login") ||
                (path.equals("/exams") && "GET".equalsIgnoreCase(method)) ||
                (path.equals("/booking") && "POST".equalsIgnoreCase(method));
    }

}
