package mx.luxore.serviceImpl;

import mx.luxore.dto.*;
import mx.luxore.entity.*;
import mx.luxore.exception.ResourceNotFoundException;
import mx.luxore.repositorywrapper.*;
import mx.luxore.service.PropertyService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private TPropertyRepositoryWrapper propertyRepositoryWrapper;

    @Autowired
    private TAmenitiesPropertyRepositoryWrapper amenitiesPropertyRepositoryWrapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CPropertyTypeRepositoryWrapper propertyTypeRepositoryWrapper;

    @Autowired
    private CCityRepositoryWrapper cityRepositoryWrapper;

    @Autowired
    private CStateRepositoryWrapper stateRepositoryWrapper;

    @Autowired
    private CColonyRepositoryWrapper colonyRepositoryWrapper;

    @Autowired
    private CCategoryRepositoryWrapper categoryRepositoryWrapper;

    @Autowired
    private CAmenityRepositoryWrapper amenityRepositoryWrapper;

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
            propertiesDto.setEnabled(p.getEnable());
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
    public ResponseEntity<?> saveProperty(TPropertyDto prop) {
        Optional<CPropertyType> propertyType = propertyTypeRepositoryWrapper.findById(prop.getIdPropertyType().getId());
        Optional<CCity> city = cityRepositoryWrapper.findById(prop.getIdCity().getId());
        Optional<CState> state = stateRepositoryWrapper.findById(prop.getIdState().getId());
        Optional<CColony> colony = colonyRepositoryWrapper.findById(prop.getIdColony().getId());
        Optional<CCategory> category = categoryRepositoryWrapper.findById(prop.getIdCategory().getId());

        if (propertyType.isEmpty() || city.isEmpty() || state.isEmpty() || colony.isEmpty() || category.isEmpty())
            throw new ResourceNotFoundException("propertyType", " ", " ", new Throwable("updateProperty()"), this.getClass().getName());

        TProperty property = new TProperty();
        property.setIdPropertyType(propertyType.get());
        property.setMainImage("");
        property.setIdCity(city.get());
        property.setIdState(state.get());
        property.setIdColony(colony.get());
        property.setIdCategory(category.get());
        property.setPrice(prop.getPrice());
        property.setTitle(prop.getTitle());
        property.setDescription(prop.getDescription());
        property.setAddres(prop.getAddres());
        property.setGarage(prop.getGarage());
        property.setCarsNumber(prop.getCarsNumber());
        property.setRooms(prop.getRooms());
        property.setBedrooms(prop.getBedrooms());
        property.setBathrooms(prop.getBathrooms());
        property.setPostedYear(Instant.now());
        property.setMetersSurface(prop.getMetersSurface());
        property.setMetersBuilded(prop.getMetersBuilded());
        property.setFeaturedProperty(prop.getFeaturedProperty());
        property.setUpdateOn(Instant.now());
        property.setZip(prop.getZip());
        property.setFloors(prop.getFloors());
        property.setFeatures(prop.getFeatures());
        property.setLatitude(prop.getLatitude());
        property.setLongitude(prop.getLongitude());
        property.setPageAddress(prop.getPageAddress());
        property.setComercialValue(prop.getComercialValue());
        property.setNotes(prop.getNotes());
        property.setCredit(prop.getCredit());
        property.setSold(prop.getSold() != null ? prop.getSold() : false);
        property.setSlugTitle(prop.getSlugTitle());
        property.setEnable(prop.getEnable());
        propertyRepositoryWrapper.save(property);
        updateAmenities(property, prop);
        propertyRepositoryWrapper.save(property);
        return new ResponseEntity<>(new DefaultMessage(property.getId().toString(), 200), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateProperty(TPropertyDto prop) {
        Optional<TProperty> property = propertyRepositoryWrapper.findById(prop.getId());
        Optional<CPropertyType> propertyType = propertyTypeRepositoryWrapper.findById(prop.getIdPropertyType().getId());
        Optional<CCity> city = cityRepositoryWrapper.findById(prop.getIdCity().getId());
        Optional<CState> state = stateRepositoryWrapper.findById(prop.getIdState().getId());
        Optional<CColony> colony = colonyRepositoryWrapper.findById(prop.getIdColony().getId());
        Optional<CCategory> category = categoryRepositoryWrapper.findById(prop.getIdCategory().getId());

        if (property.isEmpty() || propertyType.isEmpty() || city.isEmpty() || state.isEmpty() || colony.isEmpty() || category.isEmpty())
            throw new ResourceNotFoundException("propertyType", " ", " ", new Throwable("updateProperty()"), this.getClass().getName());

        updateAmenities(property.get(), prop);
        property.get().setIdPropertyType(propertyType.get());
        property.get().setIdCity(city.get());
        property.get().setIdState(state.get());
        property.get().setIdColony(colony.get());
        property.get().setIdCategory(category.get());
        property.get().setPrice(prop.getPrice());
        property.get().setTitle(prop.getTitle());
        property.get().setDescription(prop.getDescription());
        property.get().setAddres(prop.getAddres());
        property.get().setGarage(prop.getGarage());
        property.get().setCarsNumber(prop.getCarsNumber());
        property.get().setRooms(prop.getRooms());
        property.get().setBedrooms(prop.getBedrooms());
        property.get().setBathrooms(prop.getBathrooms());
        property.get().setPostedYear(prop.getPostedYear());
        property.get().setMetersSurface(prop.getMetersSurface());
        property.get().setMetersBuilded(prop.getMetersBuilded());
        property.get().setFeaturedProperty(prop.getFeaturedProperty());
        property.get().setUpdateOn(Instant.now());
        property.get().setZip(prop.getZip());
        property.get().setFloors(prop.getFloors());
        property.get().setFeatures(prop.getFeatures());
        property.get().setFloors(prop.getFloors());
        property.get().setLatitude(prop.getLatitude());
        property.get().setLongitude(prop.getLongitude());
        property.get().setPageAddress(prop.getPageAddress());
        property.get().setComercialValue(prop.getComercialValue());
        property.get().setNotes(prop.getNotes());
        property.get().setCredit(prop.getCredit());
        property.get().setSold(prop.getSold());
        property.get().setSlugTitle(prop.getSlugTitle());
        property.get().setEnable(prop.getEnable());
        propertyRepositoryWrapper.save(property.get());

        return new ResponseEntity<>(new DefaultMessage(property.get().getId().toString(), 200), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteProperty(int id) {
        Optional<TProperty> property = propertyRepositoryWrapper.findById(id);
        if (property.isEmpty())
            throw new ResourceNotFoundException("Propiedades", " ", " ", new Throwable("deleteProperty()"), this.getClass().getName());

        property.get().setEnable(false);
        propertyRepositoryWrapper.save(property.get());
        return new ResponseEntity<>(new DefaultMessage(property.get().getId().toString(), 200), HttpStatus.OK);
    }


    public void updateAmenities(TProperty propsE, TPropertyDto prop) {
        propsE.getTAmenitiesProperties().forEach(amp -> {
            amenitiesPropertyRepositoryWrapper.delete(amp);
        });
        Set<TAmenitiesProperty> amenitiesProperties = prop.getAmenities().stream().map(p -> {
            TAmenitiesProperty am = new TAmenitiesProperty();
            am.setIdProperty(propsE);
            Optional<CAmenity> amenity = amenityRepositoryWrapper.findById(p.getId());
            if (amenity.isEmpty())
                throw new ResourceNotFoundException("propertyType", " ", " ", new Throwable("updateAmenities()"), this.getClass().getName());
            am.setIdAmenity(amenity.get());
            amenitiesPropertyRepositoryWrapper.save(am);
            return am;
        }).collect(Collectors.toSet());

        propsE.setTAmenitiesProperties(amenitiesProperties);
    }
}
