package com.centerpoint.servicehelpdesk.repoDao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centerpoint.servicehelpdesk.entity.BranchesEntity;
import com.centerpoint.servicehelpdesk.entity.OrganizationEntity;

@Repository
public interface BranchRepo extends JpaRepository<BranchesEntity, Long>{

	List<BranchesEntity> findAllByOrganizationEntity(OrganizationEntity organizationEntity);

}
