package com.probodia.challengeservice.api.repository;

import com.probodia.challengeservice.api.entity.challenge.Caution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CautionRepository extends JpaRepository<Caution, Long> {
}
