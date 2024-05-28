package mx.luxore.repositorywrapper;

import mx.luxore.entity.CPropertyType;
import mx.luxore.repository.CPropertyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CPropertyTypeRepositoryWrapper {

    @Autowired
    private CPropertyTypeRepository repository;

    public List<CPropertyType> findAll() {
        return repository.findAll();
    }

    public Optional<CPropertyType> findById(int id) {
        return repository.findById(id);
    }

    public Integer save(CPropertyType propertyType) {
        this.repository.saveAndFlush(propertyType);
        return propertyType.getId();
    }

    public Integer update(CPropertyType propertyType) {
        this.repository.saveAndFlush(propertyType);
        return propertyType.getId();
    }
}
