package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.entities.Agent;
import com.mercadolibre.grupo1.projetointegrador.entities.AuthenticableUser;
import com.mercadolibre.grupo1.projetointegrador.entities.Customer;
import com.mercadolibre.grupo1.projetointegrador.entities.Seller;
import com.mercadolibre.grupo1.projetointegrador.exceptions.EntityNotFoundException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.InvalidOperationException;
import com.mercadolibre.grupo1.projetointegrador.repositories.AgentRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.CustomerRepository;
import com.mercadolibre.grupo1.projetointegrador.repositories.SellerRepository;
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
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private SellerRepository sellerRepository;

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

    public AuthenticableUser findUserById(Long id){
        return userRepository.findUserById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com ID "+ id +" não encontrado na base de dados."));
    }

    public <T> T findUserById(Long id, Class<T> type){
        final String ERROR_MESSAGE = "Usuário do tipo " + type.getSimpleName() + " com ID " + id + " não encontrado!";

        try {
            if (type.equals(Agent.class))
                return (T) agentRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE));

            if (type.equals(Customer.class))
                return (T) customerRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE));

            if (type.equals(Seller.class))
                return (T) sellerRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE));
        }catch (ClassCastException e){
            throw new InvalidOperationException("Tipo de conversão nao permitida!");
        }

        return null;
    }

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }
    public void resetPassword(String username, String password) {
        AuthenticableUser user = findByUsername(username);
        user.setPassword(password);
        userRepository.save(user);
    }
}
