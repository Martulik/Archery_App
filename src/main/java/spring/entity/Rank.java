package spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ranks")
public class Rank {
    @Id
    @Column(name = "rank_name", nullable = false)
    private String rank_name;

    @Column(name = "color", nullable = false)
    private String color;

    @JsonBackReference
    @OneToMany(mappedBy = "rank_name")
    private List<Student> students;

    @Override
    public String toString() {
        return "Rank{" +
                "rank_name='" + rank_name + '\'' +
                ", color='" + color + '\'' +
                ", students=" + students +
                '}';
    }
}
