package mx.luxore.repository;

import mx.luxore.entity.TUserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TUserRolesRepository extends JpaRepository<TUserRoles, Long> {
}