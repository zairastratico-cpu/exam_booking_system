package zairastratico.be_exam_booking_system.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zairastratico.be_exam_booking_system.entities.Booking;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findAll(Pageable pageable);

    Page<Booking> findByEmail(String email, Pageable pageable);
    
    Page<Booking> findByExamId(Long examId, Pageable pageable);

    Page<Booking> findByExamIdOrderByCreatedAtDesc(Long examId, Pageable pageable);

    boolean existsByEmailAndExamId(String email, Long examId);

    long countByExamId(Long examId);

}
