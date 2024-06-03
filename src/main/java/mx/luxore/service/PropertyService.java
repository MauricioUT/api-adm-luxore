package mx.luxore.service;

import mx.luxore.dto.PropertyReqDto;
import mx.luxore.dto.TPropertyDto;
import org.springframework.http.ResponseEntity;

public interface PropertyService {

    ResponseEntity<?> getProperties(PropertyReqDto requets);
    ResponseEntity<?> getPropertyById(int id);
    ResponseEntity<?> saveProperty(TPropertyDto prop);
    ResponseEntity<?> updateProperty(TPropertyDto prop);
    ResponseEntity<?> deleteProperty(int id);

}
