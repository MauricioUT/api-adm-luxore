package mx.luxore.repository;

import mx.luxore.entity.CCity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CCityRepository extends JpaRepository<CCity, Integer> {

    List<CCity> findByIdState_Id(Integer id, Pageable pageable);

    long countByIdState_Id(Integer id);

}