package com.probodia.challengeservice.api.service;

import com.probodia.challengeservice.api.entity.challenge.Challenge;
import com.probodia.challengeservice.api.entity.challenge.Participant;
import com.probodia.challengeservice.api.mapper.ChallengeRecordMapper;
import com.probodia.challengeservice.api.repository.ParticipantRepository;
import com.probodia.challengeservice.api.dto.challengejoin.ChallengeJoinResponse;
import com.probodia.challengeservice.api.dto.challengerecord.ChallengeRecordResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChallengeJoinService {

    private final ParticipantRepository participantRepository;
    private final ChallengeService challengeService;


    @Transactional
    public ChallengeJoinResponse save(Long challengeId, String userId) {

        //챌린지 존재 검증
        Optional<Challenge> challenge = challengeService.findById(challengeId);

        if(challenge.isEmpty()) throw new IllegalArgumentException("Not found Challenge with Id");
        //userid - challengeId 에서 이미 들어가 있는지 검증하고

        Optional<Participant> ispresent = participantRepository.findByUserIdAndChallenge(userId, challenge.get());
        if(ispresent.isPresent()) throw new IllegalArgumentException("Already Join Challenge");


        log.info("CHALLENGE : {}",challenge.get().getId());

        //entity 생성한다.
        Participant participant = new Participant();

        participant.setChallenge(challenge.get());
        participant.setUserId(userId);
        participant.setAchievement(0);

        log.info("AFTER : {}",participant.getChallenge().getId());

        Participant saved = participantRepository.saveAndFlush(participant);


        return new ChallengeJoinResponse(saved.getUserId(), saved.getChallenge().getId(),saved.getId());
    }

    @Transactional
    public Optional<Participant> findByChallengeAndUser(Long challengeId, String userId) {
        //챌린지 존재 검증
        Optional<Challenge> challenge = challengeService.findById(challengeId);

        if(challenge.isEmpty()) throw new IllegalArgumentException("Not found Challenge with Id");
        //userid - challengeId 에서 이미 들어가 있는지 검증하고

        Optional<Participant> ret = participantRepository.findByUserIdAndChallenge(userId, challenge.get());

        return ret;
    }

    @Transactional
    public List<ChallengeRecordResponse> getAllActivity(Long challengeId, String userId){

        List<ChallengeRecordResponse> ret = new ArrayList<>();

        Optional<Challenge> byId = challengeService.findById(challengeId);
        if(byId.isEmpty()) throw new IllegalArgumentException("No matching challenge with id");
        Optional<Participant> participant = participantRepository.findByUserIdAndChallenge(userId, byId.get());
        if(participant.isEmpty()) throw new IllegalArgumentException("No matching participant");
        participant.get().getActivities().forEach(r -> ret.add(ChallengeRecordMapper.INSTANCE.entityToDto(r,challengeId)));

        return ret;
    }
}
