package com.bharath.junit.spring.service;

import static org.junit.Assert.*;

import com.bharath.junit.spring.dao.TicketDAO;
import com.bharath.junit.spring.dto.Ticket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// !!!JUNIT4!!!
// Class for Testing always end with "Test"
// placed inside the test directory
// and corresponding to the class to be tested inside the "same" qualified package
// @RunWith(SpringJUnit4ClassRunner.class)
// -> Indicates that the class should use Spring's JUnit facilities
// @ContextConfiguration(locations = "classpath:application-context.xml")
// -> Indicates which XML files contain the ApplicationContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")
public class TicketServiceImplTest {

  public static final String PASSENGER_NAME = "Bharath";
  public static final String PHONE_NUMBER = "1234567890";
  public static final int RESULT = 1;

  // @Mock marks the field to be mocked
  @Mock
  private TicketDAO ticketDAO;

  // @InjectMocks injects all mocked fields, that exist inside the @InjectMocks-field
  @Autowired
  @InjectMocks
  private TicketService ticketService;

  // @Before marks this method to be called before each test method once
  @Before
  public void setup() {

    // MockitoAnnotations.initMocks(this); enables the Mockito specific Annotations inside
    // this class
    MockitoAnnotations.initMocks(this);
  }

  // @Test marks the method as a test method
  @Test
  public void buyTicketsShouldReturnAValidValue() {

    // Mocks the given method
    // and returns for the corresponding call the specified return value
    Mockito.when(this.ticketDAO.createTicket(Mockito.any(Ticket.class))).thenReturn(1);

    int result = this.ticketService.buyTicket(PASSENGER_NAME, PHONE_NUMBER);

    // checks whether the expected and given value are the "same"
    assertEquals(RESULT, result);
  }

}