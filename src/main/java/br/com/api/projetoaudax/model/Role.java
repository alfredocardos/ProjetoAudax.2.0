package br.com.api.projetoaudax.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Role() {
    }

    public Role(Long role) {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
