package mx.luxore.security.service;

import mx.luxore.entity.TUsers;
import mx.luxore.repositorywrapper.TUsersRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private TUsersRepositoryWrapper usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUsers user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario ".concat(username).concat(" No existe")));

        Collection<? extends GrantedAuthority> authorities = user.getTUserRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_".concat(r.getIdRole().getName())))
                .collect(Collectors.toSet());

        return new User(user.getUsername(),
                user.getPassword(),
                authorities);
    }
}
