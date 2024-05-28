package mx.luxore.repositorywrapper;

import mx.luxore.entity.CColony;
import mx.luxore.repository.CColonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CColonyRepositoryWrapper {

    @Autowired
    private CColonyRepository repository;

    public List<CColony> findAll() {
        return repository.findAll();
    }

    public Optional<CColony> findById(int id) {
        return repository.findById(id);
    }

    public Integer save(CColony colony) {
        this.repository.saveAndFlush(colony);
        return colony.getId();
    }

    public Integer update(CColony colony) {
        this.repository.saveAndFlush(colony);
        return colony.getId();
    }
}
