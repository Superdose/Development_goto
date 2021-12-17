package com.bharath.junit.spring.dao;

import com.bharath.junit.spring.dto.Ticket;

public interface TicketDAO {
  int createTicket(Ticket ticket);
}
