package mx.luxore.repositorywrapper;

import mx.luxore.entity.CState;
import mx.luxore.repository.CStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CStateRepositoryWrapper {

    @Autowired
    private CStateRepository repository;

    public List<CState> findAll() {
        return repository.findAll();
    }

    public Optional<CState> findById(int id) {
        return repository.findById(id);
    }
}
