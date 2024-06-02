package mx.luxore.repositorywrapper;

import mx.luxore.entity.TProperty;
import mx.luxore.repository.TPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TPropertyRepositoryWrapper {

    @Autowired
    private TPropertyRepository repository;

    public Page<TProperty> findAll(Pageable pag) {
        return repository.findAll(pag);
    }

    public List<TProperty> findByTitleLike(Pageable pag, String title) {
        return repository.findByTitleLike(title, pag);
    }

    public List<TProperty> findByTitleLikeAndIdCategory_Id(Pageable pag, String title, Integer idCategory) {
        return repository.findByTitleLikeAndIdCategory_Id(title, idCategory, pag);
    }

    public List<TProperty> findByIdCategory_Id(Pageable pag, Integer idCategory) {
        return repository.findByIdCategory_Id(idCategory, pag);
    }

    public long countAll() {
        return repository.count();
    }

    public long countByTitleLike(String title) {
        return repository.countByTitleLike(title);
    }

    public long countByTitleLikeAndIdCategory_Id(String title, Integer idCategory) {
        return repository.countByTitleLikeAndIdCategory_Id(title, idCategory);
    }

    public long countByIdCategory_Id(Integer idCategory) {
        return repository.countByIdCategory_Id(idCategory);
    }

    public Optional<TProperty> findById(int id) {
        return repository.findById(id);
    }

    public Integer save(TProperty property) {
        this.repository.saveAndFlush(property);
        return property.getId();
    }
}
