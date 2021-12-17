package com.bharath.powermock.useradminservice.dao;

import static org.junit.Assert.*;

import static org.powermock.api.mockito.PowerMockito.*;

import com.bharath.powermock.useradminservice.dto.User;
import com.bharath.powermock.useradminservice.util.IDGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

// !!! Powermock is outdated !!!
// !! JUNIT4 !!
// @RunWith(PowerMockRunner.class) enables powermock in this class
// @PrepareForTest(IDGenerator.class) prepares the specified classes for mocking via powermock
@RunWith(PowerMockRunner.class)
@PrepareForTest(IDGenerator.class)
public class UserDAOTest {

  // @Test marks the method as a test method
  @Test
  public void createShouldReturnAUserId() {
    UserDAO userDAO = new UserDAO();

    // allows mocking static methods of the given class
    mockStatic(IDGenerator.class);

    // Mocks the given static method
    // and returns for the corresponding call the specified return value
    when(IDGenerator.generateID()).thenReturn(1);

    int result = userDAO.create(new User());

    // checks whether the expected and given value are the "same"
    assertEquals(1, result);

    // VerifyStatic checks whether the specified static method was called during the test
    verifyStatic(IDGenerator.class);
  }

}