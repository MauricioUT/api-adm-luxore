package mx.luxore.repositorywrapper;

import mx.luxore.entity.TUserRoles;
import mx.luxore.repository.TUserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TUsersRolesRepositoryWrapper {

    @Autowired
    private TUserRolesRepository repository;

    public List<TUserRoles> findAll() {
        return repository.findAll();
    }

    public void save(TUserRoles ur) {
        this.repository.saveAndFlush(ur);
    }
}
