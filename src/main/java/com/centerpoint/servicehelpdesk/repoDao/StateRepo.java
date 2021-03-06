package com.centerpoint.servicehelpdesk.repoDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centerpoint.servicehelpdesk.entity.State;


@Repository
public interface StateRepo extends JpaRepository<State, Long> {
	

}
