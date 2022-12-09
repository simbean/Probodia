package com.probodia.challengeservice.api.dto.challengerecord;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "챌린지 기록 응답")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChallengeRecordResponse {


    @ApiModelProperty(value = "챌린지 기록 Id", required = true,example = "1")
    Long activityId;

    @ApiModelProperty(value = "챌린지 Id", required = true,example = "1")
    Long challengeId;

    @ApiModelProperty(value = "userId", required = true,example = "예상)먹은 음식 : {김치, 밥}")
    String content;

    @ApiModelProperty(value = "userId", required = true,example = "https://asdfa.com")
    String imageUrl;
}
