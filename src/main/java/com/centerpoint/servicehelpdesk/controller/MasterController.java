package com.centerpoint.servicehelpdesk.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.centerpoint.servicehelpdesk.dto.AnouncementDTO;
import com.centerpoint.servicehelpdesk.dto.BranchDTO;
import com.centerpoint.servicehelpdesk.dto.CityDTO;
import com.centerpoint.servicehelpdesk.dto.CountryDTO;
import com.centerpoint.servicehelpdesk.dto.DepartmentDTO;
import com.centerpoint.servicehelpdesk.dto.IdtticketDto;
import com.centerpoint.servicehelpdesk.dto.OrganizationDTO;
import com.centerpoint.servicehelpdesk.dto.ParkedReasonDTO;
import com.centerpoint.servicehelpdesk.dto.ServiceCategoryDTO;
import com.centerpoint.servicehelpdesk.dto.StateDTO;
import com.centerpoint.servicehelpdesk.dto.TicketDTO;
import com.centerpoint.servicehelpdesk.dto.TicketResponseDTO;
import com.centerpoint.servicehelpdesk.dto.UserDTO;
import com.centerpoint.servicehelpdesk.dto.UserLocationDTO;
import com.centerpoint.servicehelpdesk.entity.AnouncementEntity;
import com.centerpoint.servicehelpdesk.entity.BranchesEntity;
import com.centerpoint.servicehelpdesk.entity.City;
import com.centerpoint.servicehelpdesk.entity.Country;
import com.centerpoint.servicehelpdesk.entity.DepartmentEntity;
import com.centerpoint.servicehelpdesk.entity.OrganizationEntity;
import com.centerpoint.servicehelpdesk.entity.ServiceCategories;
import com.centerpoint.servicehelpdesk.entity.Stages;
import com.centerpoint.servicehelpdesk.entity.State;
import com.centerpoint.servicehelpdesk.entity.Status;
import com.centerpoint.servicehelpdesk.entity.Ticket;
import com.centerpoint.servicehelpdesk.entity.TicketResponses;
import com.centerpoint.servicehelpdesk.entity.User;
import com.centerpoint.servicehelpdesk.entity.UserRoles;
import com.centerpoint.servicehelpdesk.exception.ServiceException;
import com.centerpoint.servicehelpdesk.service.MasterService;
import com.centerpoint.servicehelpdesk.service.TicketService;
import com.centerpoint.servicehelpdesk.service.UserService;

@RestController
@CrossOrigin
public class MasterController {

	@Autowired
	private MasterService masterService;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private TicketService ticketService;

	@GetMapping("getUserRoles")
	private ResponseEntity<List<UserRoles>> getUserRole() throws ServiceException {
		List<UserRoles> userRole = masterService.getUserRole();
		return new ResponseEntity<List<UserRoles>>(userRole, HttpStatus.OK);
	}

	@PostMapping("saveCountry")
	private ResponseEntity<Country> saveCountry(@RequestBody Country country) throws ServiceException {
		Country saveCountry = masterService.saveCountry(country);
		return new ResponseEntity<Country>(saveCountry, HttpStatus.OK);
	}

	@PostMapping("saveState")
	private ResponseEntity<State> saveState(@RequestBody State state) throws ServiceException {
		State saveState = masterService.saveState(state);
		return new ResponseEntity<State>(saveState, HttpStatus.OK);
	}

	@PostMapping("saveCity")
	private ResponseEntity<City> saveCity(@RequestBody City city) throws ServiceException {
		City saveCity = masterService.saveCity(city);
		return new ResponseEntity<City>(saveCity, HttpStatus.OK);
	}

	@GetMapping("getCity")
	private ResponseEntity<List<CityDTO>> getCity() throws ServiceException {
		List<CityDTO> city = masterService.getCity();
		return new ResponseEntity<List<CityDTO>>(city, HttpStatus.OK);
	}

	@GetMapping("getStateByCityId/{cityId}")
	private ResponseEntity<StateDTO> getStatebyCityId(@PathVariable Long cityId) throws ServiceException {
		StateDTO statebyCityId = masterService.getStatebyCityId(cityId);
		return new ResponseEntity<StateDTO>(statebyCityId, HttpStatus.OK);
	}

	@GetMapping("getCountryByStateId/{stateId}")
	private ResponseEntity<CountryDTO> getCountryStateId(@PathVariable Long stateId) throws ServiceException {
		CountryDTO countryStateId = masterService.getCountryStateId(stateId);
		return new ResponseEntity<CountryDTO>(countryStateId, HttpStatus.OK);
	}

	@PostMapping("saveOrganization")
	private ResponseEntity<OrganizationEntity> saveOrganization(@RequestBody OrganizationEntity organizationEntity)
			throws ServiceException {
		OrganizationEntity saveOrganization = masterService.saveOrganization(organizationEntity);
		return new ResponseEntity<OrganizationEntity>(saveOrganization, HttpStatus.OK);
	}

	@GetMapping("getOrganization")
	private ResponseEntity<List<OrganizationDTO>> getOrganization() throws ServiceException {
		List<OrganizationDTO> organization = masterService.getOrganization();
		return new ResponseEntity<List<OrganizationDTO>>(organization, HttpStatus.OK);
	}

	@PostMapping("saveBranch")
	private ResponseEntity<BranchesEntity> saveBranch(@RequestBody BranchesEntity branchesEntity)
			throws ServiceException {
		BranchesEntity saveBranch = masterService.saveBranch(branchesEntity);
		return new ResponseEntity<BranchesEntity>(saveBranch, HttpStatus.OK);
	}

	@GetMapping("getBranchbyOrganizationId/{organizationId}")
	private ResponseEntity<List<BranchDTO>> getBranchbyOrganizationId(@PathVariable Long organizationId)
			throws ServiceException {
		List<BranchDTO> branchbyOrganizationId = masterService.getBranchbyOrganizationId(organizationId);
		return new ResponseEntity<List<BranchDTO>>(branchbyOrganizationId, HttpStatus.OK);
	}

	@PostMapping("saveDepartment")
	private ResponseEntity<DepartmentEntity> saveDepartment(@RequestBody DepartmentEntity departmentEntity)
			throws ServiceException {
		DepartmentEntity saveDepartment = masterService.saveDepartment(departmentEntity);
		return new ResponseEntity<DepartmentEntity>(saveDepartment, HttpStatus.OK);
	}

	@GetMapping("getDepartmentbyBranchId/{branchId}")
	private ResponseEntity<List<DepartmentDTO>> getDepartmentbyBranchId(@PathVariable Long branchId)
			throws ServiceException {
		List<DepartmentDTO> departmentbyBranchId = masterService.getDepartmentbyBranchId(branchId);
		return new ResponseEntity<List<DepartmentDTO>>(departmentbyBranchId, HttpStatus.OK);
	}

	@PostMapping("saveServiceCategories")
	private ResponseEntity<ServiceCategories> saveServiceCategories(@RequestBody ServiceCategories serviceCategories)
			throws ServiceException {
		ServiceCategories saveServiceCategories = masterService.saveServiceCategories(serviceCategories);
		return new ResponseEntity<ServiceCategories>(saveServiceCategories, HttpStatus.OK);
	}

	@GetMapping("getDepartment")
	private ResponseEntity<List<DepartmentDTO>> getDepartment() throws ServiceException {
		List<DepartmentDTO> department = masterService.getDepartment();
		return new ResponseEntity<List<DepartmentDTO>>(department, HttpStatus.OK);
	}

	@GetMapping("getServiceCategory/{departmentId}")
	private ResponseEntity<List<ServiceCategoryDTO>> getServiceCategory(@PathVariable Long departmentId)
			throws ServiceException {
		List<ServiceCategoryDTO> serviceCategory = masterService.getServiceCategory(departmentId);
		return new ResponseEntity<List<ServiceCategoryDTO>>(serviceCategory, HttpStatus.OK);
	}

	@GetMapping("getStatus")
	private ResponseEntity<List<Status>> getStatus() throws ServiceException {
		List<Status> status = masterService.getStatus();
		return new ResponseEntity<List<Status>>(status, HttpStatus.OK);
	}

	@PostMapping("/saveUser")
	private ResponseEntity<UserDTO> saveUser(@RequestBody @Valid User user, BindingResult bindingResult)
			throws ServiceException {
		UserDTO saveUser = userService.saveUser(user);
		return new ResponseEntity<UserDTO>(saveUser, HttpStatus.OK);
	}

	@GetMapping("/login/{ssoId}")
	public ResponseEntity<UserDTO> getUserDetails(@PathVariable String ssoId) throws ServiceException {
		UserDTO dto = null;
		User findUserCode = userService.getUserDetails(ssoId);
		if (findUserCode != null) {
			dto = mapper.map(findUserCode, UserDTO.class);
			return new ResponseEntity<UserDTO>(dto, HttpStatus.OK);
		} else {
			return new ResponseEntity<UserDTO>(dto, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("getUserLocation")
	private ResponseEntity<List<UserLocationDTO>> getUserLocation() throws ServiceException {
		List<UserLocationDTO> userLocation = userService.getUserLocation();
		return new ResponseEntity<List<UserLocationDTO>>(userLocation, HttpStatus.OK);
	}

	@GetMapping("getuser/{userId}")
	public ResponseEntity<UserDTO> getuser(@PathVariable Long userId) throws ServiceException {
		UserDTO user = userService.getUser(userId);
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}

	@GetMapping("getUserList")
	private ResponseEntity<Page<UserDTO>> getUserList(@RequestParam String searchText, Pageable pageable)
			throws ServiceException {
		Page<UserDTO> userList = userService.getUserList(searchText, pageable);
		return new ResponseEntity<Page<UserDTO>>(userList, HttpStatus.OK);
	}

	@PutMapping("disableUserById/{userId}")
	public ResponseEntity<Boolean> disableUserById(@PathVariable Long userId) throws ServiceException {
		Boolean b = userService.disableUserById(userId);
		if (true == b) {
			return new ResponseEntity<Boolean>(b, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(b, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("editUser")
	public ResponseEntity<UserDTO> editUser(@RequestBody User user) throws ServiceException {
		UserDTO editUser = userService.editUser(user);
		return new ResponseEntity<UserDTO>(editUser, HttpStatus.OK);
	}

	@PostMapping("saveTicket")
	private ResponseEntity<TicketDTO> saveTicket(@RequestPart Ticket ticket,
			@RequestPart(name = "file", required = false) MultipartFile pic) throws ServiceException, IOException {
		if (pic != null) {
			ticket.setPic(ticket.getUser().getUserId() + "" + pic.getOriginalFilename());
			try {
				byte[] bytes = pic.getBytes();
				ticket.setData(bytes);
			} catch (Exception e) {
				throw new ServiceException("Internal server Error", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		TicketDTO saveTicket = ticketService.saveTicket(ticket);
		return new ResponseEntity<TicketDTO>(saveTicket, HttpStatus.OK);
	}

	@GetMapping("getTicket")
	private ResponseEntity<Page<TicketDTO>> getTicket(@RequestParam String searchText, @RequestParam Long statusId,
			@RequestParam Long userId, Pageable pageable) throws ServiceException {
		Page<TicketDTO> ticket = ticketService.getTicket(userId, statusId, searchText, pageable);
		return new ResponseEntity<Page<TicketDTO>>(ticket, HttpStatus.OK);
	}

	@GetMapping("getTicketResponseByUserTicketId/{ticketId}")
	private ResponseEntity<List<TicketResponseDTO>> getTicketResponseByUserTicketId(@PathVariable Long ticketId)
			throws ServiceException {
		List<TicketResponseDTO> ticketResponseByUserTicketId = ticketService.getTicketResponseByUserTicketId(ticketId);
		return new ResponseEntity<List<TicketResponseDTO>>(ticketResponseByUserTicketId, HttpStatus.OK);
	}

	@GetMapping("getTicketResponseByTicketId/{ticketId}")
	private ResponseEntity<List<TicketResponseDTO>> getTicketResponseByTicketId(@PathVariable Long ticketId)
			throws ServiceException {
		List<TicketResponseDTO> ticketResponseByTicketId = ticketService.getTicketResponseByTicketId(ticketId);
		return new ResponseEntity<List<TicketResponseDTO>>(ticketResponseByTicketId, HttpStatus.OK);
	}

	@PostMapping("saveTicketResponse")
	private ResponseEntity<Boolean> saveTicketResponse(@RequestBody TicketResponses ticketResponses)
			throws ServiceException {
		Boolean b = ticketService.saveTicketResponse(ticketResponses);
		if (true == b) {
			return new ResponseEntity<Boolean>(b, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(b, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("closeTicketResponse")
	private ResponseEntity<Boolean> closeTicketResponse(@RequestBody TicketResponses ticketResponses)
			throws ServiceException {
		Boolean b = ticketService.closeTicketResponse(ticketResponses);
		if (true == b) {
			return new ResponseEntity<Boolean>(b, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(b, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getOpenticketCount/{statusId}/{userId}")
	public ResponseEntity<Long> getOpenticketCount(@PathVariable Long statusId, @PathVariable Long userId)
			throws ServiceException {
		List<Ticket> list = ticketService.getdashticketCount(statusId, userId);
		long size = list.size();
		return new ResponseEntity<>(size, HttpStatus.OK);
	}

	@GetMapping("/getCloseticketCount/{statusId}/{userId}")
	public ResponseEntity<Long> getCloseticketCount(@PathVariable Long statusId, @PathVariable Long userId)
			throws ServiceException {
		List<Ticket> list = ticketService.getdashticketCount(statusId, userId);
		long size = list.size();
		return new ResponseEntity<>(size, HttpStatus.OK);
	}

	@GetMapping("/getPendingticketCount/{statusId}/{userId}")
	public ResponseEntity<Long> getPendingticketCount(@PathVariable Long statusId, @PathVariable Long userId)
			throws ServiceException {
		List<Ticket> list = ticketService.getdashticketCount(statusId, userId);
		long size = list.size();
		return new ResponseEntity<>(size, HttpStatus.OK);
	}

	@PostMapping("saveanouncement")
	public Integer saveanouncement(@RequestBody List<AnouncementEntity> anouncementEntity) throws ServiceException {
		return userService.saveanouncement(anouncementEntity);
	}

	@GetMapping("/getanouncements/{locationId}")
	public List<AnouncementDTO> getanouncements(@PathVariable Long locationId) throws ServiceException {
		return userService.getanouncements(locationId);
	}

	@GetMapping("/anouncementGrid")
	public Page<AnouncementDTO> anouncementGrid(@RequestParam String searchText, Pageable pageable)
			throws ServiceException {
		return userService.anouncementGrid(searchText, pageable);
	}

	@PostMapping("openTicketResponse")
	public ResponseEntity<Boolean> openTicketResponse(@RequestBody TicketResponses ticketResponses)
			throws ServiceException {
		Boolean b = ticketService.openTicketResponse(ticketResponses);
		if (true == b) {
			return new ResponseEntity<Boolean>(b, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(b, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/deleteanouncement/{anouncementId}")
	public ResponseEntity<Boolean> deleteanouncement(@PathVariable Long anouncementId) throws ServiceException {
		Boolean b = userService.deleteanouncement(anouncementId);
		if (true == b) {
			return new ResponseEntity<Boolean>(b, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(b, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("agentgetTicket")
	private ResponseEntity<Page<TicketDTO>> agentgetTicket(@RequestParam Long deptId, @RequestParam String searchText,
			@RequestParam Long statusId, Pageable pageable) throws ServiceException {
		Page<TicketDTO> agentgetTicket = ticketService.agentgetTicket(deptId, statusId, searchText, pageable);
		return new ResponseEntity<Page<TicketDTO>>(agentgetTicket,HttpStatus.OK);
	}

	@GetMapping("getUserByEmpid")
	@ResponseBody
	public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String empid, @RequestParam String url)
			throws ServiceException, MessagingException {
		UserDTO dto = userService.getUserByEmail(empid, url);
		return new ResponseEntity<UserDTO>(dto, HttpStatus.OK);
	}

	@GetMapping("newPassword")
	public ResponseEntity<UserDTO> newPassword(@RequestParam String token, @RequestParam String password) throws ServiceException {
		UserDTO dto = userService.newPassword(token, password);
		return new ResponseEntity<UserDTO>(dto, HttpStatus.OK);
	}

	@PostMapping("resolvedTicketResponse")
	public ResponseEntity<Boolean> resolvedTicketResponse(@RequestBody TicketResponses ticketResponses)
			throws ServiceException {
		Boolean b = ticketService.resolvedTicketResponse(ticketResponses);
		if (true == b) {
			return new ResponseEntity<Boolean>(b, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(b, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("getUserByDepartmentId/{departmentId}")
	public ResponseEntity<List<UserDTO>> getUserByDepartmentId(@PathVariable Long departmentId) throws ServiceException {
		List<UserDTO> dto = userService.getUserByDepartmentId(departmentId);
		return new ResponseEntity<List<UserDTO>>(dto, HttpStatus.OK);
	}

	@PutMapping("selfassign/{userId}/{ticketId}")
	public ResponseEntity<Boolean> selfassign(@PathVariable Long userId, @PathVariable Long ticketId)
			throws ServiceException {
		Boolean b = ticketService.selfassign(userId, ticketId);
		if (true == b) {
			return new ResponseEntity<Boolean>(b, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(b, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("getparkedreason/{departmentId}")
	public ResponseEntity<List<ParkedReasonDTO>> getparkedreason(@PathVariable Long departmentId) throws ServiceException {
		List<ParkedReasonDTO> getparkedreason = masterService.getparkedreason(departmentId);
		return new ResponseEntity<List<ParkedReasonDTO>>(getparkedreason,HttpStatus.OK);
	}

	@GetMapping("parked/{userId}/{ticketId}/{pmassage}/{parkedId}")
	public ResponseEntity<Boolean> parked(@PathVariable Long userId, @PathVariable Long ticketId,
			@PathVariable Long parkedId, @PathVariable String pmassage) throws ServiceException {
		Boolean b = ticketService.parked(userId, ticketId, parkedId, pmassage);
		if (true == b) {
			return new ResponseEntity<Boolean>(b, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(b, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("getTicketDataByDate")
	public PdfView getTicketDataByDate(@RequestParam String sdate, @RequestParam String tdate,
			@RequestParam Long departmentId, @RequestParam Long serviceCategorieId, @RequestParam Long stagesId,
			Model model) throws ServiceException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(tdate));
		} catch (Exception e) {

		}
		c.add(Calendar.DATE, 1);
		String newDate = sdf.format(c.getTime());
		Stages stages = new Stages();
		ServiceCategories ser = new ServiceCategories();
		DepartmentEntity d = new DepartmentEntity();
		if (departmentId == 0) {
			d.setDepartmentId(null);
		} else {
			d.setDepartmentId(departmentId);
		}
		if (serviceCategorieId == 0) {
			ser.setServiceCategorieId(null);
			;
		} else {
			ser.setServiceCategorieId(serviceCategorieId);
		}
		if (stagesId == 0) {
			stages.setStagesId(null);
		} else {
			stages.setStagesId(stagesId);
		}
		Ticket ticket = new Ticket();
		ticket.setSdate(sdate);
		ticket.setTdate(newDate);
		ticket.setDepartmentEntity(d);
		ticket.setServiceCategories(ser);
		ticket.setStages(stages);
		List<Ticket> dtodata = ticketService.getTicketDataByDate(ticket);
		model.addAttribute("sdate", sdate);
		model.addAttribute("tdate", tdate);
		model.addAttribute("users", dtodata);
		return new PdfView();
	}

	@GetMapping("updatePassword")
	public ResponseEntity<UserDTO> updatePassword(@RequestParam String oldpassword, @RequestParam String newpassword,
			@RequestParam Long userid) throws ServiceException {
		BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
		String newpwd = crypt.encode(newpassword);
		UserDTO user = userService.getUser(userid);
		boolean matches = crypt.matches(oldpassword, user.getPassword());
		if (matches) {
			User userdata = mapper.map(user, User.class);
			userdata.setPassword(newpwd);
			userService.updatePassword(userdata);
			return new ResponseEntity<UserDTO>(user,HttpStatus.OK);
		}
		return new ResponseEntity<UserDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("getStages")
	private ResponseEntity<List<Stages>> getStages() throws ServiceException {
		List<Stages> stages = masterService.getStages();
		return new ResponseEntity<List<Stages>>(stages,HttpStatus.OK);
		}

	@PutMapping("peertopeer")
	public ResponseEntity<Boolean> peertopeer(@RequestBody TicketResponses ticketResponses) throws ServiceException {
			Boolean b = ticketService.peertopeer(ticketResponses);
		if (true == b) {
			return new ResponseEntity<Boolean>(b, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(b, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@GetMapping("getagentUserByDepartmentId/{departmentId}")
	public ResponseEntity<List<UserDTO>> getagentUserByDepartmentId(@PathVariable Long departmentId) throws ServiceException {
		List<UserDTO> getagentUserByDepartmentId = userService.getagentUserByDepartmentId(departmentId);
		return new ResponseEntity<List<UserDTO>>(getagentUserByDepartmentId,HttpStatus.OK);
	}

	@PutMapping("unparked/{userId}/{ticketId}")
	public ResponseEntity<Boolean> unparked(@PathVariable Long userId, @PathVariable Long ticketId) throws ServiceException {
			Boolean b = ticketService.unparked(userId, ticketId);
		if (true == b) {
			return new ResponseEntity<Boolean>(b, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(b, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("idttranfor")
	public ResponseEntity<Boolean> idttranfor(@RequestBody TicketResponses ticketResponses) throws ServiceException {
					Boolean b = ticketService.idttranfor(ticketResponses);
		if (true == b) {
			return new ResponseEntity<Boolean>(b, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(b, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/downloadimage/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws ServiceException {
		Ticket t = ticketService.getFile(fileId);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/jpeg"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + t.getPic() + "\"")
				.body(new ByteArrayResource(t.getData()));
	}

	@GetMapping("getTicketDataByDateExcelData")
	public ExcelView getTicketDataByDateExcelData(@RequestParam String sdate, @RequestParam String tdate,
			@RequestParam Long departmentId, @RequestParam Long serviceCategorieId, @RequestParam Long stagesId,
			@RequestParam Long statusid, @RequestParam Long redepartmentId, @RequestParam Long ajentlistId, Model model)
			throws ServiceException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(tdate));
		} catch (Exception e) {

		}
		c.add(Calendar.DATE, 1);
		String newDate = sdf.format(c.getTime());
		Status status = new Status();
		Stages stages = new Stages();
		ServiceCategories ser = new ServiceCategories();
		DepartmentEntity d2 = new DepartmentEntity();
		DepartmentEntity d = new DepartmentEntity();
		if (redepartmentId == 0) {
			d2.setDepartmentId(null);
		} else {
			d2.setDepartmentId(redepartmentId);
		}
		if (departmentId == 0) {
			d.setDepartmentId(null);
		} else {
			d.setDepartmentId(departmentId);
		}
		if (serviceCategorieId == 0) {
			ser.setServiceCategorieId(null);
			;
		} else {
			ser.setServiceCategorieId(serviceCategorieId);
		}
		if (stagesId == 0) {
			stages.setStagesId(null);
		} else {
			stages.setStagesId(stagesId);
		}
		if (statusid == 0) {
			status.setStatusId(null);
		} else {
			status.setStatusId(statusid);
		}
		Ticket ticket = new Ticket();
		ticket.setSdate(sdate);
		ticket.setTdate(newDate);
		ticket.setDepartmentEntity(d);
		ticket.setUserDepartment(d2);
		ticket.setServiceCategories(ser);
		ticket.setStages(stages);
		ticket.setStatus(status);
		ticket.setAgentId(ajentlistId);
		List<Ticket> dtodata = ticketService.getTicketDataByDate(ticket);
		model.addAttribute("sdate", sdate);
		model.addAttribute("tdate", tdate);
		model.addAttribute("users", dtodata);
		return new ExcelView();

	}

	@GetMapping("getTicketDataByDatePdfData")
	public PdfView getTicketDataByDatePdfData(@RequestParam String sdate, @RequestParam String tdate,
			@RequestParam Long departmentId, @RequestParam Long serviceCategorieId, @RequestParam Long stagesId,
			@RequestParam Long statusid, @RequestParam Long redepartmentId, @RequestParam Long ajentlistId, Model model)
			throws ServiceException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(tdate));
		} catch (Exception e) {

		}
		c.add(Calendar.DATE, 1);
		String newDate = sdf.format(c.getTime());
		Status status = new Status();
		Stages stages = new Stages();
		ServiceCategories ser = new ServiceCategories();
		DepartmentEntity d2 = new DepartmentEntity();
		DepartmentEntity d = new DepartmentEntity();
		if (redepartmentId == 0) {
			d2.setDepartmentId(null);
		} else {
			d2.setDepartmentId(redepartmentId);
		}
		if (departmentId == 0) {
			d.setDepartmentId(null);
		} else {
			d.setDepartmentId(departmentId);
		}
		if (serviceCategorieId == 0) {
			ser.setServiceCategorieId(null);
			;
		} else {
			ser.setServiceCategorieId(serviceCategorieId);
		}
		if (stagesId == 0) {
			stages.setStagesId(null);
		} else {
			stages.setStagesId(stagesId);
		}
		if (statusid == 0) {
			status.setStatusId(null);
		} else {
			status.setStatusId(statusid);
		}
		Ticket ticket = new Ticket();
		ticket.setSdate(sdate);
		ticket.setTdate(newDate);
		ticket.setDepartmentEntity(d);
		ticket.setUserDepartment(d2);
		ticket.setServiceCategories(ser);
		ticket.setStages(stages);
		ticket.setStatus(status);
		ticket.setAgentId(ajentlistId);
		List<Ticket> dtodata = ticketService.getTicketDataByDate(ticket);
		model.addAttribute("sdate", sdate);
		model.addAttribute("tdate", tdate);
		model.addAttribute("users", dtodata);
		return new PdfView();
	}

	@GetMapping("getAgentList")
	public ResponseEntity<List<UserDTO>> getAgentList() throws ServiceException {
		List<UserDTO> agentsList = userService.getAgentsList();
		return new ResponseEntity<List<UserDTO>>(agentsList,HttpStatus.OK);
	}

	@GetMapping("checkcurrentowner/{ticketId}")
	public ResponseEntity<IdtticketDto> checkcurrentowner(@PathVariable Long ticketId) throws ServiceException {
		IdtticketDto ticketId2 = ticketService.getTicketId(ticketId);
		return new ResponseEntity<IdtticketDto>(ticketId2,HttpStatus.OK);
	}

	@GetMapping("getDepartmentId/{departmentId}")
	public ResponseEntity<List<DepartmentDTO>> getDepartmentId(@PathVariable Long departmentId) throws ServiceException {
		List<DepartmentDTO> departmentId2 = masterService.getDepartmentId(departmentId);
		return new ResponseEntity<List<DepartmentDTO>>(departmentId2,HttpStatus.OK);
	}

	@PutMapping("retransfered")
	public ResponseEntity<Boolean> retransfered(@RequestBody TicketResponses responses) throws ServiceException {
		Boolean b = ticketService.retransfered(responses);
		if (true == b) {
			return new ResponseEntity<Boolean>(b, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(b, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("getTicketDetailsForGraph")
	public HashMap getTicketDetailsForGraph(@RequestParam String sdate, @RequestParam String tdate)
			throws ServiceException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sdate1 = sdf.parse(sdate);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(tdate));
		} catch (Exception e) {

		}
		c.add(Calendar.DATE, 1);
		Date tdate1 = c.getTime();
		long count1 = ticketService.countByStatus(sdate1, tdate1);
		long count2 = ticketService.countForOpenStatus(sdate1, tdate1);
		long count3 = ticketService.countForClosedStatus(sdate1, tdate1);
		HashMap<String, Object> map = new HashMap<>();
		map.put("totalTicketCount", count1);
		map.put("openTicketCount", count2);
		map.put("closeTicketCount", count3);
		return map;
	}

	@GetMapping("getDepartmentOpenCount")
	public List getDepartmentOpenCount(@RequestParam String sdate, @RequestParam String tdate)
			throws ServiceException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sdate1 = sdf.parse(sdate);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(tdate));
		} catch (Exception e) {

		}
		c.add(Calendar.DATE, 1);
		Date tdate1 = c.getTime();
		List list = ticketService.getDepartmentOpenCount(sdate1, tdate1);
		return list;
	}

	@GetMapping("getDepartmentCloseCount")
	public List getDepartmentCloseCount(@RequestParam String sdate, @RequestParam String tdate)
			throws ParseException, ServiceException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sdate1 = sdf.parse(sdate);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(tdate));
		} catch (Exception e) {

		}
		c.add(Calendar.DATE, 1);
		Date tdate1 = c.getTime();
		List list = ticketService.getDepartmentCloseCount(sdate1, tdate1);
		return list;
	}

	@GetMapping("getDepartmentAllCount")
	public List getDepartmentAllCount(@RequestParam String sdate, @RequestParam String tdate)
			throws ServiceException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sdate1 = sdf.parse(sdate);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(tdate));
		} catch (Exception e) {

		}
		c.add(Calendar.DATE, 1);
		Date tdate1 = c.getTime();
		List list = ticketService.getDepartmentAllCount(sdate1, tdate1);
		return list;
	}

	@GetMapping("getAllServiceCategoryCount")
	public List getAllServiceCategoryCount(@RequestParam String sdate, @RequestParam String tdate)
			throws ServiceException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sdate1 = sdf.parse(sdate);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(tdate));
		} catch (Exception e) {

		}
		c.add(Calendar.DATE, 1);
		Date tdate1 = c.getTime();
		List list = ticketService.getAllServiceCategoryCount(sdate1, tdate1);
		return list;
	}

	@GetMapping("getAgentWiseAllTickets")
	public List getAgentWiseAllTickets(@RequestParam String sdate, @RequestParam String tdate)
			throws ServiceException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sdate1 = sdf.parse(sdate);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(tdate));
		} catch (Exception e) {

		}
		c.add(Calendar.DATE, 1);
		Date tdate1 = c.getTime();
		List list = ticketService.getAgentWiseAllTickets(sdate1, tdate1);
		return list;
	}
}