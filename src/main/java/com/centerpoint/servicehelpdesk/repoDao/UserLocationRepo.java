package com.centerpoint.servicehelpdesk.repoDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centerpoint.servicehelpdesk.entity.UserLocation;


@Repository
public interface UserLocationRepo extends JpaRepository<UserLocation, Long> {

}
