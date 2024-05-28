package mx.luxore.repository;

import mx.luxore.entity.CAmenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CAmenityRepository extends JpaRepository<CAmenity, Integer> {
}