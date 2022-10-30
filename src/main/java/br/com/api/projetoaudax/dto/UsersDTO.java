package br.com.api.projetoaudax.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


public class UsersDTO implements Serializable {

    private Long id;

    private UUID uuid =UUID.randomUUID() ;

    @NotBlank(message = "O campo USERNAME é requerido")
    @Size(min = 3, max = 150, message = "O campo username tem que ter no mínimo 3 e no máximo 150 caracters")
    private String username;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "O campo PASSWORD é requerido")
    @Size(min = 8, message = "O campo password tem que ter no mínimo 8 caracters")
    private String password;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime registeredAt = LocalDateTime.now();

    public UsersDTO() {

    }

    public UsersDTO(Long id, UUID uuid, String username, String password, LocalDateTime registeredAt) {
        this.id = id;
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.registeredAt = registeredAt;
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

}
