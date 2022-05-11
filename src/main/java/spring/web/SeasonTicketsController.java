package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.entity.SeasonTicket;
import spring.exception.SeasonTicketNotFoundException;
import spring.service.PurchaseHistoryService;
import spring.service.SeasonTicketService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/archery/{id}/tickets")
public class SeasonTicketsController
{
   /* @Autowired
    SeasonTicketService seasonTicketService;
    @Autowired
    PurchaseHistoryService purchaseHistoryService;

    @GetMapping("")
    public ResponseEntity<List<SeasonTicket>> showTicketsForSale()
    {
        return new ResponseEntity<>(seasonTicketService.areForSale(), HttpStatus.OK);
    }

    @GetMapping("/activeticket")
    public ResponseEntity<String> showActiveTicket(@PathVariable long id)
    {
        try
        {
            SeasonTicket ticket = purchaseHistoryService.findActiveSeasonTicket(id, LocalDate.now());
            String stringTicket = ticket.getTicketType() + ticket.getCost() + ticket.getNumberOfClasses()
                    + ticket.getDaysDuration() + ticket.getTimeDuration();
            return new ResponseEntity<>(stringTicket, HttpStatus.OK);
        }
        catch (SeasonTicketNotFoundException e)
        {
            return new ResponseEntity<>("Нет активного абонемента", HttpStatus.OK);
        }
    }*/
}
