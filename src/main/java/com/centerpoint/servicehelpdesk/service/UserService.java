package com.centerpoint.servicehelpdesk.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centerpoint.servicehelpdesk.dto.AnouncementDTO;
import com.centerpoint.servicehelpdesk.dto.UserDTO;
import com.centerpoint.servicehelpdesk.dto.UserLocationDTO;
import com.centerpoint.servicehelpdesk.entity.AnouncementEntity;
import com.centerpoint.servicehelpdesk.entity.User;
import com.centerpoint.servicehelpdesk.exception.ServiceException;

public interface UserService {

	UserDTO saveUser(@Valid User user) throws ServiceException;

	User getUserDetails(String ssoId) throws ServiceException;

	List<UserLocationDTO> getUserLocation() throws ServiceException;

	Page<UserDTO> getUserList(String searchText, Pageable pageable) throws ServiceException;

	List<AnouncementDTO> getanouncements(Long locationId) throws ServiceException;

	Integer saveanouncement(List<AnouncementEntity> anouncementEntity) throws ServiceException;

	UserDTO getUser(Long userId) throws ServiceException;

	//Page<AnouncementDTO> anouncementGrid(Pageable pageable);

	Boolean deleteanouncement(Long anouncementId) throws ServiceException;

	Boolean disableUserById(Long userId) throws ServiceException;

	UserDTO editUser(User user) throws ServiceException;

	UserDTO getUserByEmail(String empid, String url) throws ServiceException, MessagingException;

	UserDTO newPassword(String token, String password) throws ServiceException;

	Page<AnouncementDTO> anouncementGrid(String searchText, Pageable pageable) throws ServiceException;

	List<UserDTO> getUserByDepartmentId(Long departmentId) throws ServiceException;

	void updatePassword(User userdata) throws ServiceException;

	List<UserDTO> getagentUserByDepartmentId(Long departmentId) throws ServiceException;

	List<UserDTO> getAgentsList() throws ServiceException;

}
