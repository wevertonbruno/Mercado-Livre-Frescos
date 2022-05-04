package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.auth.ChangePasswordDTO;
import com.mercadolibre.grupo1.projetointegrador.entities.AuthenticableUser;
import com.mercadolibre.grupo1.projetointegrador.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public AuthenticableUser getByUsernameOrEmail(String username, String email){
        return userRepository.findByUsernameOrEmail(username, email).orElse(null);
    }

    public AuthenticableUser findByEmail(String email){ return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("E-mail não encontrado na base de dados.")); }

    public AuthenticableUser save(AuthenticableUser user){
        return userRepository.save(user);
    }

    public AuthenticableUser findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));
    }

    public void resetPassword(String username, String password) {
        AuthenticableUser user = findByUsername(username);
        user.setPassword(password);
        userRepository.save(user);
    }
}
