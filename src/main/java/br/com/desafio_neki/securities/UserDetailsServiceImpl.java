package br.com.desafio_neki.securities;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.desafio_neki.models.User;
import br.com.desafio_neki.repositories.UserRepository;

// O UserDetailService e usado para recuperar os detalhes do usuario que esta tentando se autenticar
// na aplicacao. Isso e feito atraves do metodo loadUserByUsername.
// Se o usuario nao for encontrado e disparada uma excecao do tipo UsernameNotFoundException

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired 
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        // Optional<User> userRes = userRepo.findByUserEmail(email);
        Optional<User> userRes = userRepo.findByLogin(userLogin);
        if(userRes.isEmpty())
            throw new UsernameNotFoundException("Não foi possível encontrar usuário com o login = " + userLogin);

        User user = userRes.get();
        return new org.springframework.security.core.userdetails.User(
                userLogin,
                user.getUserPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))); // Define, de forma estatica, o perfil do usuario encontrado
    }
}