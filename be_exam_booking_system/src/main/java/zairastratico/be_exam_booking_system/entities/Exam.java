package zairastratico.be_exam_booking_system.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import zairastratico.be_exam_booking_system.entities.enums.Status;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "exams")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"admin"})
@JsonIgnoreProperties({"admin"})
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "Exam name is required")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Date is required")
    @Column(nullable = false)
    private LocalDate date;

    @NotNull(message = "Time is required")
    @Column(nullable = false)
    private LocalTime time;

    @NotNull(message = "Max number is required")
    @Min(value = 1, message = "Max number must be at least 1")
    @Column(nullable = false)
    private Integer maxNumb;

    @NotNull(message = "Available numb is required")
    @Min(value = 0, message = "Available numb cannot be negative")
    @Column(nullable = false)
    private Integer availableNumb;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status= Status.DISPONIBILE;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User admin;

    public Exam(String name, LocalDate date, LocalTime time, Integer maxNumb, User admin) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.maxNumb = maxNumb;
        this.availableNumb = maxNumb;
        this.admin = admin;
    }
}