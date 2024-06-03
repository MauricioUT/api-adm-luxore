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

    public List<TAmenitiesProperty> findByIdProperty_Id(int id) {
        return repository.findByIdProperty_Id(id);
    }

    public Optional<TAmenitiesProperty> findById(int id) {
        return repository.findById(id);
    }

    public void save(TAmenitiesProperty amenitiesProperty) {
        this.repository.saveAndFlush(amenitiesProperty);
    }

    public void delete(TAmenitiesProperty amenitiesProperty) {
        this.repository.delete(amenitiesProperty);
    }

}
