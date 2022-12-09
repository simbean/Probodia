package com.probodia.foodservice.api.mapper.foodinfo;

import com.probodia.foodservice.api.entity.FoodInfo;
import com.probodia.foodservice.api.entity.enums.category.big.BigCategoryCode;
import com.probodia.foodservice.api.entity.enums.category.small.SmallCategoryCode;
import com.probodia.foodservice.api.mapper.EntityMapper;
import com.probodia.foodservice.api.dto.FoodInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FoodInfoMapper extends EntityMapper<FoodInfoDto, FoodInfo> {

    FoodInfoMapper INSTANCE = Mappers.getMapper(FoodInfoMapper.class);

    default String toBigCategoryTypeValue(BigCategoryCode type){
        return type.getValue();
    }

    default String toSmallCategoryTypeValue(SmallCategoryCode type){
        return type.getValue();
    }
}
