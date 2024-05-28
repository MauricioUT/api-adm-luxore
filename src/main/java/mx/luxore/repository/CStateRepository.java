package mx.luxore.repository;

import mx.luxore.entity.CState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CStateRepository extends JpaRepository<CState, Integer> {
}