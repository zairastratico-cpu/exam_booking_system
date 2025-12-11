package zairastratico.be_exam_booking_system.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zairastratico.be_exam_booking_system.entities.Exam;
import zairastratico.be_exam_booking_system.entities.enums.Status;
import zairastratico.be_exam_booking_system.entities.enums.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    Page<Exam> findAll(Pageable pageable);

    Page<Exam> findByNameIgnoreCase(String name, Pageable pageable);

    Page<Exam> findByTimeSlot(TimeSlot timeSlot, Pageable pageable);

    Page<Exam> findByStatus(Status status, Pageable pageable);

    Page<Exam> findByAdminId(Long adminId, Pageable pageable);

    Page<Exam> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Exam> findByStatusOrderByDateAsc(Status status, Pageable pageable);

    List<Exam> findByStatus(Status status);

    @Query("SELECT e FROM Exam e WHERE e.status = :status AND " +
            "(e.forceVisibility = true OR " +
            "(e.date > :twoDaysFromNow) OR " +
            "(e.date = :tomorrow AND e.time > :currentTime) OR " +
            "(e.date = :today AND e.time > :currentTime))")
    List<Exam> findAvailableExamsWithVisibilityFilter(
            @Param("status") Status status,
            @Param("twoDaysFromNow") LocalDate twoDaysFromNow,
            @Param("tomorrow") LocalDate tomorrow,
            @Param("today") LocalDate today,
            @Param("currentTime") LocalTime currentTime
    );
}
