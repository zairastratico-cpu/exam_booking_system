package zairastratico.be_exam_booking_system.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import zairastratico.be_exam_booking_system.entities.Exam;
import zairastratico.be_exam_booking_system.entities.User;
import zairastratico.be_exam_booking_system.entities.enums.Status;
import zairastratico.be_exam_booking_system.entities.enums.TimeSlot;
import zairastratico.be_exam_booking_system.payloads.ExamRegistrationDTO;
import zairastratico.be_exam_booking_system.payloads.ExamResponseDTO;
import zairastratico.be_exam_booking_system.services.ExamService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {
    @Autowired
    private ExamService examService;

    @GetMapping("/available")
    public List<Exam> getAvailableExams() {
        return examService.getAvailableExamsForUsers();
    }

    @GetMapping
    public Page<Exam> getAllExams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy
    ) {
        return examService.findAllExams(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Exam getExamById(@PathVariable Long id) {
        return examService.findExamById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ExamResponseDTO createExam(@RequestBody @Valid ExamRegistrationDTO payload) {
        return examService.createExam(payload);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ExamResponseDTO updateExam(
            @PathVariable Long id,
            @RequestBody @Valid ExamRegistrationDTO payload
    ) {
        return examService.updateExam(id, payload);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
    }

    @GetMapping("/search/name")
    public Page<Exam> getExamsByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy
    ) {
        return examService.findExamsByName(name, page, size, sortBy);
    }

    @GetMapping("/search/timeslot")
    public Page<Exam> getExamsByTimeSlot(
            @RequestParam TimeSlot timeSlot,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy
    ) {
        return examService.findExamsByTimeSlot(timeSlot, page, size, sortBy);
    }

    @GetMapping("/search/status")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Exam> getExamsByStatus(
            @RequestParam Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy
    ) {
        return examService.findExamsByStatus(status, page, size, sortBy);
    }

    @GetMapping("/search/admin/{adminId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Exam> getExamsByAdmin(
            @PathVariable Long adminId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy
    ) {
        return examService.findExamsByAdminId(adminId, page, size, sortBy);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Exam> getMyExams(
            @AuthenticationPrincipal User authorizedUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy
    ) {
        Long adminId = authorizedUser.getId();

        return examService.findExamsByAdminId(adminId, page, size, sortBy);
    }

    @GetMapping("/search/daterange")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Exam> getExamsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy
    ) {
        return examService.findExamsByDateRange(startDate, endDate, page, size, sortBy);
    }

    @GetMapping("/available/sorted")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Exam> getAvailableExamsSorted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return examService.findExamsByStatusSortedByDate(Status.DISPONIBILE, page, size);
    }
}
