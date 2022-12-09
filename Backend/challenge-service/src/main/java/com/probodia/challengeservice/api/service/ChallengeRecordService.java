package com.probodia.challengeservice.api.service;

import com.probodia.challengeservice.api.entity.challenge.Activity;
import com.probodia.challengeservice.api.entity.challenge.Participant;
import com.probodia.challengeservice.api.mapper.ChallengeRecordMapper;
import com.probodia.challengeservice.api.repository.ActivityRepository;
import com.probodia.challengeservice.api.dto.challengerecord.ChallengeRecordRequest;
import com.probodia.challengeservice.api.dto.challengerecord.ChallengeRecordResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChallengeRecordService {

    private final ActivityRepository activityRepository;
    private final ChallengeJoinService challengeJoinService;

    @Transactional
    public ChallengeRecordResponse createChallengeRecord(String userId, ChallengeRecordRequest request) {

        Optional<Participant> participant = challengeJoinService.findByChallengeAndUser(request.getChallengeId(), userId);
        if(participant.isEmpty()) throw new IllegalArgumentException("Not joined challenge");

        Set<Activity> activities = participant.get().getActivities();
        if(activities== null) activities = new HashSet<>();

        Activity activity = new Activity();
        activity.setContent(request.getContent());
        activity.setImageUrl(request.getImageUrl());

        Activity saved = activityRepository.save(activity);
        activities.add(saved);

        participant.get().setActivities(activities);
        Integer achievement = participant.get().getAchievement();
        participant.get().setAchievement(achievement+1);

        return ChallengeRecordMapper.INSTANCE.entityToDto(activity, request.getChallengeId());
    }

}
