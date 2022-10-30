package br.com.api.projetoaudax.repository;

import br.com.api.projetoaudax.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);
    @Query("SELECT u from Users u JOIN FETCH u.roles where u.username = :username ")
    Users dindByUsernameFetchRoles(@Param("username")String name);
}

