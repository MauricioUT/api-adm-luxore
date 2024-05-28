package mx.luxore.repository;

import mx.luxore.entity.CColony;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CColonyRepository extends JpaRepository<CColony, Integer> {
}