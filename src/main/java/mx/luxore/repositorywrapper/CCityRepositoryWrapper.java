package mx.luxore.repositorywrapper;

import mx.luxore.entity.CCity;
import mx.luxore.repository.CCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CCityRepositoryWrapper {

    @Autowired
    private CCityRepository repository;

    public List<CCity> findAll() {
        return repository.findAll();
    }

    public Optional<CCity> findById(int id) {
        return repository.findById(id);
    }

    public Integer save(CCity city) {
        this.repository.saveAndFlush(city);
        return city.getId();
    }

    public Integer update(CCity city) {
        this.repository.saveAndFlush(city);
        return city.getId();
    }
}
