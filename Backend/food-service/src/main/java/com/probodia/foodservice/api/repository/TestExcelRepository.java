package com.probodia.foodservice.api.repository;

import com.probodia.foodservice.api.entity.TestExcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestExcelRepository extends JpaRepository<TestExcel, String> {

}
