package spring.service;

import spring.entity.PurchaseHistory;
import spring.entity.SeasonTicket;
import spring.entity.Student;

import java.time.LocalDate;

public interface PurchaseHistoryService
{
    PurchaseHistory findPurchaseWithActiveSeasonTicket(Long studentId, LocalDate date);
    SeasonTicket findActiveSeasonTicket(Long studentId, LocalDate date);
    Boolean existByStudentId(Long studentId);
    Boolean changeAvailableClassesFromActivePurchase(Long studentId, LocalDate date, Boolean toReduce);
    Boolean changeAvailableClassesFromLastPurchase(Long studentId, Boolean toReduce);
    PurchaseHistory addPurchase(Student student, LocalDate startDate, SeasonTicket seasonTicket);



   /* Boolean checkActiveSeasonTicket(Long studentId, LocalDate date);
    List<SeasonTicket> findTicketsByStudentId(Long studentId);
    */

}
