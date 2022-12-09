package com.probodia.foodservice.api.entity;

import com.probodia.foodservice.api.entity.enums.category.big.BigCategoryCode;
import com.probodia.foodservice.api.entity.enums.category.small.SmallCategoryCode;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "FOODINFO")
public class FoodInfo {

    @Id
    private String id;

    @Column(name = "BIG_CATEGORY")
    @NotNull
    private BigCategoryCode bigCategory;

    @Column(name = "SMALL_CATEGORY")
    @NotNull
    private SmallCategoryCode smallCategory;

    @Column(name = "NAME")
    @NotNull
    private String name;

    @Column(name = "QUANTITY_BY_ONE")
    @NotNull
    private Integer quantityByOne;

    @Column(name = "QUANTITY_BY_ONE_UNIT")
    @NotNull
    private String quantityByOneUnit;

    //칼로리
    @Column(name = "CALORIES")
    @ColumnDefault(value = "-1")
    private Double calories;

    //탄수화물
    @Column(name = "CARBOHYDRATE")
    @ColumnDefault(value = "-1")
    private Double carbohydrate;

    //당
    @Column(name = "SUGARS")
    @ColumnDefault(value = "-1")
    private Double sugars;

    //단백질
    @Column(name = "PROTEIN")
    @ColumnDefault(value = "-1")
    private Double protein;

    //총 지방
    @Column(name = "FAT")
    @ColumnDefault(value = "-1")
    private Double fat;

    //트랜스지방
    @Column(name = "TRANS_FAT")
    @ColumnDefault(value = "-1")
    private Double transFat;

    //포화지방
    @Column(name = "SATURATED_FAT")
    @ColumnDefault(value = "-1")
    private Double saturatedFat;

    //콜레스테롤
    @Column(name = "CHOLESTEROL")
    @ColumnDefault(value = "-1")
    private Double cholesterol;

    //나트륨
    @Column(name = "SALT")
    @ColumnDefault(value = "-1")
    private Double salt;

}
