package br.com.api.projetoaudax.service;

import br.com.api.projetoaudax.dto.ArticlesDTO;
import br.com.api.projetoaudax.model.Articles;
import br.com.api.projetoaudax.repository.ArticlesRepository;
import br.com.api.projetoaudax.service.exceptions.DataIntegrityViolationException;
import br.com.api.projetoaudax.service.exceptions.ObjectNotFoundException;
import br.com.api.projetoaudax.service.slug.Slug;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticlesService {

    @Autowired
    ArticlesRepository articlesRepository;

    @Autowired
    private ModelMapper mapper;

    public List<Articles> findAll() {
        return articlesRepository.findAll();
    }
    public Articles create(ArticlesDTO articlesDTO) {
            articlesDTO.setId(null);
            articlesDTO.setTitle(Slug.toSlug(articlesDTO.getTitle()));
            findByTitle(articlesDTO);
        return articlesRepository.save(mapper.map(articlesDTO, Articles.class));
    }

    public Articles findById(Long id) {
        Optional<Articles> obj = articlesRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Artigo não encontrado com a id: " + id));
    }

    public void delete(Long id) {
        findById(id);
        articlesRepository.deleteById(id);

    }


    public Articles update (ArticlesDTO articlesDTO) {
            findByTitle(articlesDTO);
            return articlesRepository.save(mapper.map(articlesDTO, Articles.class));
         }


    private void findByTitle(ArticlesDTO obj) {
        Optional<Articles> articles = articlesRepository.findByTitle(obj.getTitle());
        if (articles.isPresent() && !articles.get().getId().equals(obj.getId())) {
            throw new DataIntegrityViolationException("Titulo já cadastrado no sistema");

        }
    }

    }

