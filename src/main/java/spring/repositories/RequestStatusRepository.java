package spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.entity.Request;
import spring.entity.RequestStatus;

import java.util.Optional;

@Repository
public interface RequestStatusRepository extends JpaRepository<RequestStatus, Long>
{
    Optional<RequestStatus> findByStatus(String status);
}
