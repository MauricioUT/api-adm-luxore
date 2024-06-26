package mx.luxore.repository;

import mx.luxore.entity.TUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TUsersRepository extends JpaRepository<TUsers, Integer> {

    Optional<TUsers> findByUsername(String username);
}