package com.probodia.challengeservice.api.dto.challengejoin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "챌린지 참가 응답")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChallengeJoinResponse {

    @ApiModelProperty(value = "userId", required = true,example = "123123123")
    private String userId;

    @ApiModelProperty(value = "챌린지 Id", required = true,example = "2")
    private Long challengeId;

    @ApiModelProperty(value = "참가 Id", required = true,example = "2")
    private Long participantId;


}
