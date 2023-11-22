package com.soa.vie.takaful.entitymodels;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.validation.constraints.Email;

import com.soa.vie.takaful.util.AccountStateEnum;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE takaful_user SET status = 'DELETED' WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "status <> 'DELETED'")
public class TakafulUser extends AbstractBaseEntity {

  /**
   *
   */
  private static final long serialVersionUID = -4565971805385746136L;

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Email
  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String encryptePassword;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private List<Role> roles;

  @ManyToMany
  @JoinTable(name = "users_pointvente", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "Point_vente_id", referencedColumnName = "id"))
  private List<PointVente> pointVentes;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Agenda> events;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private AccountStateEnum status;

  @PreRemove
  public void deleteUser() {
    this.status = AccountStateEnum.DELETED;
  }
}
