package mx.luxore.repository;

import mx.luxore.entity.TProperty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TPropertyRepository extends JpaRepository<TProperty, Integer> {


    @Query("select t from TProperty t where t.title like concat('%', ?1,'%') or t.description like concat('%', ?1,'%') or t.addres like concat('%', ?1,'%')")
    List<TProperty> findByTitleLike(String title, Pageable pageable);

    @Query("select t from TProperty t where t.title like concat('%', ?1,'%') or t.description like concat('%', ?1,'%') or t.addres like concat('%', ?1,'%') and t.idCategory.id = ?2")
    List<TProperty> findByTitleLikeAndIdCategory_Id(String title, Integer id, Pageable pageable);

    List<TProperty> findByIdCategory_Id(Integer id, Pageable pageable);

    @Query("select count(t) from TProperty t where t.title like concat('%', ?1,'%') or t.description like concat('%', ?1,'%') or t.addres like concat('%', ?1,'%')")
    long countByTitleLike(String title);

    @Query("select count(t) from TProperty t where t.title like concat('%', ?1,'%') or t.description like concat('%', ?1,'%') or t.addres like concat('%', ?1,'%') and t.idCategory.id = ?2")
    long countByTitleLikeAndIdCategory_Id(String title, Integer id);

    long countByIdCategory_Id(Integer id);


}