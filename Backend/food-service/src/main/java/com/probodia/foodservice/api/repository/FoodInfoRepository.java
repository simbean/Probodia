package com.probodia.foodservice.api.repository;

import com.probodia.foodservice.api.entity.FoodInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodInfoRepository extends JpaRepository<FoodInfo,String> {


    Page<FoodInfo> findAllByNameLike(Pageable pageable, String name);

}
