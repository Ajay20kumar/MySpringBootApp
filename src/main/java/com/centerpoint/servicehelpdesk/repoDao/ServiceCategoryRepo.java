package com.centerpoint.servicehelpdesk.repoDao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centerpoint.servicehelpdesk.entity.DepartmentEntity;
import com.centerpoint.servicehelpdesk.entity.ServiceCategories;

@Repository
public interface ServiceCategoryRepo extends JpaRepository<ServiceCategories, Long> {
	
	List<ServiceCategories> findByDepartmentEntity(DepartmentEntity departmentEntity);


}
