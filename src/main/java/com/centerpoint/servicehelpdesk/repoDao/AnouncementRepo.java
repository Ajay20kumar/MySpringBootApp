package com.centerpoint.servicehelpdesk.repoDao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centerpoint.servicehelpdesk.entity.AnouncementEntity;
import com.centerpoint.servicehelpdesk.entity.UserLocation;

@Repository
public interface AnouncementRepo extends JpaRepository<AnouncementEntity, Long>{

	List<AnouncementEntity> findByuserLocation(UserLocation location, Sort descending);
	Page<AnouncementEntity> findAll(Specification<AnouncementEntity> specification, Pageable pageable);
}
