package zairastratico.be_exam_booking_system.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import zairastratico.be_exam_booking_system.entities.enums.Status;
import zairastratico.be_exam_booking_system.entities.enums.TimeSlot;

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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimeSlot timeSlot;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    private Integer maxNumb;

    @Column(nullable = false)
    private Integer availableNumb;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status= Status.DISPONIBILE;

    @Column(nullable = false)
    private boolean forceVisibility = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User admin;

    public Exam(String name, LocalDate date, TimeSlot timeSlot, LocalTime time, Integer maxNumb, boolean forceVisibility, User admin) {
        this.name = name;
        this.date = date;
        this.timeSlot = timeSlot;
        this.time = time;
        this.maxNumb = maxNumb;
        this.availableNumb = maxNumb;
        this.status = Status.DISPONIBILE;
        this.forceVisibility = forceVisibility;
        this.admin = admin;
    }
}