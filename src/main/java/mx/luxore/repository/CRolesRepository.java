package mx.luxore.repository;

import mx.luxore.entity.CRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CRolesRepository extends JpaRepository<CRoles, Integer> {

    Optional<CRoles> findByName(String name);
}