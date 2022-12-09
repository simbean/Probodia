package com.probodia.challengeservice.api.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {
    String code;
    Object message;
}
