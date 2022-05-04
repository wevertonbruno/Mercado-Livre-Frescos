package com.mercadolibre.grupo1.projetointegrador.repositories;

import com.mercadolibre.grupo1.projetointegrador.entities.AuthenticableUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AuthenticableUser, Long> {
    Optional<AuthenticableUser> findByUsername(String username);
    Optional<AuthenticableUser> findByUsernameOrEmail(String username, String email);
    Optional<AuthenticableUser> findByEmail(String email);
    @Query("SELECT new AuthenticableUser(u) FROM AuthenticableUser u WHERE id = :id")
    Optional<AuthenticableUser> findUserById(Long id);
}
