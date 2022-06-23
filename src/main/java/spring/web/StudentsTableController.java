package spring.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.entity.PurchaseHistory;
import spring.entity.Rank;
import spring.entity.SeasonTicket;
import spring.entity.Student;
import spring.exception.PurchaseNotFoundException;
import spring.repositories.SeasonTicketRepository;
import spring.repositories.StudentRepository;
import spring.requests.StudentAndRankRequest;
import spring.requests.StudentAndTicketRequest;
import spring.requests.TicketInfoRequest;
import spring.service.PurchaseHistoryService;
import spring.service.RankService;
import spring.service.StudentService;

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

    @GetMapping("/id")
    public ResponseEntity<Student> showStudent(@RequestBody Long id)
    {
        return new ResponseEntity<>(studentService.findStudentById(id), HttpStatus.OK);
    }

    @GetMapping("/ticket")
    public ResponseEntity<TicketInfoRequest> showTicket(@RequestBody Long id)
    {
        String ticket = "";
        TicketInfoRequest ticketInfoRequest = new TicketInfoRequest("", 0);
        try
        {
            PurchaseHistory purchaseHistory = purchaseHistoryService.findPurchaseWithActiveSeasonTicket(id, LocalDate.now());
            ticketInfoRequest.setTicketType(purchaseHistory.getSeasonTicket().getTicketType());
            ticketInfoRequest.setAvailableClasses(purchaseHistory.getAvailableClasses());
        }
        catch (PurchaseNotFoundException ignored) {}
        return new ResponseEntity<>(ticketInfoRequest, HttpStatus.OK);
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<String>> findTickets()
    {
        return new ResponseEntity<>(ticketRepository.findAll().stream().map(SeasonTicket::getTicketType).toList(), HttpStatus.OK);
    }

    @PostMapping("/changeticket")
    public void editTicket(@RequestBody StudentAndTicketRequest studentAndTicketRequest)
    {
        Student student = studentService.findStudentById(studentAndTicketRequest.getId());
        SeasonTicket seasonTicket = ticketRepository.findByTicketType(studentAndTicketRequest.getSeasonTicketType()).get();
        purchaseHistoryService.addPurchase(student, LocalDate.now(), seasonTicket);
    }

    /*@PostMapping("/{id}/changeclasses/{increase}")
    public void editClasses(@PathVariable Long id, @PathVariable Boolean increase) {

        studentService.changeAttendedClasses(id, increase);
    }*/

    @GetMapping("/ranks")
    public ResponseEntity<List<String>> findRanks()
    {
        return new ResponseEntity<>(rankService.findAll().stream().map(Rank::getRank_name).toList(), HttpStatus.OK);
    }

    @PostMapping("/changerank")
    public void edit(@RequestBody StudentAndRankRequest studentAndRankRequest) {

        studentService.updateRank(studentAndRankRequest.getId(), studentAndRankRequest.getRank());
    }
}
