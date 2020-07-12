package com.centerpoint.servicehelpdesk.serviceImpl;

import static org.springframework.data.jpa.domain.Specification.where;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.centerpoint.servicehelpdesk.service.UserService;
import com.centerpoint.servicehelpdesk.specifications.AnouncementSpec;
import com.centerpoint.servicehelpdesk.specifications.UserSpec;
import com.centerpoint.servicehelpdesk.util.BeanCollectionMapper;
import com.centerpoint.servicehelpdesk.util.EmailSendingManager;
import com.centerpoint.servicehelpdesk.util.EmailServiceInter;
import com.centerpoint.servicehelpdesk.util.MailMapper;


@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AnouncementRepo anouncementRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserLocationRepo userLocationRepo;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private EmailServiceInter serviceInter;

	private static Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Override
	public UserDTO saveUser(User user) throws ServiceException {
		UserDTO dto = null;
		User userCode=null;
		try {
			 userCode = userRepo.findUserByuserCode(user.getUserCode());
			if (userCode == null) {
				BCryptPasswordEncoder encryptpwd = new BCryptPasswordEncoder();
				String encodepwd = encryptpwd.encode(user.getPassword());
				user.setPassword(encodepwd);
				User u = userRepo.save(user);
				dto = mapper.map(u, UserDTO.class);
			}
		} catch (Exception e) {
			logger.debug("Unable to fetch the saveUser Method" + e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dto;
	}

	@Override
	public User getUserDetails(String ssoId) throws ServiceException {
		User user = null;
		try {
			user = userRepo.findUserByuserCode(ssoId);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getUserDetails Method" + e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return user;
	}

	@Override
	public List<UserLocationDTO> getUserLocation() throws ServiceException {
		List<UserLocationDTO> list1 = null;
		try {
			List<UserLocation> list = userLocationRepo.findAll();
			list1 = BeanCollectionMapper.mapList(mapper, list, UserLocationDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getUserLocation Method" + e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return list1;
	}

	private UserDTO convertToUserDto(User o) {
		UserDTO dto = null;
		try {
			ModelMapper mapper = new ModelMapper();
			dto = mapper.map(o, UserDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the convertToUserDto Method" + e);
		}
		return dto;
	}

	@Override
	public Page<UserDTO> getUserList(String searchText, Pageable pageable) throws ServiceException {
		Page<UserDTO> s = null;
		try {
			Long a = 1l;
			Page<User> p = userRepo.findAll(where(UserSpec.getactive(a)).and(UserSpec.searchName(searchText)),
					pageable);
			s = p.map(this::convertToUserDto);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getUserList Method" + e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return s;
	}

	@Override
	public Integer saveanouncement(List<AnouncementEntity> anouncementEntity) throws ServiceException {
		Integer a = 0;
		for (int i = 0; i < anouncementEntity.size(); i++) {
			anouncementRepo.save(anouncementEntity.get(i));
			a = 1;
		}
		return a;
	}

	@Override
	public List<AnouncementDTO> getanouncements(Long locationId) throws ServiceException {
		List<AnouncementDTO> anou = new ArrayList<AnouncementDTO>();
		try {
			UserLocation location = new UserLocation();
			location.setLocationId(locationId);
			List<AnouncementEntity> list = anouncementRepo.findByuserLocation(location,
					Sort.by("anouncementId").descending());
			
			for (AnouncementEntity anouncementEntity : list) {
				
				if (anouncementEntity.getEndDate().compareTo(new Date()) > 0) {
					AnouncementDTO dto = mapper.map(anouncementEntity, AnouncementDTO.class);
					anou.add(dto);
				}
			}
		} catch (Exception e) {
			logger.debug("Unable to fetch the getanouncements Method" + e);
			e.printStackTrace();
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return anou;
	}

	@Override
	public UserDTO getUser(Long userId) throws ServiceException {
		UserDTO dto = null;
		
		try {
			User user = userRepo.getOne(userId);
			dto = mapper.map(user, UserDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getUser Method" + e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dto;
	}

	private AnouncementDTO convertToAnouncementDto(AnouncementEntity o) {
		AnouncementDTO dto = null;
		try {
			dto = mapper.map(o, AnouncementDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the convertToAnouncementDto Method" + e);
		}
		return dto;
	}

	@Override
	public Boolean deleteanouncement(Long anouncementId) throws ServiceException {
		Boolean b = false;
		try {
			Active a = new Active();
			a.setActiveId(2l);
			AnouncementEntity one = anouncementRepo.getOne(anouncementId);
			one.setActive(a);
			anouncementRepo.save(one);
			b = true;
		} catch (Exception e) {
			logger.debug("Unable to fetch the deleteanouncement Method" + e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return b;
	}

	@Override
	public Boolean disableUserById(Long userId) throws ServiceException {
		Boolean b = false;
		try {
			User udata = userRepo.getOne(userId);
			Active active = new Active();
			active.setActiveId(2l);
			udata.setActive(active);
			userRepo.save(udata);
			b = true;
		} catch (Exception e) {
			logger.debug("Unable to fetch the disableUserById Method" + e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return b;
	}

	@Override
	public UserDTO editUser(User user) throws ServiceException {
		UserDTO dto = null;
		try {
			User data = userRepo.getOne(user.getUserId());
			user.setPassword(data.getPassword());
			user.setActive(data.getActive());
			User save = userRepo.save(user);
			dto = mapper.map(save, UserDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the editUser Method" + e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dto;
	}

	@Override
	public UserDTO getUserByEmail(String empid, String url) throws ServiceException, MessagingException {
		UserDTO dto = null;
		try {
			User user = userRepo.findUserByuserCode(empid);

			if (user != null) {
				User resetUser = userRepo.findUserByuserCode(empid);
				resetUser.setResetToken(UUID.randomUUID().toString());
				User save = userRepo.save(resetUser);
				String forgoturl = url + "?empid=" + resetUser.getUserCode() + "&token=" + resetUser.getResetToken();
				String content = "<html><head><title><h3>Reset Password</h3></title></head><body><table><th colspan=1>Reset Password Details</th>"
						+ "<tr><td>Hello Sir/Madam</td></tr>"
						+ "<tr><td>Someone, hopefully you, has requested to reset the password </td></tr>"
						+ "<tr><td>for your ServiceHelpDesk account.</td></tr>"
						+ "<tr><td>If you did not perform this request, you can safely ignore this email.</td></tr>"
						+ "<tr><td>Otherwise, click the link below to complete the process.</td></tr>" + "</td></tr>"
						+ "<tr><td colspan=2><a href=" + forgoturl
						+ ">Please Click On The Link</a></td></tr></table></body></html>";
				
				//StringBuilder builder = new StringBuilder();
				Boolean emailStatus = null;
				EmailSendingManager mailSenderImpl = new EmailSendingManager();
				MailMapper mp = new MailMapper();
				mp.setTo(resetUser.getUserEmail());
				mp.setFrom("team@leonia.co.in");
				mp.setContent(content);
				mp.setSubject("Reset Password For EmployeeCode " + empid);
				mp.setFromname("Leonia");
				emailStatus = EmailSendingManager.mailSender(mp);
				dto = mapper.map(save, UserDTO.class);
				/*MimeMessage mimemessage = emailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimemessage, true);
				helper.setSubject("Reset Password For EmployeeCode " + empid);

				helper.setTo(resetUser.getUserEmail());
				String htmlMsg = "<html><head><title><h3>Reset Password</h3></title></head><body><table><th colspan=1>Reset Password Details</th>"
						+ "<tr><td>Hello Sir/Madam</td></tr>"
						+ "<tr><td>Someone, hopefully you, has requested to reset the password </td></tr>"
						+ "<tr><td>for your ServiceHelpDesk account.</td></tr>"
						+ "<tr><td>If you did not perform this request, you can safely ignore this email.</td></tr>"
						+ "<tr><td>Otherwise, click the link below to complete the process.</td></tr>" + "</td></tr>"
						+ "<tr><td colspan=2><a href=" + forgoturl
						+ ">Please Click On The Link</a></td></tr></table></body></html>";
				mimemessage.setContent(htmlMsg, "text/html");
				mimemessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(resetUser.getUserEmail()));
				serviceInter.sendmail(mimemessage);
				dto = mapper.map(user, UserDTO.class);*/
			}
		} catch (Exception e) {
			logger.debug("Unable to fetch the getUserByEmail Method" + e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dto;
	}

	@Override
	public UserDTO newPassword(String token, String password) throws ServiceException {
		UserDTO dto = null;
		try {
			User user = userRepo.findUserByResetToken(token);// findUserByUserEmail(email);
			if (user != null) {
				BCryptPasswordEncoder encryptpwd = new BCryptPasswordEncoder();
				String encodepwd = encryptpwd.encode(password);
				user.setPassword(encodepwd);
				userRepo.save(user);
				dto = mapper.map(user, UserDTO.class);
			}

		} catch (Exception e) {
			logger.debug("Unable to fetch the newPassword Method" + e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dto;
	}

	@Override
	public Page<AnouncementDTO> anouncementGrid(String searchText, Pageable pageable) throws ServiceException {
		Page<AnouncementDTO> s = null;
		try {
			Long a = 1l;
			Page<AnouncementEntity> p = anouncementRepo
					.findAll(where(AnouncementSpec.getactive(a)).and(AnouncementSpec.searchName(searchText)), pageable);
			s = p.map(this::convertToAnouncementDto);
		} catch (Exception e) {
			logger.debug("Unable to fetch the anouncementGrid Method" + e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return s;
	}

	@Override
	public List<UserDTO> getUserByDepartmentId(Long departmentId) throws ServiceException {
		List<UserDTO> dto = null;
		try {
			DepartmentEntity departmentEntity = new DepartmentEntity();
			departmentEntity.setDepartmentId(departmentId);
			List<User> user = userRepo.findByDepartmentEntity(departmentEntity);
			dto = BeanCollectionMapper.mapList(mapper, user, UserDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getUserByDepartmentId Method" + e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dto;
	}

	@Override
	public List<UserDTO> getagentUserByDepartmentId(Long departmentId) throws ServiceException {
		List<UserDTO> dto = null;
		try {
			DepartmentEntity departmentEntity = new DepartmentEntity();
			departmentEntity.setDepartmentId(departmentId);
			UserRoles r = new UserRoles();
			r.setRoleId(3l);
			List<User> user = userRepo.findByUserRolesAndDepartmentEntity(r, departmentEntity);
			dto = BeanCollectionMapper.mapList(mapper, user, UserDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getagentUserByDepartmentId Method" + e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dto;
	}

	@Override
	public void updatePassword(User userdata) throws ServiceException {
		userRepo.save(userdata);
	}

	@Override
	public List<UserDTO> getAgentsList() throws ServiceException {
		List<UserDTO> dto = null;
		try {
		UserRoles roles = new UserRoles();
		roles.setRoleId(3l);
		
		List<User> list = userRepo.findByUserRoles(roles);
		dto = BeanCollectionMapper.mapList(mapper, list, UserDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getAgentsList Method" + e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dto;
	}
}
