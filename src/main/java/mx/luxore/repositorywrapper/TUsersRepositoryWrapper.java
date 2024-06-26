package mx.luxore.repositorywrapper;

import mx.luxore.entity.TUsers;
import mx.luxore.repository.TUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TUsersRepositoryWrapper {

    @Autowired
    private TUsersRepository repository;

    public Page<TUsers> findAll(Pageable pag) {
        return repository.findAll(pag);
    }

    public Optional<TUsers> findById(int id) {
        return repository.findById(id);
    }

    public Optional<TUsers> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public void save(TUsers users) {
        this.repository.saveAndFlush(users);
    }

    public void delete(TUsers users) {
        this.repository.delete(users);
    }
}
