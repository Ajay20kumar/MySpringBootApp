package com.centerpoint.servicehelpdesk.repoDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centerpoint.servicehelpdesk.entity.OrganizationEntity;

@Repository
public interface OrganizationRepo extends JpaRepository<OrganizationEntity, Long> {

}
