package br.com.api.projetoaudax.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "tb_articles")
public class Articles implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private UUID uuid = UUID.randomUUID();


    @NotBlank(message = "O campo TITLE é obrigatório!")
    @Size(min = 30, max = 70, message = "O campo title tem que ter no mínimo 30 e no máximo 70 caracters")
    @Column(unique = true, nullable = false)
    private String title;

    @NotBlank(message = "O campo RESUME é obrigatório!")
    @Size(min = 50, max = 100, message = "O campo resume tem que ter no mínimo 50 e no máximo 100 caracters")
    @Column(nullable = false)
    private String resume;
    @NotBlank(message = "O campo TEXT é obrigatório!")
    @Size(min = 200, message = "O campo text tem que ter no mínimo 200 caracters")
    @Column(nullable = false)
    private String text;

    private String slug;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime registeredAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    public Articles() {
    }

    public Articles(Long id, UUID uuid, String title, String resume, String text, String slug, LocalDateTime registeredAt, Users users) {
        this.id = id;
        this.uuid = uuid;
        this.title = title;
        this.resume = resume;
        this.text = text;
        this.slug = slug;
        this.registeredAt = registeredAt;
        this.users = users;
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



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }



    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
