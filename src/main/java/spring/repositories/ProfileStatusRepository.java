package spring.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.entity.ProfileStatus;

import java.util.Optional;

@Repository
public interface ProfileStatusRepository extends JpaRepository<ProfileStatus, String> {
    @Query("select p from ProfileStatus p where p.status = ?1")
    Optional<ProfileStatus> findByStatus(String status);
}
