package com.probodia.foodservice.api.dto;


import com.probodia.foodservice.utils.PageInfoUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

//@ApiModel(value = "페이징 기록 조회 모델")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "음식 검색 결과 응답")
public class PagingFoodNameDto {

    @ApiModelProperty(value = "데이터",example = "", required = true)
    private List<FoodNameDto> data;

    @ApiModelProperty(value = "시간 태그",example = "아침", required = true)
    private PageInfoUtil pageInfo;

}
