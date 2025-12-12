package zairastratico.be_exam_booking_system.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zairastratico.be_exam_booking_system.entities.Booking;
import zairastratico.be_exam_booking_system.payloads.BookingRegistrationDTO;
import zairastratico.be_exam_booking_system.payloads.BookingResponseDTO;
import zairastratico.be_exam_booking_system.services.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/exam/{examId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponseDTO createBooking(@PathVariable Long examId, @RequestBody @Valid BookingRegistrationDTO payload) {
        return bookingService.createBooking(examId, payload);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Page<Booking> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        return bookingService.findAllBookings(page, size, sortBy);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Booking getBookingById(@PathVariable Long id) {
        return bookingService.findBookingById(id);
    }

    @GetMapping("/exam/{examId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Page<Booking> getBookingsByExam(
            @PathVariable Long examId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        return bookingService.findBookingsByExamId(examId, page, size, sortBy);
    }

    @GetMapping("/exam/{examId}/sorted")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Page<Booking> getBookingsByExamSorted(
            @PathVariable Long examId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return bookingService.findBookingsByExamIdSortedByCreatedAt(examId, page, size);
    }

    @GetMapping("/search/email")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Page<Booking> getBookingsByEmail(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        return bookingService.findBookingsByEmail(email, page, size, sortBy);
    }

    @GetMapping("/exam/{examId}/count")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public long countBookingsByExam(@PathVariable Long examId) {
        return bookingService.countBookingsByExam(examId);
    }

    @GetMapping("/check")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public boolean isAlreadyBooked(
            @RequestParam String email,
            @RequestParam Long examId
    ) {
        return bookingService.isAlreadyBooked(email, examId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }
}
