package zairastratico.be_exam_booking_system.security;

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
import zairastratico.be_exam_booking_system.entities.User;
import zairastratico.be_exam_booking_system.exceptions.UnauthorizedException;
import zairastratico.be_exam_booking_system.services.UserService;
import zairastratico.be_exam_booking_system.tools.JWTTools;

import java.io.IOException;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Invalid Token");
        }

        String accessToken = authHeader.substring(7);

        try {
            jwtTools.verifyToken(accessToken);

            String userIdStr = jwtTools.extractId(accessToken);
            Long userId = Long.parseLong(userIdStr);

            User authorizedUser = userService.findUserById(userId);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authorizedUser,
                    null,
                    authorizedUser.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            throw new UnauthorizedException("Invalid Token: " + ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();

        return path.startsWith("/login") ||
                (path.startsWith("/exams/") && "GET".equalsIgnoreCase(method)) ||
                (path.equals("/bookings") && "POST".equalsIgnoreCase(method));
    }

}
