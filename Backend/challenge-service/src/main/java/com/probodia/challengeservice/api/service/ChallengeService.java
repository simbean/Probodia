package com.probodia.challengeservice.api.service;

import com.probodia.challengeservice.api.entity.challenge.Caution;
import com.probodia.challengeservice.api.entity.challenge.Challenge;
import com.probodia.challengeservice.api.entity.challenge.Participant;
import com.probodia.challengeservice.api.mapper.ChallengeMapper;
import com.probodia.challengeservice.api.repository.ChallengeRepository;
import com.probodia.challengeservice.api.dto.challenge.ChallengeCreateRequest;
import com.probodia.challengeservice.api.dto.challenge.ChallengeResponse;
import com.probodia.challengeservice.api.repository.ParticipantRepository;
import com.probodia.challengeservice.config.PointConfig;
import com.probodia.challengeservice.feignclient.UserServiceClient;
import com.probodia.challengeservice.messagequeue.RabbitProducer;
import com.probodia.challengeservice.messagequeue.entity.PointVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final CautionService cautionService;

    private final RabbitProducer rabbitProducer;
    private final UserServiceClient client;
    private final ParticipantRepository participantRepository;
    private final PointConfig pointConfig;

    @Transactional
    public String getStDateById(Long id){
        Challenge challenge = challengeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No such Challenge"));
        return challenge.getStDate();
    }

    @Transactional
    public void processChallengeAfter(){

        String curDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        log.info(curDate);

        List<Challenge> allByEnDate = challengeRepository.findAllByEnDate(curDate);

        for(Challenge c : allByEnDate){
            List<Participant> parts = participantRepository.findAllByChallenge(c);
            for(Participant p : parts){
                if(p.getAchievement() == c.getTotalCnt()){
                    client.setPoint(pointConfig.getPointPolicy(), p.getUserId());
                }
            }
        }

//        rabbitProducer.sendPoint(new PointVO("2343341101",100));
//        client.setPoint(100, "2343341101");

    }

    @Transactional
    public ChallengeResponse save(ChallengeCreateRequest request) {

        Challenge challenge = ChallengeMapper.INSTANCE.dtoToEntity(request);
        log.info("Make challenge : {}", challenge);
        challenge.getCautions().forEach(r ->{
            log.info("Caution : {}",r);
        });

        Set<Caution> savedCaution = new HashSet<>();

        challenge.getCautions().forEach(r -> {
            Caution caution = cautionService.saveCaution(r);
            log.info("Before : {}",caution);
            savedCaution.add(caution);
        });

        challenge.setCautions(savedCaution);

        Challenge saved = challengeRepository.save(challenge);


        return ChallengeMapper.INSTANCE.entityToDto(saved);
    }

    @Transactional
    public List<ChallengeResponse> findAll(){
        List<ChallengeResponse> ret = new ArrayList<>();
        challengeRepository.findAll().forEach(r -> ret.add(ChallengeMapper.INSTANCE.entityToDto(r)));

        return ret;
    }

    @Transactional
    public Optional<Challenge> findById(Long id){
        return challengeRepository.findById(id);
    }


}
