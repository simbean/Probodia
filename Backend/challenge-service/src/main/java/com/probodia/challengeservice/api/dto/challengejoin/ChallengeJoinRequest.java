package com.probodia.challengeservice.api.dto.challengejoin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@ApiModel(value = "챌린지 참가 요청")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChallengeJoinRequest {

    @ApiModelProperty(value = "userId", required = true,example = "123123123")
    @NotNull(message = "UserId cannot be null")
    private String userId;

    @ApiModelProperty(value = "챌린지 Id", required = true,example = "2")
    @NotNull(message = "ChallengeId")
    private Long challengeId;

}
