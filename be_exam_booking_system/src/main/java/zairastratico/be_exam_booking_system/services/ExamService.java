package zairastratico.be_exam_booking_system.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zairastratico.be_exam_booking_system.entities.Exam;
import zairastratico.be_exam_booking_system.entities.User;
import zairastratico.be_exam_booking_system.entities.enums.Status;
import zairastratico.be_exam_booking_system.entities.enums.TimeSlot;
import zairastratico.be_exam_booking_system.exceptions.BadRequestException;
import zairastratico.be_exam_booking_system.exceptions.NotFoundException;
import zairastratico.be_exam_booking_system.payloads.ExamRegistrationDTO;
import zairastratico.be_exam_booking_system.payloads.ExamResponseDTO;
import zairastratico.be_exam_booking_system.repositories.ExamRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private AuthorizationService authService;

    @Autowired
    private UserService userService;

    //find

    public Exam findExamById(Long id) {
        return examRepository.findById(id).orElseThrow(() -> new NotFoundException("Exam with id " + id + " not found"));
    }

    public Page<Exam> findAllExams(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return examRepository.findAll(pageable);
    }

    public  Page<Exam> findExamsByName(String name, int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        return examRepository.findByNameIgnoreCase(name, pageable);
    }

    public Page<Exam> findExamsByTimeSlot(TimeSlot timeSlot, int page, int size, String sortBy) {

        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        return examRepository.findByTimeSlot(timeSlot, pageable);
    }

    public Page<Exam> findExamsByStatus(Status status, int page, int size, String sortBy) {

        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        return examRepository.findByStatus(status, pageable);
    }

    public Page<Exam> findExamsByAdminId(Long adminId, int page, int size, String sortBy) {

        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        return examRepository.findByAdminId(adminId, pageable);
    }

    public Page<Exam> findExamsByDateRange(LocalDate startDate, LocalDate endDate, int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        return examRepository.findByDateBetween(startDate, endDate, pageable);
    }

    public Page<Exam> findExamsByStatusSortedByDate(Status status, int page, int size) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size);

        return examRepository.findByStatusOrderByDateAsc(status, pageable);
    }
    public List<Exam> getAvailableExamsForUsers() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate twoDaysFromNow = today.plusDays(2);
        LocalTime now = LocalTime.now();

        return examRepository.findAvailableExamsWithVisibilityFilter(
                Status.DISPONIBILE,
                twoDaysFromNow,
                tomorrow,
                today,
                now
        );
    }

    //CRUD
    public ExamResponseDTO createExam(ExamRegistrationDTO payload) {
        User admin = userService.findUserById(payload.adminId());

        Exam exam = new Exam(
                payload.name(),
                payload.date(),
                payload.timeSlot(),
                payload.time(),
                payload.maxNumb(),
                payload.forceVisibility(),
                admin
        );

        Exam savedExam = examRepository.save(exam);
        log.info("Exam created with id: {} (forceVisibility: {})",
                savedExam.getId(), savedExam.isForceVisibility());

        return new ExamResponseDTO(savedExam.getId());
    }

    public ExamResponseDTO updateExam(Long id, ExamRegistrationDTO payload) {
        Exam exam = findExamById(id);

        exam.setName(payload.name());
        exam.setDate(payload.date());
        exam.setTimeSlot(payload.timeSlot());
        exam.setTime(payload.time());
        exam.setMaxNumb(payload.maxNumb());

        int bookedCount = exam.getMaxNumb() - exam.getAvailableNumb();
        exam.setAvailableNumb(payload.maxNumb() - bookedCount);

        if (exam.getAvailableNumb() <= 0) {
            exam.setStatus(Status.COMPLETO);
        } else {
            exam.setStatus(Status.DISPONIBILE);
        }

        Exam updatedExam = examRepository.save(exam);
        log.info("Exam updated with id: {}", updatedExam.getId());

        return new ExamResponseDTO(updatedExam.getId());
    }

    public void deleteExam(Long id) {
        Exam exam = findExamById(id);

        if (exam.getAvailableNumb() < exam.getMaxNumb()) {
            throw new BadRequestException("Cannot delete exam with bookings");
        }

        examRepository.deleteById(id);
        log.info("Exam deleted with id: {}", id);
    }

    //posti disponibili

    public void decreaseAvailableSeats(Long examId) {
        Exam exam = findExamById(examId);

        if (exam.getAvailableNumb() <= 0) {
            throw new BadRequestException("Exam is full");
        }

        exam.setAvailableNumb(exam.getAvailableNumb() - 1);

        if (exam.getAvailableNumb() == 0) {
            exam.setStatus(Status.COMPLETO);
        }

        examRepository.save(exam);
        log.info("Decreased available seats for exam id: {}", examId);
    }

    public void increaseAvailableSeats(Long examId) {
        Exam exam = findExamById(examId);

        exam.setAvailableNumb(exam.getAvailableNumb() + 1);
        exam.setStatus(Status.DISPONIBILE);

        examRepository.save(exam);
        log.info("Increased available seats for exam id: {}", examId);
    }

    //force visibility
    public void toggleForceVisibility(Long examId) {
        Exam exam = findExamById(examId);
        exam.setForceVisibility(!exam.isForceVisibility());
        examRepository.save(exam);
        log.info("Toggled forceVisibility for exam {}: {}", examId, exam.isForceVisibility());
    }
}
