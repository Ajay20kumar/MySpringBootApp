package com.centerpoint.servicehelpdesk.service;

import java.util.List;

import com.centerpoint.servicehelpdesk.dto.BranchDTO;
import com.centerpoint.servicehelpdesk.dto.CityDTO;
import com.centerpoint.servicehelpdesk.dto.CountryDTO;
import com.centerpoint.servicehelpdesk.dto.DepartmentDTO;
import com.centerpoint.servicehelpdesk.dto.OrganizationDTO;
import com.centerpoint.servicehelpdesk.dto.ParkedReasonDTO;
import com.centerpoint.servicehelpdesk.dto.ServiceCategoryDTO;
import com.centerpoint.servicehelpdesk.dto.StateDTO;
import com.centerpoint.servicehelpdesk.entity.BranchesEntity;
import com.centerpoint.servicehelpdesk.entity.City;
import com.centerpoint.servicehelpdesk.entity.Country;
import com.centerpoint.servicehelpdesk.entity.DepartmentEntity;
import com.centerpoint.servicehelpdesk.entity.OrganizationEntity;
import com.centerpoint.servicehelpdesk.entity.ServiceCategories;
import com.centerpoint.servicehelpdesk.entity.Stages;
import com.centerpoint.servicehelpdesk.entity.State;
import com.centerpoint.servicehelpdesk.entity.Status;
import com.centerpoint.servicehelpdesk.entity.UserRoles;
import com.centerpoint.servicehelpdesk.exception.ServiceException;

public interface MasterService {

	List<UserRoles> getUserRole() throws ServiceException;

	State saveState(State state) throws ServiceException;

	City saveCity(City city) throws ServiceException;

	List<CityDTO> getCity() throws ServiceException;

	StateDTO getStatebyCityId(Long cityId) throws ServiceException;

	CountryDTO getCountryStateId(Long stateId) throws ServiceException;

	OrganizationEntity saveOrganization(OrganizationEntity organizationEntity) throws ServiceException;

	List<OrganizationDTO> getOrganization() throws ServiceException;

	BranchesEntity saveBranch(BranchesEntity branchesEntity) throws ServiceException;

	List<BranchDTO> getBranchbyOrganizationId(Long organizationId) throws ServiceException;

	DepartmentEntity saveDepartment(DepartmentEntity departmentEntity) throws ServiceException;

	List<DepartmentDTO> getDepartmentbyBranchId(Long branchId) throws ServiceException;

	ServiceCategories saveServiceCategories(ServiceCategories serviceCategories) throws ServiceException;

	Country saveCountry(Country country) throws ServiceException;

	List<DepartmentDTO> getDepartment() throws ServiceException;

	List<ServiceCategoryDTO> getServiceCategory(Long departmentId) throws ServiceException;

	List<Status> getStatus() throws ServiceException;

	List<ParkedReasonDTO> getparkedreason(Long departmentId) throws ServiceException;

	List<Stages> getStages() throws ServiceException;

	List<DepartmentDTO> getDepartmentId(Long departmentId) throws ServiceException;

}
