package com.probodia.challengeservice.api.repository;

import com.probodia.challengeservice.api.entity.challenge.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge,Long> {

    List<Challenge> findAllByEnDate(String endate);
}
