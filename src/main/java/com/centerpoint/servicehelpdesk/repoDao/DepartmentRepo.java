package com.centerpoint.servicehelpdesk.repoDao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centerpoint.servicehelpdesk.entity.BranchesEntity;
import com.centerpoint.servicehelpdesk.entity.DepartmentEntity;

@Repository
public interface DepartmentRepo extends JpaRepository<DepartmentEntity, Long> {

	List<DepartmentEntity> findAllByBranchesEntity(BranchesEntity branchesEntity);
	List<DepartmentEntity> findByDepartmentId(Long departmentId);
}
