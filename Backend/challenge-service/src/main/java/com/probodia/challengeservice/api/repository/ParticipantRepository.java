package com.probodia.challengeservice.api.repository;


import com.probodia.challengeservice.api.entity.challenge.Challenge;
import com.probodia.challengeservice.api.entity.challenge.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByUserIdAndChallenge(String userId, Challenge challenge);
    List<Participant> findAllByChallenge(Challenge challenge);
}
