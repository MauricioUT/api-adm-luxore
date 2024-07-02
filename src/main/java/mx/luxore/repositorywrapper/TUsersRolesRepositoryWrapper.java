package mx.luxore.repositorywrapper;

import mx.luxore.entity.TUserRoles;
import mx.luxore.repository.TUserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class TUsersRolesRepositoryWrapper {

    @Autowired
    private TUserRolesRepository repository;

    public List<TUserRoles> findAll() {
        return repository.findAll();
    }

    public void save(TUserRoles ur) {
        this.repository.saveAndFlush(ur);
    }

    public void saveAll(Set<TUserRoles> urs) {
        this.repository.saveAll(urs);
    }

    public void delete(Set<TUserRoles> urs) {
        this.repository.deleteAll(urs);
    }
}
