package br.com.api.projetoaudax.service;


import br.com.api.projetoaudax.dto.CreateUserRoleDTO;
import br.com.api.projetoaudax.model.Role;
import br.com.api.projetoaudax.model.Users;
import br.com.api.projetoaudax.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreateRoleUserService {

  @Autowired
  UsersRepository userRepository;

  public Users execute(CreateUserRoleDTO createUserRoleDTO) {

    Optional<Users> userExists = userRepository.findById(createUserRoleDTO.getIdUser());
    List<Role> roles = new ArrayList<>();

    if (userExists.isEmpty()) {
      throw new Error("User does not exists!");
    }

    roles = createUserRoleDTO.getIdsRoles().stream().map(role -> {
      return new Role(role);
    }).collect(Collectors.toList());

    Users user = userExists.get();

    user.setRoles(roles);

    userRepository.save(user);

    return user;

  }
}
