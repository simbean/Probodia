package com.probodia.foodservice.api.entity.enums.base;


import com.probodia.foodservice.api.exception.NoSuchEnumValue;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

public class CodeValueConverter<E extends Enum<E> & BaseEnumCode> implements AttributeConverter<E, Integer> {

    private Class<E> clz;

    protected CodeValueConverter(Class<E> enumClass){
        this.clz = enumClass;
    }

    @Override
    public Integer convertToDatabaseColumn(E attribute) {
        return (int) attribute.getCode();
    }

    @Override
    public E convertToEntityAttribute(Integer dbData) {
        return EnumSet.allOf(clz).stream()
                .filter(e->e.getCode().equals(dbData))
                .findAny()
                .orElseThrow(()-> new NoSuchEnumValue(String.format("Invalid value : %d", dbData)));
    }
}