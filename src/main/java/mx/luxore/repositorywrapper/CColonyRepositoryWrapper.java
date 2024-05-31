package mx.luxore.repositorywrapper;

import mx.luxore.entity.CColony;
import mx.luxore.repository.CColonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CColonyRepositoryWrapper {

    @Autowired
    private CColonyRepository repository;


    public List<CColony> findByIdCity_Id(int idCity, Pageable pageable) {
        return repository.findByIdCity_Id(idCity, pageable);
    }

    public Optional<CColony> findById(int id) {
        return repository.findById(id);
    }

    public long sizeColonyByCity(int idCity) {
        return repository.countByIdCity_Id(idCity);
    }

    public Integer save(CColony colony) {
        this.repository.saveAndFlush(colony);
        return colony.getId();
    }

}
