package com.centerpoint.servicehelpdesk.serviceImpl;

import static org.springframework.data.jpa.domain.Specification.where;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.centerpoint.servicehelpdesk.dto.IdtticketDto;
import com.centerpoint.servicehelpdesk.dto.TicketDTO;
import com.centerpoint.servicehelpdesk.dto.TicketResponseDTO;
import com.centerpoint.servicehelpdesk.entity.DepartmentEntity;
import com.centerpoint.servicehelpdesk.entity.Stages;
import com.centerpoint.servicehelpdesk.entity.Status;
import com.centerpoint.servicehelpdesk.entity.Ticket;
import com.centerpoint.servicehelpdesk.entity.TicketResponses;
import com.centerpoint.servicehelpdesk.entity.User;
import com.centerpoint.servicehelpdesk.exception.ServiceException;
import com.centerpoint.servicehelpdesk.repoDao.DepartmentRepo;
import com.centerpoint.servicehelpdesk.repoDao.TicketRepo;
import com.centerpoint.servicehelpdesk.repoDao.TicketResponseRepo;
import com.centerpoint.servicehelpdesk.repoDao.UserRepo;
import com.centerpoint.servicehelpdesk.service.TicketService;
import com.centerpoint.servicehelpdesk.specifications.TicketSpec;
import com.centerpoint.servicehelpdesk.util.BeanCollectionMapper;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepo ticketRepo;

	@Autowired
	private TicketResponseRepo ticketResponseRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private DepartmentRepo departmentRepo;

	private static Logger logger = LogManager.getLogger(TicketServiceImpl.class);

	private TicketDTO convertToObjectDto(Ticket o) {
		ModelMapper mapper = new ModelMapper();
		TicketDTO dto = mapper.map(o, TicketDTO.class);
		return dto;
	}

	@Override
	public TicketDTO saveTicket(Ticket ticket) throws ServiceException {
		TicketDTO map = null;
		try {
			ticket.setDepartmentEntity1(ticket.getDepartmentEntity());
			ticket.setDepartmentEntity2(ticket.getDepartmentEntity());
			ticket.getTicketResponses().forEach(ticketResponses -> {
				ticketResponses.setTicket(ticket);
				ticketResponses.setDepartmentEntity1(ticket.getDepartmentEntity());
				ticketResponses.setDepartmentEntity2(ticket.getDepartmentEntity());
			});
			Ticket ticket1 = ticketRepo.save(ticket);
			 map = mapper.map(ticket1, TicketDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the saveTicket Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return map;
	}

	@Override
	public Page<TicketDTO> getTicket(Long userId, Long statusId, String searchText, Pageable pageable) throws ServiceException {
		Page<TicketDTO> s = null;
		try {
			Page<Ticket> p = ticketRepo.findAll(where(TicketSpec.getuser(userId)).and(TicketSpec.getstatus(statusId))
					.and(TicketSpec.searchName(searchText)), pageable);
			s = p.map(this::convertToObjectDto);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getTicket Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return s;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<TicketResponseDTO> getTicketResponseByTicketId(Long ticketId) throws ServiceException {
		List<TicketResponseDTO> list1 = null;
		try {
			Ticket ticket = new Ticket();
			ticket.setTicketId(ticketId);
			List<TicketResponses> list = ticketResponseRepo.findByTicket(ticket, Sort.by("responseId").descending());
			 list1 = BeanCollectionMapper.mapList(mapper, list, TicketResponseDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getTicketResponseByTicketId Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return list1;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Boolean saveTicketResponse(TicketResponses ticketResponses) throws ServiceException {
		Boolean b = false;
		try {
			Ticket ticket = ticketRepo.getOne(ticketResponses.getTicket().getTicketId());
			List<TicketResponses> ticketResponses2 = ticket.getTicketResponses();
			ticketResponses
					.setDepartmentEntity1(ticketResponses2.get(ticketResponses2.size() - 1).getDepartmentEntity1());
			ticketResponses
					.setDepartmentEntity2(ticketResponses2.get(ticketResponses2.size() - 1).getDepartmentEntity2());
			ticketResponseRepo.save(ticketResponses);
			b = true;
		} catch (Exception e) {
			logger.debug("Unable to fetch the saveTicketResponse Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return b;
	}

	@Override
	public Boolean closeTicketResponse(TicketResponses ticketResponses) throws ServiceException {
		Boolean b = false;
		try {
			Ticket ticket2 = ticketRepo.getOne(ticketResponses.getTicket().getTicketId());
			Status s = new Status();
			s.setStatusId(2l);
			ticket2.setStatus(s);
			ticketRepo.save(ticket2);
			List<TicketResponses> ticketResponses2 = ticket2.getTicketResponses();
			ticketResponses
					.setDepartmentEntity1(ticketResponses2.get(ticketResponses2.size() - 1).getDepartmentEntity1());
			ticketResponses
					.setDepartmentEntity2(ticketResponses2.get(ticketResponses2.size() - 1).getDepartmentEntity2());
			ticketResponseRepo.save(ticketResponses);
			b = true;
		} catch (Exception e) {
			logger.debug("Unable to fetch the closeTicketResponse Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return b;
	}

	@Override
	public Boolean openTicketResponse(TicketResponses ticketResponses) throws ServiceException {
		Boolean b = false;
		try {
			Ticket ticket2 = ticketRepo.getOne(ticketResponses.getTicket().getTicketId());
			Stages stages = new Stages();
			stages.setStagesId(1l);
			Status s = new Status();
			s.setStatusId(1l);
			ticket2.setStatus(s);
			ticket2.setStages(stages);
			ticket2.setUser1(null);
			ticketRepo.save(ticket2);
			List<TicketResponses> ticketResponses2 = ticket2.getTicketResponses();
			ticketResponses.setDepartmentEntity1(ticketResponses2.get(0).getDepartmentEntity1());
			ticketResponses.setDepartmentEntity2(ticketResponses2.get(0).getDepartmentEntity2());
			ticketResponseRepo.save(ticketResponses);
			b = true;
		} catch (Exception e) {
			logger.debug("Unable to fetch the openTicketResponse Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return b;
	}

	@Override
	public List<Ticket> getdashticketCount(Long statusId, Long userId) throws ServiceException {
		List<Ticket> list = null;
		try {
			Status status = new Status();
			status.setStatusId(statusId);
			User user = new User();
			user.setUserId(userId);
			list = ticketRepo.findAllByUserAndStatus(user, status);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getdashticketCount Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return list;
	}

	@Override
	public Page<TicketDTO> agentgetTicket(Long deptId, Long statusId, String searchText, Pageable pageable) throws ServiceException {
		Page<TicketDTO> s = null;
		try {
			Page<Ticket> p = ticketRepo
					.findAll(
							where((TicketSpec.getDepartment(deptId)).or(TicketSpec.getDepartment2(deptId)))
									.and(TicketSpec.getstatus(statusId)).and(TicketSpec.searchName(searchText)),
							pageable);
			s = p.map(this::convertToObjectDto);
		} catch (Exception e) {
			logger.debug("Unable to fetch the agentgetTicket Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return s;
	}

	@Override
	public List<TicketResponseDTO> getTicketResponseByUserTicketId(Long ticketId) throws ServiceException {
		List<TicketResponseDTO> list1 = null;
		try {
			Ticket ticket = new Ticket();
			ticket.setTicketId(ticketId);
			String publish = "pb";
			List<TicketResponses> list = ticketResponseRepo.findByPublishAndTicket(publish, ticket, Sort.by("responseId").descending());
			list1 = BeanCollectionMapper.mapList(mapper, list, TicketResponseDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the  getTicketResponseByUserTicketId Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return list1;
	}

	@Override
	public Boolean resolvedTicketResponse(TicketResponses ticketResponses) throws ServiceException {
		Boolean b = false;
		try {
			Ticket ticket2 = ticketRepo.getOne(ticketResponses.getTicket().getTicketId());
			Stages stages = new Stages();
			stages.setStagesId(4l);
			ticket2.setStages(stages);
			ticketRepo.save(ticket2);
			Long userId = ticketResponses.getUser().getUserId();
			User user = userRepo.getOne(userId);
			String rmessage = ticketResponses.getRmessage() + " By " + user.getUserName() + " " + user.getUsersurName();
			ticketResponses.setRmessage(rmessage);
			List<TicketResponses> ticketResponses2 = ticket2.getTicketResponses();
			ticketResponses
					.setDepartmentEntity1(ticketResponses2.get(ticketResponses2.size() - 1).getDepartmentEntity1());
			ticketResponses
					.setDepartmentEntity2(ticketResponses2.get(ticketResponses2.size() - 1).getDepartmentEntity2());
			ticketResponseRepo.save(ticketResponses);
			b = true;
		} catch (Exception e) {
			logger.debug("Unable to fetch the resolvedTicketResponse Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return b;
	}

	@Override
	public synchronized Boolean selfassign(Long userId, Long ticketId) throws ServiceException{
		Boolean b = false;
		try {
				Ticket ticket = ticketRepo.getOne(ticketId);
				if (ticket.getStages().getStagesId() == 1) {
					User user = new User();
					user.setUserId(userId);
					ticket.setUser1(user);
					Stages stages = new Stages();
					stages.setStagesId(2l);
					ticket.setStages(stages);
					ticketRepo.save(ticket);
					User user1 = userRepo.getOne(userId);
					List<TicketResponses> ticketResponses = ticket.getTicketResponses();
					TicketResponses ticketResponses2 = ticketResponses.get(0);
					TicketResponses trs = new TicketResponses();
					trs.setActive(ticketResponses2.getActive());
					trs.setPublish(ticketResponses2.getPublish());
					trs.setResponseDate(new Date());
					trs.setStatus(ticketResponses2.getStatus());
					trs.setTicket(ticketResponses2.getTicket());
					trs.setUser(user);
					trs.setDepartmentEntity1(ticketResponses2.getDepartmentEntity1());
					trs.setDepartmentEntity2(ticketResponses2.getDepartmentEntity2());
					String rmessage = "Ticket Assigned To " + user1.getUserName() + " " + user1.getUsersurName();
					trs.setRmessage(rmessage);
					ticketResponseRepo.save(trs);
					b = true;
			}
		} catch (Exception e) {
			logger.debug("Unable to fetch the selfassign Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return b;
	}

	@Override
	public Boolean parked(Long userId, Long ticketId, Long parkedId, String pmassage) throws ServiceException {
		Boolean b  = false;
		try {
			Ticket ticket = ticketRepo.getOne(ticketId);
			User user = new User();
			user.setUserId(userId);
			Stages stages = new Stages();
			stages.setStagesId(3l);
			ticket.setStages(stages);
			ticketRepo.save(ticket);
			User user1 = userRepo.getOne(userId);
			List<TicketResponses> ticketResponses = ticket.getTicketResponses();
			TicketResponses ticketResponses2 = ticketResponses.get(ticketResponses.size() - 1);
			TicketResponses trs = new TicketResponses();
			trs.setActive(ticketResponses2.getActive());
			trs.setPublish(ticketResponses2.getPublish());
			trs.setResponseDate(new Date());
			trs.setStatus(ticketResponses2.getStatus());
			trs.setTicket(ticketResponses2.getTicket());
			trs.setParkedId(parkedId);
			trs.setDepartmentEntity1(ticketResponses2.getDepartmentEntity1());
			trs.setDepartmentEntity2(ticketResponses2.getDepartmentEntity2());
			trs.setUser(user);
			String rmessage = null;
			if (pmassage == null) {
				rmessage = "Parked By " + user1.getUserName() + " " + user1.getUsersurName();
			} else {
				rmessage = "Parked By " + user1.getUserName() + " " + user1.getUsersurName() + "(" + pmassage + ")";
			}
			trs.setRmessage(rmessage);
			ticketResponseRepo.save(trs);
			b = true;
		} catch (Exception e) {
			logger.debug("Unable to fetch the parked Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return b;
	}

	@Override
	public List<Ticket> getTicketDataByDate(Ticket ticket) throws ServiceException {
		List<Ticket> list = null;
		try {
			TicketSpec spec = new TicketSpec(ticket);
			list = ticketRepo.findAll(spec, Sort.by("ticketId").ascending());
		} catch (Exception e) {
			logger.debug("Unable to fetch the getTicketDataByDate Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return list;
	}

	@Override
	public Boolean peertopeer(TicketResponses ticketResponses) throws ServiceException {
		Boolean b = false;
		try {
			Ticket ticket = ticketRepo.getOne(ticketResponses.getTicket().getTicketId());
			ticket.setUser1(ticketResponses.getUser());
			ticketRepo.save(ticket);
			User user = userRepo.getOne(ticketResponses.getActive().getActiveId());
			User u = userRepo.getOne(ticketResponses.getUser().getUserId());
			List<TicketResponses> ticketResponses2 = ticket.getTicketResponses();
			TicketResponses responses = ticketResponses2.get(ticketResponses2.size() - 1);
			ticketResponses.setPublish("pt");
			ticketResponses.setActive(responses.getActive());
			ticketResponses.setStatus(responses.getStatus());
			ticketResponses.setDepartmentEntity1(responses.getDepartmentEntity1());
			ticketResponses.setDepartmentEntity2(responses.getDepartmentEntity2());
			String message = null;
			if (ticketResponses.getTmessage() != null) {
				message = "Ticket Transfered From " + user.getUserName() + " " + user.getUsersurName() + " To "
						+ u.getUserName() + " " + u.getUsersurName() + "(" + ticketResponses.getTmessage() + ")";
			} else {
				message = "Ticket Transfered From " + user.getUserName() + " " + user.getUsersurName() + " To "
						+ u.getUserName() + " " + u.getUsersurName();
			}
			ticketResponses.setRmessage(message);
			ticketResponseRepo.save(ticketResponses);
			b = true;
		} catch (Exception e) {
			logger.debug("Unable to fetch the peertopeer Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return b;
	}

	@Override
	public Boolean unparked(Long userId, Long ticketId) throws ServiceException {
		Boolean b = false;
		try {
			Ticket ticket = ticketRepo.getOne(ticketId);
			Stages stages = new Stages();
			stages.setStagesId(2l);
			ticket.setStages(stages);
			ticketRepo.save(ticket);
			List<TicketResponses> ticketResponses = ticket.getTicketResponses();
			TicketResponses ticketResponses2 = ticketResponses.get(ticketResponses.size() - 1);
			TicketResponses trs = new TicketResponses();
			trs.setActive(ticketResponses2.getActive());
			trs.setPublish(ticketResponses2.getPublish());
			trs.setResponseDate(new Date());
			trs.setStatus(ticketResponses2.getStatus());
			trs.setTicket(ticketResponses2.getTicket());
			trs.setDepartmentEntity1(ticketResponses2.getDepartmentEntity1());
			trs.setDepartmentEntity2(ticketResponses2.getDepartmentEntity2());
			trs.setUser(ticketResponses2.getUser());
			User user = userRepo.getOne(userId);
			String message = "Unparked By " + user.getUserName() + " " + user.getUsersurName();
			trs.setRmessage(message);
			ticketResponseRepo.save(trs);
			b = true;
		} catch (Exception e) {
			logger.debug("Unable to fetch the unparked Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return b;
	}

	@Override
	public Boolean idttranfor(TicketResponses ticketResponses) throws ServiceException {
		Boolean b = false;
		try {
			Ticket ticket = ticketRepo.getOne(ticketResponses.getTicket().getTicketId());
			ticket.setUser1(null);
			ticket.setDepartmentEntity2(ticketResponses.getDepartmentEntity2());
			Stages stages = new Stages();
			stages.setStagesId(5l);
			ticket.setStages(stages);
			ticketRepo.save(ticket);
			List<TicketResponses> ticketResponses1 = ticket.getTicketResponses();
			TicketResponses ticketResponses2 = ticketResponses1.get(ticketResponses1.size() - 1);
			TicketResponses trs = new TicketResponses();
			trs.setActive(ticketResponses2.getActive());
			trs.setPublish("pt");
			trs.setResponseDate(new Date());
			trs.setStatus(ticketResponses2.getStatus());
			trs.setTicket(ticketResponses2.getTicket());
			trs.setDepartmentEntity1(ticketResponses2.getDepartmentEntity1());
			trs.setDepartmentEntity2(ticketResponses.getDepartmentEntity2());
			trs.setTmessage(ticketResponses.getTmessage());
			trs.setTreason(ticketResponses.getTreason());
			trs.setResponsetype(ticketResponses.getResponsetype());
			User u = new User();
			u.setUserId(ticketResponses.getUser().getUserId());
			trs.setUser(u);
			DepartmentEntity dept = departmentRepo.getOne(ticketResponses.getDepartmentEntity2().getDepartmentId());
			String message = null;
			if (ticketResponses.getTmessage() != "") {
				message = "Ticket Transfered From " + ticketResponses2.getDepartmentEntity2().getDepartmentName()
						+ " To " + dept.getDepartmentName() + "(" + ticketResponses.getTmessage() + ")";
			} else {
				message = "Ticket Transfered From " + ticketResponses2.getDepartmentEntity2().getDepartmentName()
						+ " To " + dept.getDepartmentName();
			}
			trs.setRmessage(message);
			ticketResponseRepo.save(trs);
			b = true;
		} catch (Exception e) {
			logger.debug("Unable to fetch the idttranfor Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return b;
	}

	@Override
	public Ticket getFile(Long ticketId) throws ServiceException {
		Ticket findByTicketId = null;
		try {
			findByTicketId = ticketRepo.findByTicketId(ticketId);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getFile method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return findByTicketId;
	}

	@Override
	public IdtticketDto getTicketId(Long ticketId) throws ServiceException {
		IdtticketDto map = null;
		try {
			Ticket ticket = ticketRepo.findByTicketId(ticketId);
			map = mapper.map(ticket, IdtticketDto.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getTicketId mettod"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return map;
	}

	@Override
	public Boolean retransfered(TicketResponses ticketResponses) throws ServiceException {
		Boolean b = false;
		try {
			Ticket ticket = ticketRepo.getOne(ticketResponses.getTicket().getTicketId());
			ticket.setUser1(null);
			ticket.setDepartmentEntity2(ticketResponses.getDepartmentEntity2());
			Stages stages = new Stages();
			stages.setStagesId(1l);
			ticket.setStages(stages);
			ticketRepo.save(ticket);
			List<TicketResponses> ticketResponses1 = ticket.getTicketResponses();
			TicketResponses ticketResponses2 = ticketResponses1.get(ticketResponses1.size() - 1);
			TicketResponses trs = new TicketResponses();
			trs.setActive(ticketResponses2.getActive());
			trs.setPublish("pt");
			trs.setResponseDate(new Date());
			trs.setStatus(ticketResponses2.getStatus());
			trs.setTicket(ticketResponses2.getTicket());
			trs.setDepartmentEntity1(ticketResponses2.getDepartmentEntity1());
			trs.setDepartmentEntity2(ticketResponses.getDepartmentEntity2());
			trs.setTmessage(ticketResponses.getTmessage());
			trs.setResponsetype(ticketResponses.getResponsetype());
			User u = new User();
			u.setUserId(ticketResponses.getUser().getUserId());
			trs.setUser(u);
			DepartmentEntity dept = departmentRepo.getOne(ticketResponses.getDepartmentEntity2().getDepartmentId());
			String message = null;
			if (ticketResponses.getTmessage() != "") {
				message = "Ticket Transfered From " + ticketResponses2.getDepartmentEntity2().getDepartmentName()
						+ " To " + dept.getDepartmentName() + "(" + ticketResponses.getTmessage() + ")";
			} else {
				message = "Ticket Transfered From " + ticketResponses2.getDepartmentEntity2().getDepartmentName()
						+ " To " + dept.getDepartmentName();
			}
			trs.setRmessage(message);
			ticketResponseRepo.save(trs);
			b = true;
		} catch (Exception e) {
			logger.debug("Unable to fetch the retransfered method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return b;
	}

	@Override
	public long countByStatus(Date sdate, Date tdate) {
		long count = ticketRepo.count(TicketSpec.getbetweendates(sdate,tdate));
		return count;
	}


	@Override
	public long countForOpenStatus(Date sdate, Date tdate) {
		long count = ticketRepo.count(where(TicketSpec.getstatus(1l).and(TicketSpec.getbetweendates(sdate,tdate))));
		return count;
	}


	@Override
	public long countForClosedStatus(Date sdate, Date tdate) {
		long count = ticketRepo.count(where(TicketSpec.getstatus(2l).and(TicketSpec.getbetweendates(sdate,tdate))));
		return count;
	}


	@Override
	public List getDepartmentOpenCount(Date sdate, Date tdate) {
		List<Object> list = ticketRepo.getDepartmentOpenCount(sdate,tdate);
		List list2 = new ArrayList<>();
		for (Object obj : list) {
			HashMap<String,Object> map=new HashMap<>();
			Object[] obj1 = (Object[]) obj;
			map.put("departmentname",(String)obj1[1]);
			map.put("count", obj1[0]);
			list2.add(map);
		}
		return list2;
	}


	@Override
	public List getDepartmentCloseCount(Date sdate, Date tdate) {
		List<Object> list = ticketRepo.getDepartmentCloseCount(sdate,tdate);
		List list2 = new ArrayList<>();
		for (Object obj : list) {
			HashMap<String,Object> map=new HashMap<>();
			Object[] obj1 = (Object[]) obj;
			map.put("departmentname",(String)obj1[1]);
			map.put("count", obj1[0]);
			list2.add(map);
		}
		return list2;
	}


	@Override
	public List getDepartmentAllCount(Date sdate, Date tdate) {
		List<Object> list = ticketRepo.getDepartmentAllCount(sdate,tdate);
		List list2 = new ArrayList<>();
		for (Object obj : list) {
			HashMap<String,Object> map=new HashMap<>();
			Object[] obj1 = (Object[]) obj;
			map.put("label",(String)obj1[1]);
			map.put("y", obj1[0]);
			list2.add(map);
		}
		return list2;
	}


	@Override
	public List getAllServiceCategoryCount(Date sdate, Date tdate) {
		List<Object> list = ticketRepo.getAllServiceCategoryCount(sdate,tdate);
		List list2 = new ArrayList<>();
		for (Object obj : list) {
			HashMap<String,Object> map=new HashMap<>();
			Object[] obj1 = (Object[]) obj;
			map.put("label",(String)obj1[1]);
			map.put("y", obj1[0]);
			list2.add(map);
		}
		return list2;
	}


	@Override
	public List getAgentWiseAllTickets(Date sdate, Date tdate) {
		List<Object> list = ticketRepo.getAgentWiseAllTickets(sdate,tdate);
		List list2 = new ArrayList<>();
		for (Object obj : list) {
			HashMap<String,Object> map=new HashMap<>();
			Object[] obj1 = (Object[]) obj;
			map.put("label",(String)obj1[1]+" "+(String)obj1[2]);
			map.put("y", obj1[0]);
			list2.add(map);
		}
		return list2;
	}

}
