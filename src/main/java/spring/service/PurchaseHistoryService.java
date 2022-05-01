package spring.service;

import spring.entity.PurchaseHistory;
import spring.entity.SeasonTicket;
import spring.entity.Student;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PurchaseHistoryService
{
    SeasonTicket findActiveSeasonTicket(Long studentId, LocalDate date);
    Boolean existByStudentId(Long studentId);




    Boolean checkActiveSeasonTicket(Long studentId, LocalDate date);
    List<SeasonTicket> findTicketsByStudentId(Long studentId);
    PurchaseHistory addPurchase(Student student, LocalDate startDate, SeasonTicket seasonTicket);
    Boolean changeAvailableClassesFromActivePurchase(Long studentId, LocalDate date, Boolean toReduce);
    Boolean changeAvailableClassesFromLastPurchase(Long studentId, Boolean toReduce);
}
