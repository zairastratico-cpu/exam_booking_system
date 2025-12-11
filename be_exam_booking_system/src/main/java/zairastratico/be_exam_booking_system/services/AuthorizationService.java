package zairastratico.be_exam_booking_system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zairastratico.be_exam_booking_system.entities.User;
import zairastratico.be_exam_booking_system.exceptions.UnauthorizedException;
import zairastratico.be_exam_booking_system.payloads.LoginDTO;
import zairastratico.be_exam_booking_system.tools.JWTTools;

@Service
public class AuthorizationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bCrypt;

    public String checkEmailBeforeLogin(LoginDTO payload) {
        User found = userService.findUserByEmail(payload.email());
        if (bCrypt.matches(payload.password(), found.getPassword())) {
            String extractedToken = jwtTools.createToken(found);
            return extractedToken;
        } else {
            throw new UnauthorizedException("Unauthorized - try again");
        }
    }
}
