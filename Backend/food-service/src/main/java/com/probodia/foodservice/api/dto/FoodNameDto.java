package com.probodia.foodservice.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "음식 검색 결과")
public class FoodNameDto {

    @ApiModelProperty(value = "음식 이름",example = "새우깡", required = true)
    @NotNull(message = "Food name cannot be null")
    private String foodName;
    @ApiModelProperty(value = "음식 ID",example = "음식 ID", required = true)
    @NotNull(message = "Food id cannot be null")
    private String foodId;
}
