package com.centerpoint.servicehelpdesk.repoDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centerpoint.servicehelpdesk.entity.Active;

@Repository
public interface ActiveRepo extends JpaRepository<Active, Long>{

}
