package mx.luxore.service;

import mx.luxore.dto.CatalogDto;
import mx.luxore.dto.CityDto;
import mx.luxore.dto.ColonyDto;
import org.springframework.http.ResponseEntity;

public interface CatalogsService {

    ResponseEntity<?> getCatalogs(String catalog);

    ResponseEntity<?> addAmenity(CatalogDto amenity);
    ResponseEntity<?> addCity(CityDto city);
    ResponseEntity<?> addColony(ColonyDto colony);
    ResponseEntity<?> addPropertyType(CatalogDto property);
}
