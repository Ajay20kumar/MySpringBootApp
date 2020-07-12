package com.centerpoint.servicehelpdesk.repoDao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centerpoint.servicehelpdesk.entity.DepartmentEntity;
import com.centerpoint.servicehelpdesk.entity.ParkedReason;

@Repository
public interface ParkedReasonRepo extends JpaRepository<ParkedReason, Long> {
	
	List<ParkedReason> findByDepartmentEntity(DepartmentEntity departmentEntity);

}
