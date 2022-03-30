package spring.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "profile_status")
public class ProfileStatus {
    @Id
    @Column(name = "status", nullable = false)
    private String status;

    @Override
    public String toString() {
        return "ProfileStatus{" +
                "rank_name='" + status + '\'' +
                '}';
    }
}
