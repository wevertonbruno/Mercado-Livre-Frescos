package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.entities.AuthenticableUser;
import com.mercadolibre.grupo1.projetointegrador.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user from repository by throwing a exception if user is not found.
        AuthenticableUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado na base de dados."));
        return user;
    }
}
