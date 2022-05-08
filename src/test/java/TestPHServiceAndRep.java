import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.Application;
import spring.exception.SeasonTicketNotFoundException;
import spring.repositories.PurchaseHistoryRepository;
import spring.repositories.SeasonTicketRepository;
import spring.service.PurchaseHistoryService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=Application.class)
public class TestPHServiceAndRep
{

    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;

    @Autowired
    PurchaseHistoryService purchaseHistoryService;

    @Autowired
    SeasonTicketRepository seasonTicketRepository;

    @Test
    void testFindingOfActiveTicket()
    {
        assertThrows(SeasonTicketNotFoundException.class, () -> {
            purchaseHistoryService.findActiveSeasonTicket(28L, LocalDate.now()).getTicketType();
        });
        assertEquals("UNLIMIT", purchaseHistoryService.findActiveSeasonTicket(29L, LocalDate.now()).getTicketType());
        assertEquals("LONG_LIMIT", purchaseHistoryService.findActiveSeasonTicket(30L, LocalDate.now()).getTicketType());
        assertThrows(SeasonTicketNotFoundException.class, () -> {
            purchaseHistoryService.findActiveSeasonTicket(31L, LocalDate.now()).getTicketType();
        });
    }

    @Test
    void testExistingOfPurchases()
    {
        assertFalse(purchaseHistoryService.existByStudentId(28L));
        assertTrue(purchaseHistoryService.existByStudentId(29L));
        assertTrue(purchaseHistoryService.existByStudentId(30L));
        assertTrue(purchaseHistoryService.existByStudentId(31L));
    }

    @Test
    void testLastPurchase()
    {
        assertEquals("UNLIMIT", purchaseHistoryRepository.findTopByStudentIdOrderByStartDateDesc(29L).get().getSeasonTicket().getTicketType());
        assertFalse(purchaseHistoryService.changeAvailableClassesFromLastPurchase(28L, false));
    }
}
