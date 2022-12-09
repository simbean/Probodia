package com.probodia.foodservice.api.service;

import com.probodia.foodservice.api.entity.FoodInfo;
import com.probodia.foodservice.api.entity.enums.category.big.BigCategoryCode;
import com.probodia.foodservice.api.entity.enums.category.small.SmallCategoryCode;
import com.probodia.foodservice.api.repository.FoodInfoRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Service;

import static com.probodia.foodservice.api.entity.statics.FoodApiEntityStatic.FOODINFO_MAP;
import static com.probodia.foodservice.api.entity.statics.FoodApiEntityStatic.PR_FOODINFO_MAP;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiSaveService {

    private final FoodInfoRepositoryImpl foodInfoRepository;


    String getValue(XSSFCell cell){


        String value = "";
        if(cell == null){ // 빈 셀 체크
            return null;
        }else{
            // 타입 별로 내용을 읽는다
            switch (cell.getCellType()){

                case XSSFCell.CELL_TYPE_NUMERIC:
                    //log.debug("type : {}, value: {}",cell.getCellType(),cell.getNumericCellValue());
                    value = "\'" + cell.getNumericCellValue() + "\'";
                    break;
                case XSSFCell.CELL_TYPE_STRING:
                        value = "\'" + cell.getStringCellValue() + "\'";
                    break;
                case XSSFCell.CELL_TYPE_BLANK:
                    value = cell.getBooleanCellValue() + "";
                    break;
                case XSSFCell.CELL_TYPE_ERROR:
                    value = cell.getErrorCellValue() + "";
                    break;
            }
        }
        return value;
    }

    private String makeQuery(String query, String valueQuery){


        query = query.substring(0, query.length() - 1);
        valueQuery = valueQuery.substring(0, valueQuery.length() - 1);

        query += ") ";
        valueQuery += ")";
        query += valueQuery;

        return query;
    }

    public int saveFoodInfo(XSSFRow row,int rowNo){
        int cells = row.getPhysicalNumberOfCells(); // 해당 Row에 사용자가 입력한 셀의 수를 가져온다

        String query = "insert into foodinfo (";
        String valueQuery = " VALUES (";

        for (int cellIndex = 0; cellIndex < cells; cellIndex++) {
            XSSFCell cell = row.getCell(cellIndex); // 셀의 값을 가져온다
            String value = getValue(cell);
            if(value.equals("\'-\'")) continue;
            //log.debug("VALUE : {}",value);
            if(!FOODINFO_MAP.containsKey(cellIndex)) continue;

            String column = FOODINFO_MAP.get(cellIndex);

            query += column + ',';

            if(column.equals("BIG_CATEGORY")){
                value = String.valueOf(BigCategoryCode.findByCode(value.substring(1, value.length() - 1)));
                String tmp =  "\'";
                tmp += value + "\'";
                value = tmp;
            }
            else if(column.equals("SMALL_CATEGORY")){
                value = String.valueOf(SmallCategoryCode.findByCode(value.substring(1, value.length() - 1)));
                String tmp =  "\'";
                tmp += value + "\'";
                value = tmp;
            }

            valueQuery += value + ',';

            //log.debug("{}번 행 : {}번 열 값은 {}" , cellIndex , rowNo, value);
        }

        query = makeQuery(query,valueQuery);

        return foodInfoRepository.saveEntity(query);


    }

    public int saveProcessingFoodInfo(XSSFRow row,int rowNo) {

            int cells = row.getPhysicalNumberOfCells(); // 해당 Row에 사용자가 입력한 셀의 수를 가져온다

            String query = "insert into foodinfo (";
            String valueQuery = " VALUES (";

            for (int cellIndex = 0; cellIndex < cells; cellIndex++) {
                XSSFCell cell = row.getCell(cellIndex); // 셀의 값을 가져온다
                String value = getValue(cell);
                if(value.equals("\'-\'")) continue;
                log.debug("VALUE : {}",value);
                if(!PR_FOODINFO_MAP.containsKey(cellIndex)) continue;

                String column = PR_FOODINFO_MAP.get(cellIndex);

                query += column + ',';

                if(column.equals("BIG_CATEGORY")){
                    value = String.valueOf(BigCategoryCode.findByCode(value.substring(1, value.length() - 1)));
                    String tmp =  "\'";
                    tmp += value + "\'";
                    value = tmp;
                }
                else if(column.equals("SMALL_CATEGORY")){
                    value = String.valueOf(SmallCategoryCode.findByCode(value.substring(1, value.length() - 1)));
                    String tmp =  "\'";
                    tmp += value + "\'";
                    value = tmp;
                }

                valueQuery += value + ',';

                //log.debug("{}번 행 : {}번 열 값은 {}" , cellIndex , rowNo, value);
            }

            query = makeQuery(query,valueQuery);

            return foodInfoRepository.saveEntity(query);
    }

}
