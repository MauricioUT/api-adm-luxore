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

    public List<TImage> findAll() {
        return repository.findAll();
    }

    public Optional<TImage> findById(int id) {
        return repository.findById(id);
    }

    public Integer save(TImage image) {
        this.repository.saveAndFlush(image);
        return image.getId();
    }

    public Integer update(TImage image) {
        this.repository.saveAndFlush(image);
        return image.getId();
    }
}
