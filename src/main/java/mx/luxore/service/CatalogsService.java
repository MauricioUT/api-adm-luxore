package mx.luxore.service;

import mx.luxore.dto.CatalogDto;
import mx.luxore.dto.CityDto;
import mx.luxore.dto.ColonyDto;
import mx.luxore.dto.PageableDto;
import org.springframework.http.ResponseEntity;

public interface CatalogsService {

    ResponseEntity<?> getCatalogs(String catalog);
    ResponseEntity<?> getCityPageable(CityDto state);
    ResponseEntity<?> getColonyPageable(ColonyDto city);
    ResponseEntity<?> getAmenityPageable(PageableDto page);
    ResponseEntity<?> getColonyById(int id);

    ResponseEntity<?> addAmenity(CatalogDto amenity);
    ResponseEntity<?> addCity(CityDto city);
    ResponseEntity<?> addColony(ColonyDto colony);
    ResponseEntity<?> addPropertyType(CatalogDto property);


    ResponseEntity<?> updateAmenity(CatalogDto amenity);
    ResponseEntity<?> updateCity(CityDto city);
    ResponseEntity<?> updateColony(ColonyDto colony);
    ResponseEntity<?> updatePropertyType(CatalogDto property);
}
