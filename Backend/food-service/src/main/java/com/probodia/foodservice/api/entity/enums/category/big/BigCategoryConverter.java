package com.probodia.foodservice.api.entity.enums.category.big;

import com.probodia.foodservice.api.entity.enums.base.CodeValueConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class BigCategoryConverter extends CodeValueConverter<BigCategoryCode> {
    public BigCategoryConverter() {
        super(BigCategoryCode.class);
    }
}
