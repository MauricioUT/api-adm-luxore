package mx.luxore.repositorywrapper;

import mx.luxore.entity.CAmenity;
import mx.luxore.repository.CAmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CAmenityRepositoryWrapper {

    @Autowired
    private CAmenityRepository repository;

    public List<CAmenity> findAll() {
        return repository.findAll();
    }

    public Optional<CAmenity> findById(int id) {
        return repository.findById(id);
    }

    public Integer save(CAmenity amenity) {
        this.repository.saveAndFlush(amenity);
        return amenity.getId();
    }

    public Integer update(CAmenity amenity) {
        this.repository.saveAndFlush(amenity);
        return amenity.getId();
    }
}
