package hu.triszt4n.minijira.service;

import hu.triszt4n.minijira.repository.UserRepository;
import hu.triszt4n.minijira.util.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var userEntity = userRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username: not found"));
        return new MyUserPrincipal(userEntity);
    }
}
