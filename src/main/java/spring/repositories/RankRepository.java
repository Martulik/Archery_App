package spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.entity.Rank;

import java.util.Optional;

@Repository
public interface RankRepository extends JpaRepository<Rank, String> {
    @Query("select r from Rank r where r.rank_name = ?1")
    Optional<Rank> findByRank_name(String name);
}
