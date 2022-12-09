package com.probodia.foodservice.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FoodInfoSortVo implements Comparable<FoodInfoSortVo>{
    private int missingColumn;
    private int nameLength;
    private int idx;


    @Override
    public int compareTo(FoodInfoSortVo o) {
        if(o.getMissingColumn()==getMissingColumn()){
            return getNameLength() - o.getNameLength();
        }
        return getMissingColumn() - o.getMissingColumn();
    }
}
