package mx.luxore.repositorywrapper;

import mx.luxore.entity.CCity;
import mx.luxore.entity.CColony;
import mx.luxore.repository.CCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CCityRepositoryWrapper {

    @Autowired
    private CCityRepository repository;

    public List<CCity> findByIdState_Id(int idState, Pageable pageable) {
        return repository.findByIdState_Id(idState, pageable);
    }

    public long sizeColonyByState(int idState) {
        return repository.countByIdState_Id(idState);
    }

    public Optional<CCity> findById(int id) {
        return repository.findById(id);
    }

    public Integer save(CCity city) {
        this.repository.saveAndFlush(city);
        return city.getId();
    }

}
