package com.probodia.foodservice.api.controller;

import com.probodia.foodservice.api.service.ApiSaveService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Slf4j
@Api(value = "호출 X", description = "호출 X")
public class ApiSaveController {

    //추후 배치로 땡겨오는 걸로 수정할 예정

    private final ApiSaveService apiService;

    @PostMapping("/saveAllProcessingFood")
    public ResponseEntity saveAllProcessingFoodInfo(@RequestBody String url){

        try {

            log.debug("URL : {}",url);

            FileInputStream file = new FileInputStream(url);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            int rowNo = 0;
            int cellIndex = 0;

            XSSFSheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();

            //rows = 3;

            for(rowNo=1; rowNo < rows; rowNo++){

                XSSFRow row = sheet.getRow(rowNo);
                if(row == null) throw new IllegalArgumentException("No row in Excel.");

                int state = apiService.saveProcessingFoodInfo(row, rowNo);


            }


        } catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity(HttpStatus.CREATED);

    }

    @PostMapping("/saveAllFood")
    public ResponseEntity saveAllFoodInfo(@RequestBody String url){

        try {

            log.debug("URL : {}",url);

            FileInputStream file = new FileInputStream(url);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            int rowNo = 0;
            int cellIndex = 0;

            XSSFSheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();

            //rows = 3;

            for(rowNo=1; rowNo < rows; rowNo++){

                XSSFRow row = sheet.getRow(rowNo);
                if(row == null) throw new IllegalArgumentException("No row in Excel.");

                int state = apiService.saveFoodInfo(row, rowNo);


            }


        } catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity(HttpStatus.CREATED);

    }


}
