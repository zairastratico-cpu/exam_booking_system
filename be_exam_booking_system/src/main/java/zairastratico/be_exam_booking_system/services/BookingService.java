package zairastratico.be_exam_booking_system.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zairastratico.be_exam_booking_system.entities.Booking;
import zairastratico.be_exam_booking_system.entities.Exam;
import zairastratico.be_exam_booking_system.exceptions.BadRequestException;
import zairastratico.be_exam_booking_system.exceptions.NotFoundException;
import zairastratico.be_exam_booking_system.payloads.BookingRegistrationDTO;
import zairastratico.be_exam_booking_system.payloads.BookingResponseDTO;
import zairastratico.be_exam_booking_system.repositories.BookingRepository;

@Service
@Slf4j
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ExamService examService;

    public Booking findBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking with id " + id + " not found"));
    }

    public Page<Booking> findAllBookings(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return bookingRepository.findAll(pageable);
    }

    public Page<Booking> findBookingsByEmail(String email, int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return bookingRepository.findByEmail(email, pageable);
    }

    public Page<Booking> findBookingsByExamId(Long examId, int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return bookingRepository.findByExamId(examId, pageable);
    }

    public Page<Booking> findBookingsByExamIdSortedByCreatedAt(Long examId, int page, int size) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size);
        return bookingRepository.findByExamIdOrderByCreatedAtDesc(examId, pageable);
    }

    public long countBookingsByExam(Long examId) {
        return bookingRepository.countByExamId(examId);
    }

    //CRUD
    public BookingResponseDTO createBooking(BookingRegistrationDTO payload) {
        Exam exam = examService.findExamById(payload.examId());

        if (bookingRepository.existsByEmailAndExamId(payload.email(), payload.examId())) {
            throw new BadRequestException("You are already booked for this exam");
        }

        if (exam.getAvailableNumb() <= 0) {
            throw new BadRequestException("Exam is full");
        }

        Booking booking = new Booking(
                payload.name(),
                payload.surname(),
                payload.email(),
                payload.phone(),
                payload.documentType(),
                payload.documentNumber(),
                payload.securityCode(),
                exam
        );

        Booking savedBooking = bookingRepository.save(booking);
        log.info("Booking created with id: {} for exam: {} by user: {}",
                savedBooking.getId(), exam.getId(), payload.email());

        examService.decreaseAvailableSeats(payload.examId());

        return new BookingResponseDTO(savedBooking.getId());
    }

    public void deleteBooking(Long id) {
        Booking booking = findBookingById(id);
        examService.increaseAvailableSeats(booking.getExam().getId());
        bookingRepository.deleteById(id);
        log.info("Booking {} deleted for exam {}", id, booking.getExam().getId());
    }

    public boolean isAlreadyBooked(String email, Long examId) {
        return bookingRepository.existsByEmailAndExamId(email, examId);
    }
}
