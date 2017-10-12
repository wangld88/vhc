package com.vhc.repository;

import com.vhc.model.User;
import org.springframework.data.repository.CrudRepository;

public abstract interface UserRepository
  extends CrudRepository<User, Long>
{
  public abstract User findByUsername(String paramString);
}
