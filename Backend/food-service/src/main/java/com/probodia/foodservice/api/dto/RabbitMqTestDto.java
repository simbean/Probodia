package com.probodia.foodservice.api.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RabbitMqTestDto {
    private String name;
    private Integer age;

    public RabbitMqTestDto(byte[] utf8, int age) {
        name = utf8.toString();
    }
}
