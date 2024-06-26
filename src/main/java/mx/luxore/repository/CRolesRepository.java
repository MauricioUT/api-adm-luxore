package mx.luxore.repository;

import mx.luxore.entity.CRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CRolesRepository extends JpaRepository<CRoles, Integer> {
}