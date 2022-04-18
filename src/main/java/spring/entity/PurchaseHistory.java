package spring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "purchase_history", schema = "paradoks")
public class PurchaseHistory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "purchase_id")
    private Long purchaseId;
    @Basic
    @Column(name = "start_date")
    private Date startDate;
    @Basic
    @Column(name = "student_id")
    private Long studentId;
    @Basic
    @Column(name = "ticket_type")
    private String ticketType;
    @Basic
    @Column(name = "available_classes")
    private Integer availableClasses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseHistory that = (PurchaseHistory) o;
        return purchaseId == that.purchaseId && studentId == that.studentId && Objects.equals(startDate, that.startDate) && Objects.equals(ticketType, that.ticketType) && Objects.equals(availableClasses, that.availableClasses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseId, startDate, studentId, ticketType, availableClasses);
    }
}
