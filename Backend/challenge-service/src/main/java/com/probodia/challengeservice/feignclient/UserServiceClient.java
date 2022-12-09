package com.probodia.challengeservice.feignclient;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping(value = "/api/users/point", produces = "application/json")
    public String setPoint(@RequestBody Integer point,@RequestParam("userId") String userId);

}