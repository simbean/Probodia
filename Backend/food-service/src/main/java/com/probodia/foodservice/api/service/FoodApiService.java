package com.probodia.foodservice.api.service;


import com.probodia.foodservice.api.dto.FoodInfoDto;
import com.probodia.foodservice.api.entity.FoodInfo;
import com.probodia.foodservice.api.mapper.foodinfo.FoodInfoMapper;
import com.probodia.foodservice.api.repository.FoodInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FoodApiService {

    private final FoodInfoRepository foodInfoRepository;

    public Optional<FoodInfo> findById(String foodId){

        Optional<FoodInfo> byId = foodInfoRepository.findById(foodId);

        return byId;
    }

    public List<FoodInfoDto> findByIds(List<String> foodIds){

        List<FoodInfoDto> ret = new ArrayList<>();

        List<String> uniqueList = foodIds.stream().distinct().collect(Collectors.toList());
        uniqueList.stream().forEach(f -> {
            Optional<FoodInfo> byId = foodInfoRepository.findById(f);

            if(byId.isPresent()) {
                ret.add(FoodInfoMapper.INSTANCE.toDto(byId.get()));
                log.debug("ID : {}",byId.get().getFat());
            }
            else throw new IllegalArgumentException("No matching food");
        });
        return ret;
    }


    public Page<FoodInfo> getAllByFoodNamePaging(Integer page, Integer size, String name){

        PageRequest pageRequest = PageRequest.of(page, size);


        //requestName = "%오뚜기%";

       return foodInfoRepository.findAllByNameLike(pageRequest,name);
    }
}
