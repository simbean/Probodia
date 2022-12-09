package com.probodia.foodservice.api.entity.enums.category.small;

import com.probodia.foodservice.api.entity.enums.base.CodeValueConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class SmallCategoryConverter extends CodeValueConverter<SmallCategoryCode> {
    public SmallCategoryConverter() {
        super(SmallCategoryCode.class);
    }
}
