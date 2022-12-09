package com.probodia.foodservice.api.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestFoodDetailDto {
    List<String> foodIds;
}
