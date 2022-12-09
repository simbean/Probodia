package com.probodia.foodservice.api.entity.statics;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FoodApiEntityStatic {

    static public Map<Integer,String> FOODINFO_MAP;
    static public Map<Integer,String> PR_FOODINFO_MAP;

    public FoodApiEntityStatic() {

        FOODINFO_MAP = new HashMap<>();
        FOODINFO_MAP.put(1,"ID");
        FOODINFO_MAP.put(5,"NAME");
        FOODINFO_MAP.put(9,"BIG_CATEGORY");
        FOODINFO_MAP.put(10,"SMALL_CATEGORY");
        FOODINFO_MAP.put(11,"QUANTITY_BY_ONE");
        FOODINFO_MAP.put(12,"QUANTITY_BY_ONE_UNIT");
        FOODINFO_MAP.put(15,"CALORIES");
        FOODINFO_MAP.put(17,"PROTEIN");
        FOODINFO_MAP.put(18,"FAT");
        FOODINFO_MAP.put(19,"CARBOHYDRATE");
        FOODINFO_MAP.put(20,"SUGARS");
        FOODINFO_MAP.put(92,"TRANS_FAT");
        FOODINFO_MAP.put(68,"SATURATED_FAT");
        FOODINFO_MAP.put(67,"CHOLESTEROL");
        FOODINFO_MAP.put(33,"SALT");

        PR_FOODINFO_MAP = new HashMap<>();
        PR_FOODINFO_MAP.put(1,"ID");
        PR_FOODINFO_MAP.put(5,"NAME");
        PR_FOODINFO_MAP.put(8,"BIG_CATEGORY");
        PR_FOODINFO_MAP.put(9,"SMALL_CATEGORY");
        PR_FOODINFO_MAP.put(10,"QUANTITY_BY_ONE");
        PR_FOODINFO_MAP.put(11,"QUANTITY_BY_ONE_UNIT");
        PR_FOODINFO_MAP.put(14,"CALORIES");
        PR_FOODINFO_MAP.put(16,"PROTEIN");
        PR_FOODINFO_MAP.put(17,"FAT");
        PR_FOODINFO_MAP.put(18,"CARBOHYDRATE");
        PR_FOODINFO_MAP.put(19,"SUGARS");
        PR_FOODINFO_MAP.put(84,"TRANS_FAT");
        PR_FOODINFO_MAP.put(75,"SATURATED_FAT");
        PR_FOODINFO_MAP.put(74,"CHOLESTEROL");
        PR_FOODINFO_MAP.put(30,"SALT");

    }
}
