package mx.luxore.repository;

import mx.luxore.entity.TProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TPropertyRepository extends JpaRepository<TProperty, Integer> {
}