package mx.luxore.repositorywrapper;

import mx.luxore.entity.TImage;
import mx.luxore.repository.TImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TImageRepositoryWrapper {

    @Autowired
    private TImageRepository repository;

    public Optional<TImage> findById(int id) {
        return repository.findById(id);
    }

    public void delete(TImage image) {
        this.repository.delete(image);
    }
    public void save(TImage image) {
        this.repository.saveAndFlush(image);
    }

}
