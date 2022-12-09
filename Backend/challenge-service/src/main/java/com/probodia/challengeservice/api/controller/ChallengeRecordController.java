package com.probodia.challengeservice.api.controller;

import com.probodia.challengeservice.api.service.ChallengeRecordService;
import com.probodia.challengeservice.api.dto.challengerecord.ChallengeRecordRequest;
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

@RestController
@RequestMapping("/api/challenge/record")
@Slf4j
@RequiredArgsConstructor
@Api(value = "Challenge Record Controller", description = "챌린지 기록과 관련된 API")
public class ChallengeRecordController {

    private final ChallengeRecordService challengeRecordService;
    private final AuthTokenProvider tokenProvider;

    @PostMapping
    @ApiOperation(value = "챌린지 기록", notes = "content와 imageUrl로 챌린지를 기록한다.")
    public ResponseEntity<ChallengeRecordResponse> createChallengeRecord(@RequestHeader(value = "Authorization")String token,
            @Valid @RequestBody ChallengeRecordRequest request){

        String userId = getUserByToken(token);

        ChallengeRecordResponse ret = challengeRecordService.createChallengeRecord(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ret);
    }


    private String getUserByToken(String bearerToken){

        return tokenProvider.getTokenSubject(bearerToken.substring(7));
    }

}
