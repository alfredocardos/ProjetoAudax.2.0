package br.com.api.projetoaudax.service;

import br.com.api.projetoaudax.dto.UsersDTO;
import br.com.api.projetoaudax.model.Users;
import br.com.api.projetoaudax.repository.UsersRepository;
import br.com.api.projetoaudax.service.exceptions.DataIntegrityViolationException;
import br.com.api.projetoaudax.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private ModelMapper mapper;

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public Users create(UsersDTO obj) {
        findByUsername(obj);
      obj.setPassword(passwordEncoder().encode(obj.getPassword()));
        return usersRepository.save(mapper.map(obj, Users.class));
    }

    public Users findById(Long id) {
        Optional<Users> obj = usersRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado com a id: " + id));
    }

    public Users findByName(String username) {
        Optional<Users> obj = usersRepository.findByUsername(username);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado com o username: " + username));
    }

    public void delete(Long id) {
        findById(id);
        usersRepository.deleteById(id);

    }

    public Users update(UsersDTO obj) {
        findByUsername(obj);
        return usersRepository.save(mapper.map(obj, Users.class));
    }
    private void findByUsername(UsersDTO obj) {
        Optional<Users> user = usersRepository.findByUsername(obj.getUsername());
        if (user.isPresent() && !user.get().getId().equals(obj.getId())) {
            throw new DataIntegrityViolationException("Username já cadastrado no sistema");

        }
    }

    }



