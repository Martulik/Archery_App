package spring.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.entity.Rank;
import spring.entity.SeasonTicket;
import spring.entity.Student;
import spring.exception.SeasonTicketNotFoundException;
import spring.repositories.SeasonTicketRepository;
import spring.repositories.StudentRepository;
import spring.service.PurchaseHistoryService;
import spring.service.RankService;
import spring.service.StudentService;

import javax.print.DocFlavor;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/archery/admin/students")
@RequiredArgsConstructor
public class StudentsTableController
{
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final SeasonTicketRepository ticketRepository;
    private final PurchaseHistoryService purchaseHistoryService;
    private final RankService rankService;

    @GetMapping("")
    public ResponseEntity<List<Student>> showStudents()
    {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> showStudent(@PathVariable Long id)
    {
        return new ResponseEntity<>(studentService.findStudentById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/ticket")
    public ResponseEntity<String> showTicket(@PathVariable Long id)
    {
        String ticket = "";
        try
        {
            ticket = purchaseHistoryService.findActiveSeasonTicket(id, LocalDate.now()).getTicketType();
        }
        catch (SeasonTicketNotFoundException ignored) {}
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<String>> findTickets()
    {
        return new ResponseEntity<>(ticketRepository.findAll().stream().map(SeasonTicket::getTicketType).toList(), HttpStatus.OK);
    }

    @PostMapping("/{id}/changeticket/{ticket}")
    public void editTicket(@PathVariable Long id, @PathVariable String ticket)
    {
        Student student = studentService.findStudentById(id);
        SeasonTicket seasonTicket = ticketRepository.findByTicketType(ticket).get();
        purchaseHistoryService.addPurchase(student, LocalDate.now(), seasonTicket);
    }

    @PostMapping("/{id}/changeclasses/{increase}")
    public void editClasses(@PathVariable Long id, @PathVariable Boolean increase) {

        studentService.changeAttendedClasses(id, increase);
    }

    @GetMapping("/{id}/ranks")
    public ResponseEntity<List<String>> findRanks()
    {
        return new ResponseEntity<>(rankService.findAll().stream().map(Rank::getRank_name).toList(), HttpStatus.OK);
    }

    @PostMapping("/{id}/changerank/{rank}")
    public void edit(@PathVariable Long id, @PathVariable String rank) {

        studentService.updateRank(id, rank);
    }
}
