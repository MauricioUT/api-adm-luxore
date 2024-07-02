package mx.luxore.repositorywrapper;

import mx.luxore.entity.CRoles;
import mx.luxore.repository.CRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CRolesRepositoryWrapper {

    @Autowired
    private CRolesRepository repository;

    public List<CRoles> findAll() {
        return repository.findAll();
    }

    public Optional<CRoles> findById(int id) {
        return repository.findById(id);
    }

    public void save(CRoles roles) {
        this.repository.saveAndFlush(roles);
    }

    public Optional<CRoles> findByName(String name) {
        return repository.findByName(name);
    }
}
