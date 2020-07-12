package com.centerpoint.servicehelpdesk.serviceImpl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.centerpoint.servicehelpdesk.entity.ParkedReason;
import com.centerpoint.servicehelpdesk.entity.ServiceCategories;
import com.centerpoint.servicehelpdesk.entity.Stages;
import com.centerpoint.servicehelpdesk.entity.State;
import com.centerpoint.servicehelpdesk.entity.Status;
import com.centerpoint.servicehelpdesk.entity.UserRoles;
import com.centerpoint.servicehelpdesk.exception.ServiceException;
import com.centerpoint.servicehelpdesk.repoDao.BranchRepo;
import com.centerpoint.servicehelpdesk.repoDao.CityRepo;
import com.centerpoint.servicehelpdesk.repoDao.CountryRepo;
import com.centerpoint.servicehelpdesk.repoDao.DepartmentRepo;
import com.centerpoint.servicehelpdesk.repoDao.OrganizationRepo;
import com.centerpoint.servicehelpdesk.repoDao.ParkedReasonRepo;
import com.centerpoint.servicehelpdesk.repoDao.ServiceCategoryRepo;
import com.centerpoint.servicehelpdesk.repoDao.StagesRepo;
import com.centerpoint.servicehelpdesk.repoDao.StateRepo;
import com.centerpoint.servicehelpdesk.repoDao.StatusRepo;
import com.centerpoint.servicehelpdesk.repoDao.UserRoleRepo;
import com.centerpoint.servicehelpdesk.service.MasterService;
import com.centerpoint.servicehelpdesk.util.BeanCollectionMapper;

@Service
@Transactional
public class MasterServiceImpl implements MasterService {

	@Autowired
	private OrganizationRepo organizationRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private DepartmentRepo departmentRepo;

	@Autowired
	private ServiceCategoryRepo serviceCategoryRepo;

	@Autowired
	private CityRepo cityRepo;

	@Autowired
	private StateRepo stateRepo;

	@Autowired
	private CountryRepo countryRepo;

	@Autowired
	private UserRoleRepo userRoleRepo;

	@Autowired
	private StatusRepo ststusRepo;

	@Autowired
	private StagesRepo stagesRepo;

	@Autowired
	private ParkedReasonRepo parkedReasonRepo;

	@Autowired
	private ModelMapper mapper;
	
	private static Logger logger = LogManager.getLogger(MasterServiceImpl.class);

	@Override
	public List<UserRoles> getUserRole() throws ServiceException {
		List<UserRoles> findAll = null;
		try {
			findAll = userRoleRepo.findAll();
		} catch (Exception e) {
			logger.debug("Unable to fetch the getUserRole Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return findAll;
	}

	@Override
	public Country saveCountry(Country country) throws ServiceException {
		Country c = null;
		try {
			c = countryRepo.save(country);
		} catch (Exception e) {
			logger.debug("Unable to fetch the saveCountry Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return c;
	}

	@Override
	public State saveState(State state) throws ServiceException {
		State s = null;
		try {
			s = stateRepo.save(state);
		} catch (Exception e) {
			logger.debug("Unable to fetch the saveState Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return s;
	}

	@Override
	public City saveCity(City city) throws ServiceException {
		City c = null;
		try {
			c = cityRepo.save(city);
		} catch (Exception e) {
			logger.debug("Unable to fetch the saveCity Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return c;
	}

	@Override
	public List<CityDTO> getCity() throws ServiceException {
		List<CityDTO> dto = null;
		try {
			List<City> list = cityRepo.findAll();
			dto = BeanCollectionMapper.mapList(mapper, list, CityDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getCity Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dto;
	}

	@Override
	public StateDTO getStatebyCityId(Long cityId)  throws ServiceException {
		StateDTO s = null;
		try {
			State state = stateRepo.getOne(cityId);
			if(state==null)
			{
				throw new ServiceException("hihihi", HttpStatus.NOT_FOUND);
			}
			s = mapper.map(state, StateDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getStatebyCityId Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return s;
	}

	@Override
	public CountryDTO getCountryStateId(Long stateId) throws ServiceException {
		CountryDTO c = null;
		try {
			Country country = countryRepo.getOne(stateId);
			c = mapper.map(country, CountryDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getCountryStateId Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return c;
	}

	@Override
	public OrganizationEntity saveOrganization(OrganizationEntity organizationEntity) throws ServiceException {
		OrganizationEntity o = null;
		try {
			o = organizationRepo.save(organizationEntity);
		} catch (Exception e) {
			logger.debug("Unable to fetch the saveOrganization Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return o;
	}

	@Override
	public List<OrganizationDTO> getOrganization() throws ServiceException {
		List<OrganizationDTO> dto = null;
		try {
			List<OrganizationEntity> list = organizationRepo.findAll();
			dto = BeanCollectionMapper.mapList(mapper, list, OrganizationDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getOrganization Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dto;
	}

	@Override
	public BranchesEntity saveBranch(BranchesEntity branchesEntity) throws ServiceException {
		BranchesEntity b = null;
		try {
			b = branchRepo.save(branchesEntity);
		} catch (Exception e) {
			logger.debug("Unable to fetch the saveBranch Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return b;
	}

	@Override
	public List<BranchDTO> getBranchbyOrganizationId(Long organizationId) throws ServiceException {
		List<BranchDTO> dto = null;
		try {
			OrganizationEntity organizationEntity = new OrganizationEntity();
			organizationEntity.setOrganizationId(organizationId);
			List<BranchesEntity> branchesEntities = branchRepo.findAllByOrganizationEntity(organizationEntity);
			dto = BeanCollectionMapper.mapList(mapper, branchesEntities, BranchDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getBranchbyOrganizationId Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dto;
	}

	@Override
	public DepartmentEntity saveDepartment(DepartmentEntity departmentEntity) throws ServiceException {
		DepartmentEntity d = null;
		try {
			d = departmentRepo.save(departmentEntity);
		} catch (Exception e) {
			logger.debug("Unable to fetch the saveDepartment Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return d;
	}

	@Override
	public List<DepartmentDTO> getDepartmentbyBranchId(Long branchId) throws ServiceException {
		List<DepartmentDTO> dto = null;
		try {
		BranchesEntity branchesEntity = new BranchesEntity();
		branchesEntity.setBranchId(branchId);
		List<DepartmentEntity> list = departmentRepo.findAllByBranchesEntity(branchesEntity);
		dto = BeanCollectionMapper.mapList(mapper, list, DepartmentDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getDepartmentbyBranchId Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dto;
	}

	@Override
	public ServiceCategories saveServiceCategories(ServiceCategories serviceCategories) throws ServiceException {
		ServiceCategories s = null;
		try {
			s = serviceCategoryRepo.save(serviceCategories);
		} catch (Exception e) {
			logger.debug("Unable to fetch the saveServiceCategories Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return s;
	}

	@Override
	public List<DepartmentDTO> getDepartment() throws ServiceException {
		List<DepartmentDTO> dto = null;
		try {
			List<DepartmentEntity> list = departmentRepo.findAll();
			dto = BeanCollectionMapper.mapList(mapper, list, DepartmentDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getDepartment Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dto;
	}

	@Override
	public List<ServiceCategoryDTO> getServiceCategory(Long departmentId) throws ServiceException {
		List<ServiceCategoryDTO> dto = null;
		try {
		DepartmentEntity departmentEntity = new DepartmentEntity();
		departmentEntity.setDepartmentId(departmentId);
		List<ServiceCategories> serviceCategories = serviceCategoryRepo.findByDepartmentEntity(departmentEntity);
		dto = BeanCollectionMapper.mapList(mapper, serviceCategories, ServiceCategoryDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getServiceCategory Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dto;
	}

	@Override
	public List<Status> getStatus() throws ServiceException {
		List<Status> findAll = null;
		try {
			findAll = ststusRepo.findAll();
		} catch (Exception e) {
			logger.debug("Unable to fetch the getStatus Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return findAll;
	}

	@Override
	public List<ParkedReasonDTO> getparkedreason(Long departmentId) throws ServiceException {
		List<ParkedReasonDTO> dto = null;
		try {
		DepartmentEntity departmentEntity = new DepartmentEntity();
		departmentEntity.setDepartmentId(departmentId);
		List<ParkedReason> findByDepartmentEntity = parkedReasonRepo.findByDepartmentEntity(departmentEntity);
		dto = BeanCollectionMapper.mapList(mapper, findByDepartmentEntity, ParkedReasonDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getparkedreason Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dto;
	}

	@Override
	public List<Stages> getStages() throws ServiceException {
		List<Stages> stages = null;
		try {
			stages = stagesRepo.findAll();
		} catch (Exception e) {
			logger.debug("Unable to fetch the getStages Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return stages;
	}

	@Override
	public List<DepartmentDTO> getDepartmentId(Long departmentId) throws ServiceException {
		List<DepartmentDTO> dto = null;
		try {
		List<DepartmentEntity> list = departmentRepo.findByDepartmentId(departmentId);
		dto = BeanCollectionMapper.mapList(mapper, list, DepartmentDTO.class);
		} catch (Exception e) {
			logger.debug("Unable to fetch the getDepartmentId Method"+e);
			throw new ServiceException("Internal servar error occur", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return dto;
	}
}
