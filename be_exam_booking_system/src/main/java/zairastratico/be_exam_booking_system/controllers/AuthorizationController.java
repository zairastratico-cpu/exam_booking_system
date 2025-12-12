package zairastratico.be_exam_booking_system.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zairastratico.be_exam_booking_system.payloads.LoginDTO;
import zairastratico.be_exam_booking_system.payloads.LoginResponseDTO;
import zairastratico.be_exam_booking_system.services.AuthorizationService;
import zairastratico.be_exam_booking_system.services.UserService;

@RestController
public class AuthorizationController {
    @Autowired
    public UserService userService;

    @Autowired
    public AuthorizationService authorizationsService;
    
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Valid LoginDTO payload) {
        String extractedToken = authorizationsService.checkEmailBeforeLogin(payload);
        return new LoginResponseDTO(extractedToken);
    }
}
