package mx.luxore.repositorywrapper;

import mx.luxore.entity.TAmenitiesProperty;
import mx.luxore.repository.TAmenitiesPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TAmenitiesPropertyRepositoryWrapper {

    @Autowired
    private TAmenitiesPropertyRepository repository;

    public List<TAmenitiesProperty> findAll() {
        return repository.findAll();
    }

    public Optional<TAmenitiesProperty> findById(int id) {
        return repository.findById(id);
    }

    public Integer save(TAmenitiesProperty amenitiesProperty) {
        this.repository.saveAndFlush(amenitiesProperty);
        return amenitiesProperty.getId();
    }

    public Integer update(TAmenitiesProperty amenitiesProperty) {
        this.repository.saveAndFlush(amenitiesProperty);
        return amenitiesProperty.getId();
    }
}
