package com.bharath.powermock.useradminservice.dao;

import com.bharath.powermock.useradminservice.dto.User;
import com.bharath.powermock.useradminservice.util.IDGenerator;

public class UserDAO {

  public int create(User user) {
    int id = IDGenerator.generateID();

    // save the user object to the db...
    return id;
  }

}

