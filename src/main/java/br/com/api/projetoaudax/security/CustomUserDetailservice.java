package br.com.api.projetoaudax.security;

import br.com.api.projetoaudax.model.Users;
import br.com.api.projetoaudax.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class CustomUserDetailservice implements UserDetailsService {
    @Autowired
    UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users byusername1 = usersRepository.dindByUsernameFetchRoles(username);
        if (byusername1 != null){
            return (UserDetails) new Error("Usuário nao existi!");
        }
        return UserPrincipal.create(byusername1);
    }

    }

