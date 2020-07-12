package com.centerpoint.servicehelpdesk.repoDao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centerpoint.servicehelpdesk.entity.Ticket;
import com.centerpoint.servicehelpdesk.entity.TicketResponses;
import com.centerpoint.servicehelpdesk.entity.User;

@Repository
public interface TicketResponseRepo extends JpaRepository<TicketResponses, Long> {

	//List<TicketResponses> findByTicket(Ticket ticket);

	List<TicketResponses> findByTicketAndUser(Ticket ticket, User user);

	List<TicketResponses> findByTicket(Ticket ticket, Sort sort);
	
	List<TicketResponses> findByPublishAndTicket(String publish, Ticket ticket,Sort sort);

	

}
