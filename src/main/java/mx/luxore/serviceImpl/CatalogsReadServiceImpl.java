package mx.luxore.serviceImpl;

import mx.luxore.dto.*;
import mx.luxore.entity.*;
import mx.luxore.exception.ResourceNotFoundException;
import mx.luxore.repositorywrapper.*;
import mx.luxore.service.CatalogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CatalogsReadServiceImpl implements CatalogsService {

    @Autowired
    private CAmenityRepositoryWrapper amenityRepositoryWrapper;

    @Autowired
    private CCategoryRepositoryWrapper categoryRepositoryWrapper;

    @Autowired
    private CCityRepositoryWrapper cityRepositoryWrapper;

    @Autowired
    private CColonyRepositoryWrapper colonyRepositoryWrapper;

    @Autowired
    private CPropertyTypeRepositoryWrapper propertyTypeRepositoryWrapper;

    @Autowired
    private CStateRepositoryWrapper stateRepositoryWrapper;


    private static final String CATEGORIES = "categories";
    private static final String STATES = "states";
    private static final String PROPERTY_TYPE = "property";
    private static final String AMENITIES = "amenities";



    @Override
    public ResponseEntity<?> getCatalogs(String catalog) {
        return switch (catalog) {
            case CATEGORIES -> getCategories();
            case STATES -> getStates();
            case PROPERTY_TYPE -> getPropertyType();
            case AMENITIES -> getAmenities();
            default -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        };
    }

    @Override
    public ResponseEntity<?> getCityPageable(CityDto state) {
        Pageable pageable = PageRequest.of(state.getPage(), state.getTotalPage());
        List<CCity> cities = cityRepositoryWrapper.findByIdState_Id(state.getIdState(),pageable);
        if (cities.isEmpty())
            throw new ResourceNotFoundException("Ciudades", " ", " ", new Throwable("getCityPaginable()"), this.getClass().getName());
        List<CatalogDto> catalog = cities.stream().map(a -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(a.getId());
            cat.setDescription(a.getCity());
            return cat;
        }).collect(Collectors.toList());
        long size = cityRepositoryWrapper.sizeColonyByState(state.getIdState());
        return new ResponseEntity<>(new ObjectPageableDto(size, catalog), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getColonyPageable(ColonyDto city) {
        Pageable pageable = PageRequest.of(city.getPage(), city.getTotalPage());
        List<CColony> colonies = colonyRepositoryWrapper.findByIdCity_Id(city.getIdCity(), pageable);
        if (colonies.isEmpty())
            throw new ResourceNotFoundException("Colonias", " ", " ", new Throwable("getColonyPaginable()"), this.getClass().getName());
        List<ColonyDto> catalog = colonies.stream().map(a -> {
            ColonyDto cat = new ColonyDto();
            cat.setId(a.getId());
            cat.setDescription(a.getColony());
            cat.setPostalCode(a.getPostalCode());
            return cat;
        }).collect(Collectors.toList());
        long size = colonyRepositoryWrapper.sizeColonyByCity(city.getIdCity());
        return new ResponseEntity<>(new ObjectPageableDto(size, catalog), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAmenityPageable(CatalogDto page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getTotalPage());
        Page<CAmenity> amenities = amenityRepositoryWrapper.findAll(pageable);
        if (amenities.isEmpty())
            throw new ResourceNotFoundException("Amenidades", " ", " ", new Throwable("getAmeinities()"), this.getClass().getName());
        List<CatalogDto> catalog = amenities.stream().map(a -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(a.getId());
            cat.setDescription(a.getAmenity());
            return cat;
        }).collect(Collectors.toList());
        long size = amenityRepositoryWrapper.countAll();
        return new ResponseEntity<>(new ObjectPageableDto(size, catalog), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addAmenity(CatalogDto amenity) {
        CAmenity am = new CAmenity();
        am.setAmenity(amenity.getDescription());
        amenityRepositoryWrapper.save(am);
        return new ResponseEntity<>(new DefaultMessage(am.getId().toString(), 200), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addCity(CityDto city) {
        Optional<CState> state = stateRepositoryWrapper.findById(city.getIdState());
        if (state.isEmpty()) {
            throw new ResourceNotFoundException("Estado", " ", " ",
                    new Throwable("addCity()"), this.getClass().getName());
        }
        CCity ci = new CCity();
        ci.setCity(city.getDescription());
        ci.setIdState(state.get());
        cityRepositoryWrapper.save(ci);
        return new ResponseEntity<>(new DefaultMessage(ci.getId().toString(), 200), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addColony(ColonyDto colony) {
        Optional<CCity> city = cityRepositoryWrapper.findById(colony.getIdCity());
        if (city.isEmpty()) {
            throw new ResourceNotFoundException("Ciudad", " ", " ",
                    new Throwable("addCity()"), this.getClass().getName());
        }
        CColony co = new CColony();
        co.setColony(colony.getDescription());
        co.setIdCity(city.get());
        co.setPostalCode(colony.getPostalCode());
        colonyRepositoryWrapper.save(co);
        return new ResponseEntity<>(new DefaultMessage(co.getId().toString(), 200), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addPropertyType(CatalogDto property) {
        CPropertyType pt = new CPropertyType();
        pt.setPropertyType(property.getDescription());
        propertyTypeRepositoryWrapper.save(pt);
        return new ResponseEntity<>(new DefaultMessage(pt.getId().toString(), 200), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> updateAmenity(CatalogDto amenity) {
        Optional<CAmenity> am = amenityRepositoryWrapper.findById(amenity.getId());
        if (am.isEmpty()) {
            throw new ResourceNotFoundException("Amenidad", " ", " ",
                new Throwable("updateAmenity()"), this.getClass().getName());
        }
        am.get().setAmenity(amenity.getDescription());
        amenityRepositoryWrapper.save(am.get());
        return new ResponseEntity<>(new DefaultMessage(am.get().getId().toString(), 200), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateCity(CityDto city) {
        Optional<CCity> ci = cityRepositoryWrapper.findById(city.getId());
        if (ci.isEmpty()) {
            throw new ResourceNotFoundException("Ciudad", " ", " ",
                    new Throwable("updateCity()"), this.getClass().getName());
        }
        Optional<CState> state = stateRepositoryWrapper.findById(city.getIdState());
        if (state.isEmpty()) {
            throw new ResourceNotFoundException("Estado", " ", " ",
                    new Throwable("updateCity()"), this.getClass().getName());
        }
        ci.get().setCity(city.getDescription());
        ci.get().setIdState(state.get());
        cityRepositoryWrapper.save(ci.get());
        return new ResponseEntity<>(new DefaultMessage(ci.get().getId().toString(), 200), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateColony(ColonyDto colony) {
        Optional<CColony> col = colonyRepositoryWrapper.findById(colony.getId());
        if (col.isEmpty()) {
            throw new ResourceNotFoundException("Colonia", " ", " ",
                    new Throwable("updateColony()"), this.getClass().getName());
        }
        Optional<CCity> city = cityRepositoryWrapper.findById(colony.getIdCity());
        if (city.isEmpty()) {
            throw new ResourceNotFoundException("Ciudad", " ", " ",
                    new Throwable("updateColony()"), this.getClass().getName());
        }
        col.get().setIdCity(city.get());
        col.get().setColony(colony.getDescription());
        col.get().setPostalCode(colony.getPostalCode());
        colonyRepositoryWrapper.save(col.get());
        return new ResponseEntity<>(new DefaultMessage(col.get().getId().toString(), 200), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updatePropertyType(CatalogDto property) {
        Optional<CPropertyType> pt = propertyTypeRepositoryWrapper.findById(property.getId());
        if (pt.isEmpty()) {
            throw new ResourceNotFoundException("Tipo de propiedad", " ", " ",
                    new Throwable("updatePropertyType()"), this.getClass().getName());
        }
        pt.get().setPropertyType(property.getDescription());
        propertyTypeRepositoryWrapper.save(pt.get());
        return new ResponseEntity<>(new DefaultMessage(pt.get().getId().toString(), 200), HttpStatus.OK);
    }

    public ResponseEntity<?> getCategories() {
        List<CCategory> categories = categoryRepositoryWrapper.findAll();
        if (categories.isEmpty())
            throw new ResourceNotFoundException("Categorias", " ", " ", new Throwable("getCategories()"), this.getClass().getName());
        List<CatalogDto> catalog = categories.stream().map(a -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(a.getId());
            cat.setDescription(a.getCategory());
            return cat;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }

    private ResponseEntity<?> getStates() {
        List<CState> states = stateRepositoryWrapper.findAll();
        if (states.isEmpty())
            throw new ResourceNotFoundException("estados", " ", " ", new Throwable("getStates()"), this.getClass().getName());
        List<CatalogDto> catalog = states.stream().map(a -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(a.getId());
            cat.setDescription(a.getState());
            return cat;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }

    private ResponseEntity<?> getPropertyType() {
        List<CPropertyType> propertyTypes = propertyTypeRepositoryWrapper.findAll();
        if (propertyTypes.isEmpty())
            throw new ResourceNotFoundException("Tipo de propiedad", " ", " ", new Throwable("getPrpertyType()"), this.getClass().getName());
        List<CatalogDto> catalog = propertyTypes.stream().map(a -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(a.getId());
            cat.setDescription(a.getPropertyType());
            return cat;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }

    private ResponseEntity<?> getAmenities() {
        List<CAmenity> amenities = amenityRepositoryWrapper.findAll();
        if (amenities.isEmpty())
            throw new ResourceNotFoundException("amenidades", " ", " ", new Throwable("getAmenities()"), this.getClass().getName());
        List<CatalogDto> catalog = amenities.stream().map(a -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(a.getId());
            cat.setDescription(a.getAmenity());
            return cat;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }

}
