package com.probodia.challengeservice.api.repository;

import com.probodia.challengeservice.api.entity.challenge.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {
}
