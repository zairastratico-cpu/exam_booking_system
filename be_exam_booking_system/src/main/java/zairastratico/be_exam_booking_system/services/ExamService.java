package zairastratico.be_exam_booking_system.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zairastratico.be_exam_booking_system.entities.Exam;
import zairastratico.be_exam_booking_system.entities.enums.Status;
import zairastratico.be_exam_booking_system.entities.enums.TimeSlot;
import zairastratico.be_exam_booking_system.exceptions.NotFoundException;
import zairastratico.be_exam_booking_system.repositories.ExamRepository;

@Slf4j
@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    public Exam findExamById(Long id) {
        return examRepository.findById(id).orElseThrow(() -> new NotFoundException("Exam with id " + id + " not found"));
    }

    public Page<Exam> findAllExams(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return examRepository.findAll(pageable);
    }

    public  Page<Exam> findByExamsName(int page, int size, String sortBy) {
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



}
