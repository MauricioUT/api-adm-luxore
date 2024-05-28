package mx.luxore.serviceImpl;

import mx.luxore.dto.CatalogDto;
import mx.luxore.dto.CityDto;
import mx.luxore.dto.ColonyDto;
import mx.luxore.dto.DefaultMessage;
import mx.luxore.entity.*;
import mx.luxore.exception.ResourceNotFoundException;
import mx.luxore.repositorywrapper.*;
import mx.luxore.service.CatalogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CatalogsServiceImpl implements CatalogsService {

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

    private static final String AMENITIES = "amenities";
    private static final String CATEGORIES = "categories";
    private static final String STATES = "states";
    private static final String CITIES = "cities";
    private static final String COLONIES = "colonies";
    private static final String PROPERTY_TYPE = "property";


    @Override
    public ResponseEntity<?> getCatalogs(String catalog) {
        ResponseEntity<?> responseEntity = null;
        switch (catalog) {
            case AMENITIES:
                responseEntity = getAmeinities();
                break;
            case CATEGORIES:
                responseEntity = getCategories();
                break;
            case STATES:
                responseEntity = getStates();
                break;
            case CITIES:
                responseEntity = getCities();
                break;
            case COLONIES:
                responseEntity = getColonies();
                break;
            case PROPERTY_TYPE:
                responseEntity = getPrpertyType();
                break;
            default:
                responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                break;
        }

        return responseEntity;
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
        Optional<CState> state = stateRepositoryWrapper.findById(city.getStateID());
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
        Optional<CCity> city = cityRepositoryWrapper.findById(colony.getCityId());
        if (city.isEmpty()) {
            throw new ResourceNotFoundException("Ciudad", " ", " ",
                    new Throwable("addCity()"), this.getClass().getName());
        }
        CColony co = new CColony();
        co.setColony(colony.getDescription());
        co.setIdCity(city.get());
        co.setPostalCode(colony.getZip());
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


    public ResponseEntity<?> getAmeinities() {
        List<CAmenity> amenities = amenityRepositoryWrapper.findAll();
        if (amenities.isEmpty())
            throw new ResourceNotFoundException("Amenidades", " ", " ", new Throwable("getAmeinities()"), this.getClass().getName());
        List<CatalogDto> catalog = amenities.stream().map(a -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(a.getId());
            cat.setDescription(a.getAmenity());
            return cat;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }

    public ResponseEntity<?> getCategories() {
            List<CCategory> categories = categoryRepositoryWrapper.findAll();
            if (categories.isEmpty())
                throw new ResourceNotFoundException("Categorias", " ", " ", new Throwable("getAmeinities()"), this.getClass().getName());
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
            throw new ResourceNotFoundException("Categoria", " ", " ", new Throwable("getAmeinities()"), this.getClass().getName());
        List<CatalogDto> catalog = states.stream().map(a -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(a.getId());
            cat.setDescription(a.getState());
            return cat;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }

    private ResponseEntity<?> getCities() {
        List<CCity> cities = cityRepositoryWrapper.findAll();
        if (cities.isEmpty())
            throw new ResourceNotFoundException("Ciudades", " ", " ", new Throwable("getAmeinities()"), this.getClass().getName());
        List<CatalogDto> catalog = cities.stream().map(a -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(a.getId());
            cat.setDescription(a.getCity());
            return cat;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }

    private ResponseEntity<?> getColonies() {
        List<CColony> colonies = colonyRepositoryWrapper.findAll();
        if (colonies.isEmpty())
            throw new ResourceNotFoundException("Colonias", " ", " ", new Throwable("getAmeinities()"), this.getClass().getName());
        List<CatalogDto> catalog = colonies.stream().map(a -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(a.getId());
            cat.setDescription(a.getColony());
            return cat;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }

    private ResponseEntity<?> getPrpertyType() {
        List<CPropertyType> propertyTypes = propertyTypeRepositoryWrapper.findAll();
        if (propertyTypes.isEmpty())
            throw new ResourceNotFoundException("Tipo de propiedad", " ", " ", new Throwable("getAmeinities()"), this.getClass().getName());
        List<CatalogDto> catalog = propertyTypes.stream().map(a -> {
            CatalogDto cat = new CatalogDto();
            cat.setId(a.getId());
            cat.setDescription(a.getPropertyType());
            return cat;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(catalog, HttpStatus.OK);
    }


}
