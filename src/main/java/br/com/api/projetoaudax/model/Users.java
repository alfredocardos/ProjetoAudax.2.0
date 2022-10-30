package br.com.api.projetoaudax.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Entity
@Table(name = "tb_users")
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid = UUID.randomUUID();
    @NotBlank(message = "O campo USERNAME é requerido")
    @Size(min = 3, max = 150, message = "O campo username tem que ter no mínimo 3 e no máximo 150 caracters")
    @Column(length = 150, unique = true)
    private String username;

    @NotBlank(message = "O campo PASSWORD é requerido")
    @Size(min = 8, message = "O campo password tem que ter no mínimo 8 caracters")
    private String password;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime registeredAt = LocalDateTime.now();
    @JsonIgnore
    @OneToMany(mappedBy = "users")
    private List<Articles> articles = new ArrayList<>();
    @ManyToMany
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Users() {
    }


    public Users(Long id, UUID uuid, String username, String password, LocalDateTime registeredAt, List<Articles> articles) {
        this.id = id;
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.registeredAt = registeredAt;
        this.articles = articles;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }



    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }
}
