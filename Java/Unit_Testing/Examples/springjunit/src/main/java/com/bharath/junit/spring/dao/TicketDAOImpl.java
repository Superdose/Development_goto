package com.bharath.junit.spring.dao;

import com.bharath.junit.spring.dto.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketDAOImpl implements TicketDAO{

  @Override
  public int createTicket(Ticket ticket) {
    return 1;
  }
}
