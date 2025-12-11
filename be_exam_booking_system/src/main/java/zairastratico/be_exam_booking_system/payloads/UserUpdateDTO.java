package zairastratico.be_exam_booking_system.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserUpdateDTO(

        @NotEmpty(message = "Name is required")
        String name,

        @Email(message = "Insert a valid Email")
        @NotEmpty(message = "Email is required")
        String email
) {
}
