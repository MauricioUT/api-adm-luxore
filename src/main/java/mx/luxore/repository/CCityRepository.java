package mx.luxore.repository;

import mx.luxore.entity.CCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CCityRepository extends JpaRepository<CCity, Integer> {
}