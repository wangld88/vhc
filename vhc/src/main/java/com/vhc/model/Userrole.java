package com.vhc.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@Entity
@Table(name="USER_ROLE")
@ApiObject
public class Userrole
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @SequenceGenerator(name="USERROLE_USRLID_GENERATOR", sequenceName="USRL_SEQ")
  @GeneratedValue(generator="USERROLE_USRLID_GENERATOR")
  private long usrlid;
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="userid")
  @ApiObjectField(description="Unique User", format="Not Null, maxlength = 30", required=true)
  private User user;
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="roleid")
  @ApiObjectField(description="Unique Role", format="Not Null, maxlength = 30", required=false)
  private Role role;
  
  public User getUser()
  {
    return this.user;
  }
  
  public void setUser(User user)
  {
    this.user = user;
  }
  
  public Role getRole()
  {
    return this.role;
  }
  
  public void setRole(Role role)
  {
    this.role = role;
  }
}
