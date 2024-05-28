package mx.luxore.repository;

import mx.luxore.entity.TAmenitiesProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TAmenitiesPropertyRepository extends JpaRepository<TAmenitiesProperty, Integer> {
}