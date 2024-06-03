package mx.luxore.repository;

import mx.luxore.entity.TAmenitiesProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TAmenitiesPropertyRepository extends JpaRepository<TAmenitiesProperty, Integer> {

    List<TAmenitiesProperty> findByIdProperty_Id(Integer id);
}