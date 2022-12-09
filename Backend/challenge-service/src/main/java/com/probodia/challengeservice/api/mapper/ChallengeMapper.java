package com.probodia.challengeservice.api.mapper;

import com.probodia.challengeservice.api.entity.challenge.Caution;
import com.probodia.challengeservice.api.entity.challenge.Challenge;
import com.probodia.challengeservice.api.entity.challenge.Frequency;
import com.probodia.challengeservice.api.dto.challenge.ChallengeCreateRequest;
import com.probodia.challengeservice.api.dto.challenge.ChallengeResponse;
import com.probodia.challengeservice.api.dto.challenge.FrequencyDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper
public interface ChallengeMapper {

    ChallengeMapper INSTANCE = Mappers.getMapper(ChallengeMapper.class);

    default Challenge dtoToEntity(ChallengeCreateRequest request){

        Challenge challenge = new Challenge();
        challenge.setContent(request.getContent());
        challenge.setEarnPoint(request.getEarnPoint());
        challenge.setEnDate(request.getEnDate());
        challenge.setName(request.getName());
        challenge.setOfficial(request.getOfficial());
        challenge.setStDate(request.getStDate());

        Set<Caution> cautionSet = new HashSet<>();

        request.getCaution().forEach(r ->{
            Caution c = new Caution();
            c.setContent(r);
            cautionSet.add(c);
        });

        challenge.setCautions(cautionSet);
//        challenge.setClosed(false);
        challenge.setActive(true);
        challenge.setParticipantCnt(0);
        challenge.setFrequency(new Frequency(request.getFrequency().getDateType(),
                request.getFrequency().getPeriod(), request.getFrequency().getTimes()));

        return challenge;
    }

    default ChallengeResponse entityToDto(Challenge challenge){

        ChallengeResponse ret = new ChallengeResponse();
        ret.setContent(challenge.getContent());
        ret.setEarnPoint(challenge.getEarnPoint());
        ret.setEnDate(challenge.getEnDate());
        ret.setName(challenge.getName());
        ret.setOfficial(challenge.getOfficial());
        ret.setStDate(challenge.getStDate());
        ret.setId(challenge.getId());

        List<String> caution = new ArrayList<>();

        challenge.getCautions().forEach(r -> {
            caution.add(r.getContent());
            System.out.println("CAUTION : "+r.getContent());
                });

        FrequencyDto frequency = new FrequencyDto();
        frequency.setPeriod(challenge.getFrequency().getPeriod());
        frequency.setTimes(challenge.getFrequency().getTimes());
        frequency.setDateType(challenge.getFrequency().getDateType());

        ret.setFrequency(frequency);
        ret.setCaution(caution);
        return ret;
    }

}
