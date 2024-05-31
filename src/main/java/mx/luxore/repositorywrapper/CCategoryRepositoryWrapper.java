package mx.luxore.repositorywrapper;

import mx.luxore.entity.CCategory;
import mx.luxore.repository.CCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CCategoryRepositoryWrapper {

    @Autowired
    private CCategoryRepository repository;

    public List<CCategory> findAll() {
        return repository.findAll();
    }

}
