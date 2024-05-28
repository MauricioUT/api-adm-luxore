package mx.luxore.repository;

import mx.luxore.entity.CPropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CPropertyTypeRepository extends JpaRepository<CPropertyType, Integer> {
}