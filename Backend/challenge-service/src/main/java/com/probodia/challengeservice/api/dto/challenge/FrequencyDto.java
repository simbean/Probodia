package com.probodia.challengeservice.api.dto.challenge;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@ApiModel(value = "챌린지 주기 관련")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FrequencyDto {

    @ApiModelProperty(value = "년/월/주", required = true,example = "년")
    @NotNull(message = "Frequency-dateType cannot be null")
    private Character dateType;

    @ApiModelProperty(value = "000년/월/주에", required = true,example = "1")
    @NotNull(message = "Frequency-period cannot be null")
    private Integer period;

    @ApiModelProperty(value = "000번", required = true,example = "2")
    @NotNull(message = "Frequency-times cannot be null")
    private Integer times;



}
