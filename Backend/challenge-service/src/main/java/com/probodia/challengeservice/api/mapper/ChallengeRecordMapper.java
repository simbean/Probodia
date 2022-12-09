package com.probodia.challengeservice.api.mapper;

import com.probodia.challengeservice.api.entity.challenge.Activity;
import com.probodia.challengeservice.api.dto.challengerecord.ChallengeRecordResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChallengeRecordMapper {

    ChallengeRecordMapper INSTANCE = Mappers.getMapper(ChallengeRecordMapper.class);

    default ChallengeRecordResponse entityToDto(Activity activity,Long challengeId){

        return new ChallengeRecordResponse(activity.getId(), challengeId,
                activity.getContent(), activity.getImageUrl());
    }

}
