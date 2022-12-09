package com.probodia.challengeservice.api.dto.challenge;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;
@ApiModel(value = "챌린지 생성 응답")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChallengeResponse {
    @ApiModelProperty(value = "챌린지 ID", required = true,example = "true")
    private Long id;

    @ApiModelProperty(value = "공식 챌린지 플래그", required = true,example = "true")
    private Boolean official;

    @ApiModelProperty(value = "챌린지 이름", required = true,example = "혈당 기록하기")
    private String name;

    @ApiModelProperty(value = "챌린지 상세 설명", required = true,example = "혈당을 기록해서 포인트를 모아보세요!")
    private String content;

    @ApiModelProperty(value = "챌린지 획득 포인트", required = true,example = "123")
    private Integer earnPoint;

    @ApiModelProperty(value = "100%에 도달하기 위해 달성해야 하는 기록 수", required = true,example = "123")
    private Integer totalCnt;

    @ApiModelProperty(value = "시작 날짜", example = "2022-10-09")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String stDate;

    @ApiModelProperty(value = "끝나는 날짜", example = "2022-10-18")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String enDate;

    @ApiModelProperty(value = "빈도", example = "1주 2")
    private FrequencyDto frequency;

    @ApiModelProperty(value = "주의사항들", example = "안하면 안됩니다.")
    List<String> caution;

}
