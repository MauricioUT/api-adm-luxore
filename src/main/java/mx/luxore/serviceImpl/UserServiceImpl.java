package mx.luxore.serviceImpl;

import mx.luxore.dto.PageableDto;
import mx.luxore.dto.UsersDto;
import mx.luxore.entity.TUsers;
import mx.luxore.exception.ResourceNotFoundException;
import mx.luxore.repositorywrapper.TUsersRepositoryWrapper;
import mx.luxore.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TUsersRepositoryWrapper usersRepositoryWrapper;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> getUsers(PageableDto request) {
        Pageable pag = PageRequest.of(request.getPage(), request.getTotalPage());
        List<TUsers> users = modelMapper.map(usersRepositoryWrapper.findAll(pag), new TypeToken<List<TUsers>>() {
        }.getType());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUser(Integer id) {
        Optional<TUsers> user = usersRepositoryWrapper.findById(id);

        UsersDto us = modelMapper.map(user.orElseThrow(() ->
                new ResourceNotFoundException("Usuario", " ", " ", new Throwable("getPropertyById()"), this.getClass().getName())
        ), UsersDto.class);

        return new ResponseEntity<>(us, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateUser(UsersDto us) {
        Optional<TUsers> user = usersRepositoryWrapper.findById(us.getId());
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Usuario", " ", " ", new Throwable("updateUser()"), this.getClass().getName());
        }
        user.get().setEmail(us.getEmail());
        user.get().setEnable(us.getEnable());
        user.get().setUsername(us.getUsername());
        usersRepositoryWrapper.save(user.get());
        return new ResponseEntity<>(us, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteUser(int id) {
        Optional<TUsers> user = usersRepositoryWrapper.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Usuario", " ", " ", new Throwable("deleteUser()"), this.getClass().getName());
        }
        usersRepositoryWrapper.delete(user.get());
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createUser(UsersDto user) {
        
        TUsers u =  TUsers.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .enable(user.getEnable())
                .password(user.getPassword())
                .build();
        usersRepositoryWrapper.save(u);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }
}
