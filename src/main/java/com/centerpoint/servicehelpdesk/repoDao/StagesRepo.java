package com.centerpoint.servicehelpdesk.repoDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centerpoint.servicehelpdesk.entity.Stages;


@Repository
public interface StagesRepo extends JpaRepository<Stages, Long> {

}
