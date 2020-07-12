package com.centerpoint.servicehelpdesk.repoDao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centerpoint.servicehelpdesk.entity.DepartmentEntity;
import com.centerpoint.servicehelpdesk.entity.User;
import com.centerpoint.servicehelpdesk.entity.UserRoles;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	User findUserByuserCode(String ssoId);
	
	Page<User> findAll(Specification<User> specification, Pageable pageable);
	
	User findUserByResetToken(String token);

	List<User> findByDepartmentEntity(DepartmentEntity departmentEntity);
	
	List<User> findByUserRolesAndDepartmentEntity(UserRoles roles,DepartmentEntity departmentEntity);
	
	List<User> findByUserRoles(UserRoles roles);
}
