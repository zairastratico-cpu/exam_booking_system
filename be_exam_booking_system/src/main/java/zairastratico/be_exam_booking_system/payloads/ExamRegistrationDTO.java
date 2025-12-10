package zairastratico.be_exam_booking_system.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ExamRegistrationDTO(
        @NotBlank(message = "Exam name is required")
        String name,

        String description,

        @NotNull(message = "Date is required")
        LocalDate date,

        @NotNull(message = "Time is required")
        LocalTime time,

        @NotNull(message = "Max number is required")
        @Min(value = 1, message = "Max number must be at least 1")
        Integer maxNumb,

        @NotNull(message = "Admin ID is required")
        Long adminId
) {
}
