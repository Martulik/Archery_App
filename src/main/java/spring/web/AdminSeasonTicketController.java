package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.entity.SeasonTicket;
import spring.service.SeasonTicketService;

import java.util.List;

@RestController
@RequestMapping("/archery/admin/tickets")
public class AdminSeasonTicketController
{
    @Autowired
    SeasonTicketService seasonTicketService;

    @GetMapping("")
    public ResponseEntity<List<SeasonTicket>> showTickets()
    {
        return new ResponseEntity<>(seasonTicketService.findTickets(), HttpStatus.OK);
    }
}
