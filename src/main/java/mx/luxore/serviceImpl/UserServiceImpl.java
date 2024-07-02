package mx.luxore.serviceImpl;

import mx.luxore.dto.PageableDto;
import mx.luxore.dto.UsersDto;
import mx.luxore.entity.CRoles;
import mx.luxore.entity.TUserRoles;
import mx.luxore.entity.TUsers;
import mx.luxore.exception.ExceptionGeneric;
import mx.luxore.exception.ResourceNotFoundException;
import mx.luxore.repositorywrapper.CRolesRepositoryWrapper;
import mx.luxore.repositorywrapper.TUsersRepositoryWrapper;
import mx.luxore.repositorywrapper.TUsersRolesRepositoryWrapper;
import mx.luxore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TUsersRepositoryWrapper usersRepositoryWrapper;

    @Autowired
    private TUsersRolesRepositoryWrapper usersRolesRepositoryWrapper;

    @Autowired
    private CRolesRepositoryWrapper rolesRepositoryWrapper;

    @Override
    public ResponseEntity<?> getUsers(PageableDto request) {
        Pageable pag = PageRequest.of(request.getPage(), request.getTotalPage());
        List<TUsers> usersList = usersRepositoryWrapper.findAll(pag).toList();
        List<UsersDto> urs = usersList.stream()
                .map(u -> UsersDto.builder()
                        .id(u.getId())
                        .username(u.getUsername())
                        .email(u.getEmail())
                        .enable(u.getEnable())
                        .role(u.getTUserRoles().stream()
                                .map(r -> r.getIdRole().getName())
                                .collect(Collectors.toSet()))
                        .build())
                .collect(Collectors.toList());
        return new ResponseEntity<>(urs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUser(Integer id) {
        Optional<TUsers> user = usersRepositoryWrapper.findById(id);
        if (user.isEmpty())
            throw new ResourceNotFoundException("Usuario", " ", " ", new Throwable("getUser()"), this.getClass().getName());

        Optional<UsersDto> us = user.stream()
                .map(u -> UsersDto.builder()
                        .id(u.getId())
                        .username(u.getUsername())
                        .email(u.getEmail())
                        .enable(u.getEnable())
                        .role(u.getTUserRoles().stream()
                                .map(r -> r.getIdRole().getName())
                                .collect(Collectors.toSet()))
                        .build()).findFirst();

        return new ResponseEntity<>(us.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateUser(UsersDto us) {
        Optional<TUsers> user = usersRepositoryWrapper.findById(us.getId());

        if (user.isEmpty())
            throw new ResourceNotFoundException("Usuario", " ", " ", new Throwable("updateUser()"), this.getClass().getName());

        if (!user.get().getUsername().equals(us.getUsername()) && usersRepositoryWrapper.findByUsername(us.getUsername()).isPresent())
            throw new ExceptionGeneric("Usuario existente", new Throwable("updateUser()"), this.getClass().getName());

        user.get().setEmail(us.getEmail());
        user.get().setEnable(us.getEnable());
        user.get().setUsername(us.getUsername());
        usersRolesRepositoryWrapper.delete(user.get().getTUserRoles());
        Set<TUserRoles> urs = this.getUserRoles(us.getRole(), user.get());
        usersRolesRepositoryWrapper.saveAll(urs);
        user.get().setTUserRoles(urs);
        usersRepositoryWrapper.save(user.get());
        return new ResponseEntity<>(us, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteUser(int id) {
        Optional<TUsers> user = usersRepositoryWrapper.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Usuario", " ", " ", new Throwable("deleteUser()"), this.getClass().getName());
        }
        usersRolesRepositoryWrapper.delete(user.get().getTUserRoles());
        usersRepositoryWrapper.delete(user.get());
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createUser(UsersDto user) {

        if (usersRepositoryWrapper.findByUsername(user.getUsername()).isPresent())
            throw new ExceptionGeneric("Usuario existente", new Throwable("createUser()"), this.getClass().getName());

        TUsers u = TUsers.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .enable(user.getEnable())
                .password(user.getPassword())
                .build();

        Set<TUserRoles> urs = this.getUserRoles(user.getRole(), u);
        u.setTUserRoles(urs);
        usersRepositoryWrapper.save(u);
        usersRolesRepositoryWrapper.saveAll(urs);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    protected Set<TUserRoles> getUserRoles(Set<Object> roles, TUsers u) {
        return roles.stream().map(r -> {
                    Optional<CRoles> role;
                    if (r instanceof String)
                        role = rolesRepositoryWrapper.findByName(String.valueOf(r));
                    else
                        role = rolesRepositoryWrapper.findById((Integer) r);

                    if (role.isEmpty())
                        throw new ResourceNotFoundException("Role", " ", " ", new Throwable("createUser()"), this.getClass().getName());
                    else
                        return TUserRoles.builder()
                                .idUser(u)
                                .idRole(role.get())
                                .build();
                }).
                collect(Collectors.toSet());
    }
}
