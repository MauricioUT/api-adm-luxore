package mx.luxore.repository;

import mx.luxore.entity.TImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TImageRepository extends JpaRepository<TImage, Integer> {
}