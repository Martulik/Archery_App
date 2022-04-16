package spring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "season_tickets")
public class SeasonTicket {
    @Id
    @Column(name = "ticket_type")
    private String ticketType;
    @Basic
    @Column(name = "cost")
    private double cost;
    @Basic
    @Column(name = "number_of_classes")
    private int numberOfClasses;
    @Basic
    @Column(name = "time_duration")
    private Time timeDuration;
    @Basic
    @Column(name = "days_duration")
    private int daysDuration;
    @Basic
    @Column(name = "is_for_sale")
    private byte isForSale;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeasonTicket that = (SeasonTicket) o;
        return Double.compare(that.cost, cost) == 0 && numberOfClasses == that.numberOfClasses && daysDuration == that.daysDuration && isForSale == that.isForSale && Objects.equals(ticketType, that.ticketType) && Objects.equals(timeDuration, that.timeDuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketType, cost, numberOfClasses, timeDuration, daysDuration, isForSale);
    }
}
