package br.com.api.projetoaudax.controller;

import br.com.api.projetoaudax.dto.ArticlesDTO;
import br.com.api.projetoaudax.service.ArticlesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/articles")
public class ArticlesController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ArticlesService articlesService;
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<ArticlesDTO>> findAll() {
        List<ArticlesDTO> listDTO = articlesService.findAll().stream().map(x -> mapper.map(x, ArticlesDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ArticlesDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(articlesService.findById(id), ArticlesDTO.class));
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<ArticlesDTO> create(@RequestBody ArticlesDTO obj) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(articlesService.create(obj).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ArticlesDTO> delete(@PathVariable Long id) {
        articlesService.delete(id);
        return ResponseEntity.noContent().build();

    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ArticlesDTO> update(@PathVariable Long id, @RequestBody ArticlesDTO obj){
        obj.setId(id);
        return ResponseEntity.ok().body(mapper.map(articlesService.update(obj), ArticlesDTO.class));
    }
}
