package mx.luxore.repository;

import mx.luxore.entity.CColony;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CColonyRepository extends JpaRepository<CColony, Integer> {


    List<CColony> findByIdCity_Id(Integer id, Pageable pageable);

    long countByIdCity_Id(Integer id);
}