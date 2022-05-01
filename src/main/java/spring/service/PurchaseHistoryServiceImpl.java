package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.entity.PurchaseHistory;
import spring.entity.SeasonTicket;
import spring.entity.Student;
import spring.exception.SeasonTicketNotFoundException;
import spring.repositories.PurchaseHistoryRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService
{
    private PurchaseHistoryRepository purchaseHistoryRepository;

    public Optional<PurchaseHistory> findPurchaseWithActiveSeasonTicket(Long studentId, LocalDate date)
    {
        return purchaseHistoryRepository.findUnspentSeasonTicket(studentId).stream().filter(purchaseHistory ->
                DAYS.between(date, purchaseHistory.getStartDate()) <= purchaseHistory.getSeasonTicket().getDaysDuration()).findFirst();
    }

    public SeasonTicket findActiveSeasonTicket(Long studentId, LocalDate date)
    {
        Optional<PurchaseHistory> optionalPurchaseHistory = findPurchaseWithActiveSeasonTicket(studentId, date);
        if (optionalPurchaseHistory.isPresent()) {
            return optionalPurchaseHistory.get().getSeasonTicket();
        }
        throw new SeasonTicketNotFoundException("Season ticket is not found");
    }

    public Boolean existByStudentId(Long studentId)
    {
        return purchaseHistoryRepository.existsByStudentId(studentId);
    }







    public Boolean checkActiveSeasonTicket(Long studentId, LocalDate date)
    {
        return findPurchaseWithActiveSeasonTicket(studentId, date).isPresent();
    }

    public List<SeasonTicket> findTicketsByStudentId(Long studentId)
    {
        List<PurchaseHistory> listPurchaseHistory = purchaseHistoryRepository.findByStudentId(studentId);
        return listPurchaseHistory.stream().map(PurchaseHistory::getSeasonTicket).toList();
    }

    public PurchaseHistory addPurchase(Student student, LocalDate startDate, SeasonTicket seasonTicket)
    {
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.setStudent(student);
        purchaseHistory.setStartDate(startDate);
        purchaseHistory.setSeasonTicket(seasonTicket);
        purchaseHistory.setAvailableClasses(seasonTicket.getNumberOfClasses());
        purchaseHistoryRepository.save(purchaseHistory);
        return purchaseHistory;
    }

    public void changeAvailableClasses(PurchaseHistory purchaseHistory, Boolean toReduce)
    {
        Integer availableClasses = purchaseHistory.getAvailableClasses();
        if (toReduce)
        {
            if (availableClasses != 0)
            {
                availableClasses--;
                if (availableClasses == 0)
                {
                    purchaseHistory.getStudent().setHasPaid(false);
                }
            }
        }
        else
        {
            availableClasses++;
        }
        purchaseHistory.setAvailableClasses(availableClasses);
    }

    public Boolean changeAvailableClassesFromActivePurchase(Long studentId, LocalDate date, Boolean toReduce)
    {
        Optional<PurchaseHistory> optionalPurchaseHistory = findPurchaseWithActiveSeasonTicket(studentId, date);
        if (optionalPurchaseHistory.isPresent())
        {
            changeAvailableClasses(optionalPurchaseHistory.get(), toReduce);
            return true;
        }
        return false;
    }

    public Boolean changeAvailableClassesFromLastPurchase(Long studentId, Boolean toReduce)
    {
        Optional<PurchaseHistory> optionalPurchaseHistory = purchaseHistoryRepository.findTopByStudentIdOrderByStartDateDesc(studentId);
        if (optionalPurchaseHistory.isPresent())
        {
            changeAvailableClasses(optionalPurchaseHistory.get(), toReduce);
            return true;
        }
        return false;
    }


    @Autowired
    public void setPurchaseHistoryRepository(PurchaseHistoryRepository purchaseHistoryRepository)
    {
        this.purchaseHistoryRepository = purchaseHistoryRepository;
    }
}
