package mx.luxore.repository;

import mx.luxore.entity.CCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CCategoryRepository extends JpaRepository<CCategory, Integer> {
}