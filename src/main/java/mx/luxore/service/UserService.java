package mx.luxore.service;

import mx.luxore.dto.PageableDto;
import mx.luxore.dto.UsersDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> getUsers(PageableDto pag);

    ResponseEntity<?> getUser(Integer id);

    ResponseEntity<?> updateUser(UsersDto user);

    ResponseEntity<?> deleteUser(int id);

    ResponseEntity<?> createUser(UsersDto user);
}
