package com.centerpoint.servicehelpdesk.repoDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centerpoint.servicehelpdesk.entity.UserRoles;
@Repository
public interface UserRoleRepo  extends JpaRepository<UserRoles, Long>{

}
