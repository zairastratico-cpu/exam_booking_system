package zairastratico.be_exam_booking_system.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zairastratico.be_exam_booking_system.entities.Exam;
import zairastratico.be_exam_booking_system.entities.enums.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    Optional<Exam> findByNameIgnoreCase(String name);

    Page<Exam> findAll(Pageable pageable);

    Page<Exam> findByStatus(Status status, Pageable pageable);

    Page<Exam> findByAdminId(Long adminId, Pageable pageable);

    Page<Exam> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Exam> findByStatusOrderByDateAsc(Status status, Pageable pageable);

    List<Exam> findByStatus(Status status);
}
