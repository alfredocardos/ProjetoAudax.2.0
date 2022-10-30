package br.com.api.projetoaudax.dto;

import java.util.List;


public class CreateUserRoleDTO {

  private Long idUser;

  private List<Long> idsRoles;

  public Long getIdUser() {
    return idUser;
  }

  public void setIdUser(Long idUser) {
    this.idUser = idUser;
  }

  public List<Long> getIdsRoles() {
    return idsRoles;
  }

  public void setIdsRoles(List<Long> idsRoles) {
    this.idsRoles = idsRoles;
  }
}
