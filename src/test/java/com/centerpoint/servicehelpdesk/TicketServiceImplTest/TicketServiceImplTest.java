package com.centerpoint.servicehelpdesk.TicketServiceImplTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.centerpoint.ServiceHelpDeskApplication;
import com.centerpoint.servicehelpdesk.dto.TicketDTO;
import com.centerpoint.servicehelpdesk.dto.TicketResponseDTO;
import com.centerpoint.servicehelpdesk.entity.DepartmentEntity;
import com.centerpoint.servicehelpdesk.entity.Ticket;
import com.centerpoint.servicehelpdesk.entity.TicketResponses;
import com.centerpoint.servicehelpdesk.exception.ServiceException;
import com.centerpoint.servicehelpdesk.repoDao.TicketRepo;
import com.centerpoint.servicehelpdesk.repoDao.TicketResponseRepo;
import com.centerpoint.servicehelpdesk.repoDao.UserRepo;
import com.centerpoint.servicehelpdesk.serviceImpl.TicketServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT,
   classes=ServiceHelpDeskApplication.class)
public class TicketServiceImplTest {
	@InjectMocks
	TicketServiceImpl ticketServiceImpl;
	@MockBean
	TicketRepo ticketRepo;
	@MockBean
	TicketResponseRepo ticketResponseRepo;
	@MockBean
	UserRepo userRepo;
	@MockBean
	ModelMapper mapper;
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
	}
	@Test
	public void saveTicket() throws ServiceException {
		List<TicketResponses> list=new ArrayList<TicketResponses>();
		TicketResponses response=new TicketResponses();
		response.setResponseId(1l);
		list.add(response);
		Ticket t=new Ticket();
		t.setTicketId(1l);
		t.setMessage("1st ticket");
		t.setTicketResponses(list);
		DepartmentEntity d1=new DepartmentEntity();
		d1.setDepartmentId(1l);
		t.setDepartmentEntity1(d1);
		t.setDepartmentEntity2(d1);
		TicketDTO tdto=new TicketDTO();
		tdto.setTicketId(1l);
		tdto.setMessage("1st ticket");
		
		when(mapper.map(Mockito.any(Ticket.class), Mockito.any(Class.class))).thenReturn(tdto);
		when(ticketRepo.save(Mockito.any(Ticket.class))).thenReturn(t);
		TicketDTO saveTicket = ticketServiceImpl.saveTicket(t);
		assertEquals(Long.valueOf(1), saveTicket.getTicketId());
		
		
	}
	@Test
	public void getTicketResponseByTicketIdTest() throws ServiceException {
		
		Ticket t=new Ticket();
		t.setTicketId(1l);
		t.setTdate("2018-09-11");
		List<TicketResponses> list=new ArrayList<TicketResponses>();	
		TicketResponses response1=new TicketResponses();
		response1.setResponseId(1L);
		response1.setRating(2);
		response1.setTicket(t);
		TicketResponseDTO response=new TicketResponseDTO();
		response.setResponseId(1L);
		response.setRating(2);
		list.add(response1);
		ArgumentCaptor<Sort> sortArgument = ArgumentCaptor.forClass(Sort.class);
		when(mapper.map(Mockito.any(TicketResponses.class), Mockito.any(Class.class))).thenReturn(response);
		when(ticketResponseRepo.findByTicket(t, Mockito.any(Sort.class))).thenReturn(list);
		List<TicketResponseDTO> ticketResponseByTicketIdlist = ticketServiceImpl.getTicketResponseByTicketId(1l);
		assertEquals(0,ticketResponseByTicketIdlist.size());
	}
	
	@Test
	public void saveTicketResponseTest() throws ServiceException {
		List<TicketResponses> list=new ArrayList<TicketResponses>();	
		Ticket t=new Ticket();
		t.setTicketId(1l);
		t.setTdate("2018-09-11");
		TicketResponses response1=new TicketResponses();
		response1.setResponseId(1L);
		response1.setRating(2);
		response1.setTicket(t);
		list.add(response1);
		t.setTicketResponses(list);
		when(ticketRepo.getOne(response1.getTicket().getTicketId())).thenReturn(t);
		when(ticketResponseRepo.save(response1)).thenReturn(response1);
		Boolean saveTicketResponse = ticketServiceImpl.saveTicketResponse(response1);
		assertEquals(Boolean.valueOf(true),saveTicketResponse);
	}

}
