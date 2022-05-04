package com.mercadolibre.grupo1.projetointegrador.services;

import com.mercadolibre.grupo1.projetointegrador.dtos.auth.*;
import com.mercadolibre.grupo1.projetointegrador.entities.AuthenticableUser;
import com.mercadolibre.grupo1.projetointegrador.entities.Customer;
import com.mercadolibre.grupo1.projetointegrador.entities.PasswordReset;
import com.mercadolibre.grupo1.projetointegrador.entities.Role;
import com.mercadolibre.grupo1.projetointegrador.exceptions.BadCredentialsException;
import com.mercadolibre.grupo1.projetointegrador.exceptions.UserRegistrationException;
import com.mercadolibre.grupo1.projetointegrador.repositories.PasswordResetRepository;
import com.mercadolibre.grupo1.projetointegrador.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Classe de servico de autenticacao
 * @author Weverton Bruno
 */
@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Value("${security.secret}")
    private String secret;

    @Transactional(readOnly = true)
    public TokenDTO login(LoginDTO login){
        //logica para autenticar usuario
        UserDetails user = userService.loadUserByUsername(login.getUsername());

        if(!passwordEncoder.matches(login.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Senha inválida!");
        }

        String token = jwtUtils.generateToken(user.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(user.getUsername());

        return new TokenDTO(token, refreshToken);
    }

    public TokenDTO refreshToken(String refreshToken) {
        if(!jwtUtils.isValidToken(refreshToken)){
            throw new UsernameNotFoundException("Refresh token inválido!");
        }

        String username = jwtUtils.getSubject(refreshToken);
        String token = jwtUtils.generateToken(username);

        return new TokenDTO(token, refreshToken);
    }

    /**
     * Pega o usuario logado do contextholder, busca no banco e faz o casting para o tipo escolhido
     * @param F
     * @return T
     * @param <T>
     * @author Weverton Bruno
     */
    @Transactional(readOnly = true)
    public <T> T getPrincipalAs(Class<T> F){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (T) userService.loadUserByUsername(principal.getUsername());
    }

    /**
     * Pega o usuario logado do contextholder, busca no banco e faz o casting para AuthenticableUser
     * @author Weverton Bruno
     */
    @Transactional(readOnly = true)
    public AuthenticableUser getPrincipal(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (AuthenticableUser) userService.loadUserByUsername(principal.getUsername());
    }

    @Transactional(readOnly = true)
    public ProfileDTO getProfile(){
        AuthenticableUser user = getPrincipal();
        return ProfileDTO.fromUser(user);
    }

    public ProfileDTO registerCustomer(RegisterDTO registerDTO) {
        AuthenticableUser user = createUser(registerDTO);
        Role role = roleService.findByName("ROLE_CUSTOMER");
        user.setRoles(Set.of(role));
        Customer customer = new Customer(user, registerDTO.getCpf());

        return ProfileDTO.fromUser(userService.save(customer));
    }

    private AuthenticableUser createUser(RegisterDTO registerDTO){
        AuthenticableUser userFound = userService.getByUsernameOrEmail(registerDTO.getUsername(), registerDTO.getEmail());
        if(userFound != null){
            throw new UserRegistrationException("Usuário e/ou E-mail já cadastrado!");
        }

        checkPassword(registerDTO.getPassword(), registerDTO.getPasswordConfirm());

        return AuthenticableUser.builder()
                .username(registerDTO.getUsername())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .build();
    }

    private void checkPassword(String password, String confirmPassword) {
        if(!password.equals(confirmPassword)){
            throw new UserRegistrationException("A senha de confirmação incorreta!");
        }

        if(password.length() < 6){
            throw new UserRegistrationException("A senha precisa ter pelo menos 6 caracteres.");
        }
    }

    @Transactional(readOnly = true)
    public void resetPasswordRequest(PasswordResetDTO resetDTO) {
        AuthenticableUser user = userService.findByEmail(resetDTO.getEmail());
        String token = jwtUtils.generateRefreshToken(user.getUsername());

        PasswordReset passwordReset = PasswordReset.builder().user(user).token(token).build();
        passwordResetRepository.save(passwordReset);

        emailService.sendEmail(
                user.getEmail(),
                "Alguém solicitou alteração de sua senha no Mercado Livre. Se foi você, click no link abaixo para redefinir sua senha.\n" +
                        "http://localhost:8080/api/v1/reset-password/verify?token=" + token);
    }

    @Transactional
    public void resetPassword(String resetToken, ChangePasswordDTO changePassword) {
        if(!jwtUtils.isValidToken(resetToken)){
            throw new BadCredentialsException("Token inválido!");
        }

        PasswordReset passwordReset = passwordResetRepository.findByToken(resetToken)
                .orElseThrow(() -> new BadCredentialsException("Token inválido!"));

        String username = jwtUtils.getSubject(resetToken);

        //Verifica a senha
        checkPassword(changePassword.getPassword(), changePassword.getPasswordConfirm());

        //Chama o service de usuário para alterar a senha
        userService.resetPassword(username, passwordEncoder.encode(changePassword.getPassword()));

        //Deleta do token
        passwordResetRepository.delete(passwordReset);
    }
}
