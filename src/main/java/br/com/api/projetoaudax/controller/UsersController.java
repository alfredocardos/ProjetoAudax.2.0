package br.com.api.projetoaudax.controller;

import br.com.api.projetoaudax.dto.CreateUserRoleDTO;
import br.com.api.projetoaudax.dto.UsersDTO;
import br.com.api.projetoaudax.model.Users;
import br.com.api.projetoaudax.service.CreateRoleUserService;
import br.com.api.projetoaudax.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private CreateRoleUserService createRoleUserService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsersDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(usersService.findById(id), UsersDTO.class));
    }

    @GetMapping(value = "/username/{username}")
    public ResponseEntity<UsersDTO> findByUsername(@PathVariable String username){
        return ResponseEntity.ok().body(mapper.map(usersService.findByName(username), UsersDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UsersDTO>> findAll() {
        List<UsersDTO> listDTO = usersService.findAll().stream().map(x -> mapper.map(x, UsersDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<UsersDTO> create(@RequestBody @Valid UsersDTO obj) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usersService.create(obj).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UsersDTO> delete(@PathVariable Long id) {
        usersService.delete(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UsersDTO> update(@PathVariable Long id, @RequestBody @Valid UsersDTO obj){
        obj.setId(id);
        return ResponseEntity.ok().body(mapper.map(usersService.update(obj), UsersDTO.class));
    }

    @PostMapping("/role")
    public Users role(@RequestBody CreateUserRoleDTO createUserRoleDTO){
        return createRoleUserService.execute(createUserRoleDTO);
    }
}
