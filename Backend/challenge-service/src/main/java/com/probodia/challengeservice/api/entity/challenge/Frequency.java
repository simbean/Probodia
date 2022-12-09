package com.probodia.challengeservice.api.entity.challenge;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Setter
@Getter
public class Frequency {

    //년 월 주
    private Character dateType;

    //000(년,월,주)에
    private Integer period;

    //000번
    private Integer times;
}
