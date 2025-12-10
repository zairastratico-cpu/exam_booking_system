package zairastratico.be_exam_booking_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zairastratico.be_exam_booking_system.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmail(String email);
}
