package mx.luxore.repositorywrapper;

import mx.luxore.entity.TProperty;
import mx.luxore.repository.TPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TPropertyRepositoryWrapper {

    @Autowired
    private TPropertyRepository repository;

    public List<TProperty> findAll() {
        return repository.findAll();
    }

    public Optional<TProperty> findById(int id) {
        return repository.findById(id);
    }

    public Integer save(TProperty property) {
        this.repository.saveAndFlush(property);
        return property.getId();
    }

    public Integer update(TProperty property) {
        this.repository.saveAndFlush(property);
        return property.getId();
    }
}
