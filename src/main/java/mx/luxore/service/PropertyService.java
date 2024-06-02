package mx.luxore.service;

import mx.luxore.dto.PropertyReqDto;
import org.springframework.http.ResponseEntity;

public interface PropertyService {

    ResponseEntity<?> getProperties(PropertyReqDto requets);
    ResponseEntity<?> getPropertyById(int id);
    ResponseEntity<?> saveProperty();
    ResponseEntity<?> updateProperty();
    ResponseEntity<?> deleteProperty();

}
