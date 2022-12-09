package com.probodia.challengeservice.api.service;

import com.probodia.challengeservice.api.entity.challenge.Caution;
import com.probodia.challengeservice.api.repository.CautionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CautionService {

    private final CautionRepository cautionRepository;


    public Caution saveCaution(Caution c){
//        Caution c = new Caution();
//        c.setContent(content);
        return cautionRepository.save(c);
    }

}
