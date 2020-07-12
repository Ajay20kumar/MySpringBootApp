package com.centerpoint.servicehelpdesk.UserServiceImplTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.centerpoint.ServiceHelpDeskApplication;
import com.centerpoint.servicehelpdesk.dto.AnouncementDTO;
import com.centerpoint.servicehelpdesk.dto.UserDTO;
import com.centerpoint.servicehelpdesk.dto.UserLocationDTO;
import com.centerpoint.servicehelpdesk.entity.Active;
import com.centerpoint.servicehelpdesk.entity.AnouncementEntity;
import com.centerpoint.servicehelpdesk.entity.DepartmentEntity;
import com.centerpoint.servicehelpdesk.entity.User;
import com.centerpoint.servicehelpdesk.entity.UserLocation;
import com.centerpoint.servicehelpdesk.entity.UserRoles;
import com.centerpoint.servicehelpdesk.exception.ServiceException;
import com.centerpoint.servicehelpdesk.repoDao.AnouncementRepo;
import com.centerpoint.servicehelpdesk.repoDao.UserLocationRepo;
import com.centerpoint.servicehelpdesk.repoDao.UserRepo;
import com.centerpoint.servicehelpdesk.serviceImpl.UserServiceImpl;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT,
   classes=ServiceHelpDeskApplication.class)
@TestPropertySource(locations="classpath:application.properties")
public class UserServiceImplTest {
	
	@InjectMocks
	UserServiceImpl userService;
	
	@Mock
	ModelMapper mapper;
	
	@Mock
	UserRepo userRepo;
	
	@Mock
	AnouncementRepo anouncementRepo;
	
	@Mock
	UserLocationRepo userLocationRepo;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
	}
	@Test
	public void saveUserTest() throws ServiceException {
		User user=new User();
		user.setUserId(2l);
		user.setUserName("pavan");
		user.setUserCode("202");
		user.setPassword("kumar");
		UserDTO userDto = new UserDTO();
		userDto.setUserId(2l);
		when(mapper.map(Mockito.any(User.class), Mockito.any(Class.class))).thenReturn(userDto);
		when(userRepo.findUserByuserCode(Mockito.anyString())).thenReturn(null);
    	when(userRepo.save(Mockito.any(User.class))).thenReturn(user);
    	UserDTO userdto = userService.saveUser(user);
    	assertEquals(Long.valueOf(2),userdto.getUserId());
   
	}
	@Test
	public void getUserDetailsTest() throws ServiceException {
		User user=new User();
		user.setUserId(1l);
		user.setUserName("pavan");
		user.setUserCode("3334");
		user.setPassword("kumar");
		when(userRepo.findUserByuserCode(Mockito.anyString())).thenReturn(user);
		User userDetails = userService.getUserDetails("3334");
        assertEquals(Long.valueOf(1),userDetails.getUserId());	
		
}
	@Test
	public void getUserLocationTest() throws ServiceException {
		List<UserLocation> list =new ArrayList<UserLocation>() ;
		UserLocation locationDto1=new UserLocation();
		locationDto1.setLocationId(1l);
		locationDto1.setLocationName("Somajiguda");
		UserLocation locationDto2=new UserLocation();
		locationDto2.setLocationId(2l);
		locationDto2.setLocationName("panjaguta");
		list.add(locationDto1);
		list.add(locationDto2);
		UserLocationDTO locationDto=new UserLocationDTO();
		locationDto.setLocationId(1l);
		locationDto.setLocationName("Somajiguda");
		when(mapper.map(Mockito.any(UserLocationDTO.class), Mockito.any(Class.class))).thenReturn(locationDto);
		when(userLocationRepo.findAll()).thenReturn(list);
	    List<UserLocationDTO> userLocation = userService.getUserLocation();
		assertEquals(2, userLocation.size());
	}
	
	@Test
	public void saveanouncementTest() throws ServiceException {
		List<AnouncementEntity> list =new ArrayList<AnouncementEntity>();
		AnouncementEntity annu1=new AnouncementEntity();
		annu1.setAnouncementId(1l);
		annu1.setMessage("Dance in the floor");
		AnouncementEntity annu2=new AnouncementEntity();
		annu2.setAnouncementId(2l);
		annu2.setMessage("Dance in the floor");
		list.add(annu1);
		list.add(annu2);
		for (int i = 0; i < list.size(); i++) {
		when(anouncementRepo.save(list.get(i))).thenReturn(annu1);
	  }
		Integer i=userService.saveanouncement(list);
		assertEquals(Integer.valueOf(1), i);
   }
	@Test
	public void getanouncementsTest() throws ServiceException {
		List<AnouncementEntity> list =new ArrayList<AnouncementEntity>();
		UserLocation location1 = new UserLocation();
		location1.setLocationId(1l);
		AnouncementEntity annu1=new AnouncementEntity();
		annu1.setAnouncementId(1l);
		annu1.setMessage("Dance in the floor");
		AnouncementEntity annu2=new AnouncementEntity();
		annu2.setAnouncementId(2l);
		annu2.setMessage("Dance in the floor");
		annu1.setUserLocation(location1);
		annu2.setUserLocation(location1);
		list.add(annu1);
		list.add(annu2);
		AnouncementDTO annudto=new AnouncementDTO();
		annudto.setAnouncementId(1l);
		annudto.setMessage("Dance in the floor");
		when(mapper.map(Mockito.any(AnouncementEntity.class), Mockito.any(Class.class))).thenReturn(annudto);
		when(anouncementRepo.findByuserLocation(location1,Sort.by("anouncementId"))).thenReturn(list);
		List<AnouncementDTO> getanouncements = userService.getanouncements(1l);
		assertEquals(0, getanouncements.size());
	}
    @Test
	public void getUserTest() throws ServiceException {
		User user=new User();
		user.setUserId(1l);
		user.setUserCode("101");
		user.setUserName("AKJAY");
		user.setUsersurName("alala");
		user.setPassword("kaumar");
		user.setUserShortName("ak");
		user.setUserPhone("7887878787");
		user.setUserMobile("8989898989");
		user.setUserEmail("ajay@gmail.com");
		UserDTO userDto=new UserDTO();
		userDto.setUserId(1l);
		when(mapper.map(Mockito.any(User.class), Mockito.any(Class.class))).thenReturn(userDto);
		when(userRepo.getOne(user.getUserId())).thenReturn(user);
		UserDTO u=userService.getUser(1l);
		assertEquals("101", user.getUserCode());
		assertEquals(Long.valueOf(1), user.getUserId());
		assertEquals("AKJAY", user.getUserName());
	}
	@Test
	public void disableUserByIdTest() throws ServiceException {
		User user=new User();
		user.setUserId(1l);
		user.setUserCode("101");
		user.setUserName("AKJAY");
		when(userRepo.getOne(user.getUserId())).thenReturn(user);
		when(userRepo.save(user)).thenReturn(user);
		Boolean disableUserById = userService.disableUserById(1l);
		assertEquals(Boolean.valueOf(true), disableUserById);
	}
	@Test
	public void deleteanouncementTest() throws ServiceException {
		AnouncementEntity annu1=new AnouncementEntity();
		annu1.setAnouncementId(1l);
		annu1.setMessage("Dance in the floor");
		when(anouncementRepo.getOne(annu1.getAnouncementId())).thenReturn(annu1);
		when(anouncementRepo.save(annu1)).thenReturn(annu1);
		Boolean annid = userService.deleteanouncement(1l);
		assertEquals(Boolean.valueOf(true), annid);
	}
	@Test
	public void editUserTest() throws ServiceException {
		User user=new User();
		user.setUserId(2l);
		user.setUserName("pavan");
		user.setUserCode("202");
		user.setPassword("kumar");
		Active a=new Active();
		a.setActiveId(1l);
		user.setActive(a);
		UserDTO userDto=new UserDTO();
		userDto.setUserId(2l);
		userDto.setUserName("pavan");
		userDto.setUserCode("202");
		userDto.setPassword("kumar");
		when(mapper.map(Mockito.any(User.class), Mockito.any(Class.class))).thenReturn(userDto);
		when(userRepo.getOne(user.getUserId())).thenReturn(user);
		when(userRepo.save(user)).thenReturn(user);
		UserDTO dto=userService.editUser(user);
		assertEquals(Long.valueOf(2), dto.getUserId());
	}
	@Test
	public void getUserByEmailTest() throws ServiceException, MessagingException {
		User user=new User();
		user.setUserId(2l);
		user.setUserName("pavan");
		user.setUserCode("202");
		user.setPassword("kumar");
		user.setResetToken("89898998y6645hfh");
		user.setUserEmail("ajay@centerpoint.io");
		UserDTO userDto=new UserDTO();
		userDto.setUserId(2l);
		userDto.setUserCode("202");
		String url="http:13.233.26.87:8089/forgotPassword";
		when(mapper.map(Mockito.any(User.class), Mockito.any(Class.class))).thenReturn(userDto);
		when(userRepo.findUserByuserCode(Mockito.anyString())).thenReturn(user);
		when(userRepo.save(Mockito.any(User.class))).thenReturn(user);
		UserDTO dto = userService.getUserByEmail(user.getUserCode(),url);
		assertEquals(Long.valueOf(2), dto.getUserId());
		
	}
	@Test
	public void newPasswordTest() throws ServiceException{
		User user=new User();
		user.setUserId(2l);
		user.setUserName("pavan");
		user.setUserCode("202");
		user.setPassword("kumar");
		user.setResetToken("89898998y6645hfh");
		user.setUserEmail("ajay@centerpoint.io");
		UserDTO userDto=new UserDTO();
		userDto.setUserId(2l);
		userDto.setUserName("pavan");
		userDto.setUserCode("202");
		userDto.setPassword("kumar");
		userDto.setResetToken("89898998y6645hfh");
		userDto.setUserEmail("ajay@centerpoint.io");
		when(mapper.map(Mockito.any(User.class), Mockito.any(Class.class))).thenReturn(userDto);
		when(userRepo.findUserByResetToken(Mockito.anyString())).thenReturn(user);
		when(userRepo.save(Mockito.any(User.class))).thenReturn(user);
		UserDTO dto = userService.newPassword(user.getResetToken(), user.getPassword());
		assertEquals(Long.valueOf(2), dto.getUserId());
	}
	@Test
	public void getUserByDepartmentIdTest() throws ServiceException {
		List<User> list =new ArrayList<User>();
		User user=new User();
		user.setUserId(2l);
		user.setUserName("pavan");
		user.setUserCode("202");
		user.setPassword("kumar");
		user.setResetToken("89898998y6645hfh");
		user.setUserEmail("ajay@centerpoint.io");
		DepartmentEntity departmentEntity = new DepartmentEntity();
		departmentEntity.setDepartmentId(1l);
		user.setDepartmentEntity(departmentEntity);
		list.add(user);
		
		UserDTO userDto=new UserDTO();
		userDto.setUserId(2l);
		userDto.setUserName("pavan");
		userDto.setUserCode("202");
		userDto.setPassword("kumar");
		userDto.setResetToken("89898998y6645hfh");
		userDto.setUserEmail("ajay@centerpoint.io");
		when(userRepo.findByDepartmentEntity(Mockito.any(DepartmentEntity.class))).thenReturn(list);
		List<UserDTO> userList = userService.getUserByDepartmentId(departmentEntity.getDepartmentId());
		assertEquals(1, userList.size());
	}
	@Test
	public void updatePasswordTest() throws ServiceException {
		User user=new User();
		user.setUserId(2l);
		user.setUserName("pavan");
		user.setUserCode("202");
		user.setPassword("kumar");
		user.setResetToken("89898998y6645hfh");
		user.setUserEmail("ajay@centerpoint.io");
		when(userRepo.save(Mockito.any(User.class))).thenReturn(user);
		userService.updatePassword(user);
	}
	@Test
	public void getagentUserByDepartmentIdTest() throws ServiceException {
		List<User> list =new ArrayList<User>();
		User user=new User();
		user.setUserId(2l);
		user.setUserName("pavan");
		user.setUserCode("202");
		user.setPassword("kumar");
		user.setResetToken("89898998y6645hfh");
		user.setUserEmail("ajay@centerpoint.io");
		UserRoles r = new UserRoles();
		r.setRoleId(3l);
		user.setUserRoles(r);
		DepartmentEntity departmentEntity = new DepartmentEntity();
		departmentEntity.setDepartmentId(1l);
		user.setDepartmentEntity(departmentEntity);
		list.add(user);
		when(userRepo.findByUserRolesAndDepartmentEntity(Mockito.any(UserRoles.class),Mockito.any(DepartmentEntity.class))).thenReturn(list);
		List<UserDTO> userList = userService.getagentUserByDepartmentId(departmentEntity.getDepartmentId());
		assertEquals(1, userList.size());
	}
	@Test
	public void getAgentsListTest() throws ServiceException {
		List<User> list =new ArrayList<User>();
		User user=new User();
		user.setUserId(2l);
		user.setUserName("pavan");
		user.setUserCode("202");
		user.setPassword("kumar");
		user.setResetToken("89898998y6645hfh");
		user.setUserEmail("ajay@centerpoint.io");
		UserRoles r = new UserRoles();
		r.setRoleId(3l);
		user.setUserRoles(r);
		list.add(user);
		when(userRepo.findByUserRoles(Mockito.any(UserRoles.class))).thenReturn(list);
		List<UserDTO> agentsList = userService.getAgentsList();
		assertEquals(1, agentsList.size());
	}
	/*@Test
	public void getUserListTest() throws ServiceException {
		User user=new User();
		user.setUserId(2l);
		user.setUserName("pavan");
		user.setUserCode("202");
		user.setPassword("kumar");
		Active a=new Active();
		a.setActiveId(1l);
		user.setActive(a);
		UserDTO userDto=new UserDTO();
		userDto.setUserId(2l);
		userDto.setUserName("pavan");
		userDto.setUserCode("202");
		userDto.setPassword("kumar");
		when(mapper.map(Mockito.any(User.class), Mockito.any(Class.class))).thenReturn(userDto);
		Page<UserDTO> dto = Mockito.mock(Page.class);
		Pageable pageable = new PageRequest(0, 6);
		Mockito.when(userRepo.findAll(Mockito.any(Specification.class),Mockito.any(Pageable.class))).thenReturn(dto);
		Page<UserDTO> userList = userService.getUserList("pavan", pageable);
		assertEquals(1, userList.getSize());
		
	}*/	
}
