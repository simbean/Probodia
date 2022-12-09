package com.probodia.challengeservice.api.controller;

import com.probodia.challengeservice.api.exception.IllegalJoinException;
import com.probodia.challengeservice.api.service.ChallengeJoinService;
import com.probodia.challengeservice.api.service.ChallengeService;
import com.probodia.challengeservice.api.dto.challengejoin.ChallengeJoinResponse;
import com.probodia.challengeservice.api.dto.challengerecord.ChallengeRecordResponse;
import com.probodia.challengeservice.auth.token.AuthTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/challenge/participant")
@Slf4j
@RequiredArgsConstructor
@Api(value = "Challenge Join Controller", description = "챌린지 참가 신청과 관련된 API")
public class ChallengeJoinController {

    private final ChallengeJoinService challengeJoinService;
    private final ChallengeService challengeService;
    private final AuthTokenProvider tokenProvider;

    @PostMapping
    @ApiOperation(value = "챌린지 참가", notes = "User id와 challenge id로 챌린지 참가한다.")
    public ResponseEntity<ChallengeJoinResponse> joinChallenge(@RequestHeader(value = "Authorization")String token,
                                                               @Valid @RequestBody Long challengeId) throws ParseException {

        //검증 - 현재 날짜가 challenge의 stdate보다 더 작은지 검증한다.
        String stdate = challengeService.getStDateById(challengeId);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse(stdate);
        Date curDate = new Date();

        if(curDate.after(startDate)){
            throw new IllegalJoinException("Date Error!");
        }

        ChallengeJoinResponse ret = challengeJoinService.save(challengeId, getUserByToken(token));

        return ResponseEntity.status(HttpStatus.CREATED).body(ret);
    }

    @GetMapping("/getAllActivity/{challengeId}")
    @ApiOperation(value = "challengeId로 특정 챌린지에 대한 기록 조회" , notes = "특정 챌린지 하나에 대한 자신이 한 모든 기록을 조회한다.")
    public ResponseEntity<List<ChallengeRecordResponse>> getAllActivity(@RequestHeader(value = "Authorization")String token, @PathVariable Long challengeId){

        List<ChallengeRecordResponse> ret = challengeJoinService.getAllActivity(challengeId, getUserByToken(token));

        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    private String getUserByToken(String bearerToken){

        return tokenProvider.getTokenSubject(bearerToken.substring(7));
    }

}
