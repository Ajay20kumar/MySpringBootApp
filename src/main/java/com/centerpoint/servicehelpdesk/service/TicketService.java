package com.centerpoint.servicehelpdesk.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centerpoint.servicehelpdesk.dto.IdtticketDto;
import com.centerpoint.servicehelpdesk.dto.TicketDTO;
import com.centerpoint.servicehelpdesk.dto.TicketResponseDTO;
import com.centerpoint.servicehelpdesk.entity.Ticket;
import com.centerpoint.servicehelpdesk.entity.TicketResponses;
import com.centerpoint.servicehelpdesk.exception.ServiceException;

public interface TicketService {

	TicketDTO saveTicket(Ticket ticket) throws ServiceException;

	Page<TicketDTO> getTicket(Long userId, Long activeId, String searchText, Pageable pageable) throws ServiceException;

	List<TicketResponseDTO> getTicketResponseByTicketId(Long ticketId) throws ServiceException;

	Boolean saveTicketResponse(TicketResponses ticketResponses) throws ServiceException;

	List<Ticket> getdashticketCount(Long statusId, Long userId) throws ServiceException;

	Boolean closeTicketResponse(TicketResponses ticketResponses) throws ServiceException;

	Boolean openTicketResponse(TicketResponses ticketResponses) throws ServiceException;

	Page<TicketDTO> agentgetTicket(Long deptId, Long statusId, String searchText, Pageable pageable) throws ServiceException;

	List<TicketResponseDTO> getTicketResponseByUserTicketId(Long ticketId) throws ServiceException;

	Boolean resolvedTicketResponse(TicketResponses ticketResponses) throws ServiceException;

	Boolean selfassign(Long userId, Long ticketId) throws ServiceException;

	Boolean parked(Long userId, Long ticketId, Long parkedId, String pmassage) throws ServiceException;

	List<Ticket> getTicketDataByDate(Ticket ticket) throws ServiceException;

	Boolean peertopeer(TicketResponses ticketResponses) throws ServiceException;

	Boolean unparked(Long userId, Long ticketId) throws ServiceException;

	Boolean idttranfor(TicketResponses ticketResponses) throws ServiceException;

	Ticket getFile(Long fileId) throws ServiceException;

	IdtticketDto getTicketId(Long ticketId) throws ServiceException;

	Boolean retransfered(TicketResponses responses) throws ServiceException;

	long countByStatus(Date sdate1, Date tdate1) throws ServiceException;

	long countForOpenStatus(Date sdate1, Date tdate1) throws ServiceException;

	long countForClosedStatus(Date sdate1, Date tdate1)throws ServiceException;

	List getAllServiceCategoryCount(Date sdate1, Date tdate1) throws ServiceException;

	List getAgentWiseAllTickets(Date sdate1, Date tdate1) throws ServiceException;

	List getDepartmentOpenCount(Date sdate1, Date tdate1) throws ServiceException;

	List getDepartmentCloseCount(Date sdate1, Date tdate1) throws ServiceException;

	List getDepartmentAllCount(Date sdate1, Date tdate1) throws ServiceException;
}
