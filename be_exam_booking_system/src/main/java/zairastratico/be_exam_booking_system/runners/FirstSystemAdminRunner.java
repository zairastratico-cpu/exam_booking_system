package zairastratico.be_exam_booking_system.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import zairastratico.be_exam_booking_system.payloads.UserRegistrationDTO;
import zairastratico.be_exam_booking_system.services.UserService;

@Component
public class FirstSystemAdminRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {

        UserRegistrationDTO payload = new UserRegistrationDTO(
                "scuolamoscati",
                "mrkt@scuolamoscati.it",
                "Scuola.Mo25!"
        );

        boolean exists = userService.userExistsByEmail(payload.email());

        if (!exists) {
            userService.createUser(payload);
            System.out.println("The Admin Boss has been successfully created");
        } else {
            System.out.println("The AdminBoss already exists in our system");
        }
    }
}

