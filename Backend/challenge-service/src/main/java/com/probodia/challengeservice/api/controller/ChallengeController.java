package com.probodia.challengeservice.api.controller;


import com.probodia.challengeservice.api.service.ChallengeService;
import com.probodia.challengeservice.api.dto.challenge.ChallengeCreateRequest;
import com.probodia.challengeservice.api.dto.challenge.ChallengeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/challenge")
@Slf4j
@RequiredArgsConstructor
@Api(value = "Challenge Controller", description = "챌린지 CRUD와 관련된 API")
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    @ApiOperation(value = "챌린지 생성", notes = "새로운 챌린지를 생성한다.")
    public ResponseEntity<ChallengeResponse> createChallenge(@Valid @RequestBody ChallengeCreateRequest request){

        ChallengeResponse ret = challengeService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ret);
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "챌린지 전체 목록 조회", notes = "활성화된 챌린지에 대해서 목록을 조회한다.")
    public ResponseEntity<List<ChallengeResponse>> getAllActiveChallenge(){

        List<ChallengeResponse> all = challengeService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(all);
    }

//    @GetMapping("/testPoint")
//    public String test(){
//
//        challengeService.processChallengeAfter();
//
//        return "ok";
//    }


}
