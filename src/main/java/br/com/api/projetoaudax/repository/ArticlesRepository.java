package br.com.api.projetoaudax.repository;

import br.com.api.projetoaudax.model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Long> {

    Optional<Articles> findByTitle(String title);
}
