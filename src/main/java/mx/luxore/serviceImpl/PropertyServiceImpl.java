package mx.luxore.serviceImpl;

import mx.luxore.dto.*;
import mx.luxore.entity.CAmenity;
import mx.luxore.entity.TAmenitiesProperty;
import mx.luxore.entity.TProperty;
import mx.luxore.exception.ResourceNotFoundException;
import mx.luxore.repositorywrapper.CAmenityRepositoryWrapper;
import mx.luxore.repositorywrapper.TAmenitiesPropertyRepositoryWrapper;
import mx.luxore.repositorywrapper.TPropertyRepositoryWrapper;
import mx.luxore.service.PropertyService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private TPropertyRepositoryWrapper propertyRepositoryWrapper;

    @Autowired
    private TAmenitiesPropertyRepositoryWrapper amenitiesPropertyRepositoryWrapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> getProperties(PropertyReqDto request) {
        Pageable pag = PageRequest.of(request.getPage(), request.getTotalPage());
        List<TProperty> properties;
        long size = 0;
        if (request.getIdCategory() > 0 && !request.getWildCard().isBlank()) {
            properties = propertyRepositoryWrapper.findByTitleLikeAndIdCategory_Id(pag, request.getWildCard(), request.getIdCategory());
            size = propertyRepositoryWrapper.countByTitleLikeAndIdCategory_Id(request.getWildCard(), request.getIdCategory());
        } else if (!request.getWildCard().isBlank()) {
            properties = propertyRepositoryWrapper.findByTitleLike(pag, request.getWildCard());
            size = propertyRepositoryWrapper.countByTitleLike(request.getWildCard());
        } else if (request.getIdCategory() > 0) {
            properties = propertyRepositoryWrapper.findByIdCategory_Id(pag, request.getIdCategory());
            size = propertyRepositoryWrapper.countByIdCategory_Id(request.getIdCategory());
        } else {
            properties = propertyRepositoryWrapper.findAll(pag).toList();
            size = propertyRepositoryWrapper.countAll();
        }

        if (properties.isEmpty())
            throw new ResourceNotFoundException("Propiedades", " ", " ", new Throwable("getProperties()"), this.getClass().getName());

        List<PropertiesDto> propertiesDtos = properties.stream().map(p -> {
            PropertiesDto propertiesDto = new PropertiesDto();
            propertiesDto.setId(p.getId());
            propertiesDto.setTitle(p.getTitle());
            propertiesDto.setEnabled(p.getEnable() == 1);
            propertiesDto.setSold(p.getSold() != null && p.getSold());
            propertiesDto.setPostedYear(p.getPostedYear());
            propertiesDto.setPrice(p.getPrice());
            return propertiesDto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(new ObjectPageableDto(size, propertiesDtos), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> getPropertyById(int id) {
        Optional<TProperty> property = propertyRepositoryWrapper.findById(id);

        TPropertyDto propertyDto = modelMapper.map(property.orElseThrow(() ->
                new ResourceNotFoundException("Propiedad", " ", " ", new Throwable("getPropertyById()"), this.getClass().getName())
        ), TPropertyDto.class);


        List<CatalogDto> am = property.get().getTAmenitiesProperties().stream().map(a -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(a.getIdAmenity().getId());
            cat.setDescription(a.getIdAmenity().getAmenity());
            return cat;
        }).collect(Collectors.toList());

        List<ImgDto> imgs = modelMapper.map(property.get().getTImages(), new TypeToken<List<ImgDto>>() {
        }.getType());

        propertyDto.setAmenities(am);
        propertyDto.setImages(imgs);


        return new ResponseEntity<>(propertyDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveProperty() {
        return null;

    }

    @Override
    public ResponseEntity<?> updateProperty() {
        return null;

    }

    @Override
    public ResponseEntity<?> deleteProperty() {
        return null;

    }
}
