package zairastratico.be_exam_booking_system.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import zairastratico.be_exam_booking_system.entities.enums.DocumentType;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email", "exam_id"}))
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"exam"})
@JsonIgnoreProperties({"exam"})
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType documentType;

    @Column(nullable = false)
    private String documentNumber;

    @Column(nullable = false)
    private String securityCode;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    public Booking(String name, String surname, String email, String phone, DocumentType documentType, String documentNumber, String securityCode, Exam exam) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.securityCode = securityCode;
        this.exam = exam;
    }
}