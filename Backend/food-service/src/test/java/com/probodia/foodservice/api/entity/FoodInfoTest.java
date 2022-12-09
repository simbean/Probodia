package com.probodia.foodservice.api.entity;

import com.probodia.foodservice.api.repository.FoodInfoRepository;
import com.probodia.foodservice.api.repository.TestExcelRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class FoodInfoTest {


    @Autowired
    private FoodInfoRepository foodInfoRepository;

    @Autowired
    private TestExcelRepository testExcelRepository;
    private EntityManager em;

    @Autowired
    private EntityManagerFactory emf;


    static Map<Integer,String> testMap;

    @BeforeAll
    static void beforeAll(){
        testMap = new HashMap<>();
        testMap.put(0, "ID");
        testMap.put(1, "STUDENT_NAME");
    }


    @Test
    @DisplayName("기본적인 엔티티 테스트")
    void testFood(){
        Optional<FoodInfo> byId =
                foodInfoRepository.findById("TEST_001");

        FoodInfo find = byId.get();
        Double calories = find.getCalories();
        if(calories== null)
            log.debug("find id : {}",calories);



    }

    String getValue(XSSFCell cell){


        String value = "";
        if(cell == null){ // 빈 셀 체크
            return null;
        }else{
            // 타입 별로 내용을 읽는다
            switch (cell.getCellType()){

                case XSSFCell.CELL_TYPE_NUMERIC:
                    log.debug("type : {}, value: {}",cell.getCellType(),cell.getNumericCellValue());
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

    @Test
    @DisplayName("엑셀 파일 엔티티 추가")
    void addAllFood(){

        EntityTransaction tx = null;
        em = emf.createEntityManager();

        try {
            tx = em.getTransaction();

            // 경로에 있는 파일을 읽
            FileInputStream file = new FileInputStream("/Users/parkseojin/Desktop/소마/테스트엑셀파일.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            int rowNo = 0;
            int cellIndex = 0;

            XSSFSheet sheet = workbook.getSheetAt(0); // 0 번째 시트를 가져온다
            // 만약 시트가 여러개 인 경우 for 문을 이용하여 각각의 시트를 가져온다
            int rows = sheet.getPhysicalNumberOfRows(); // 사용자가 입력한 엑셀 Row수를 가져온다

            tx.begin();

            for(rowNo = 1; rowNo < rows; rowNo++){
                XSSFRow row = sheet.getRow(rowNo);


                if(row != null){
                    int cells = row.getPhysicalNumberOfCells(); // 해당 Row에 사용자가 입력한 셀의 수를 가져온다

                    String query = "insert into test_excel (";
                    String valueQuery = " VALUES (";

                    for(cellIndex = 0; cellIndex < cells; cellIndex++){
                        XSSFCell cell = row.getCell(cellIndex); // 셀의 값을 가져온다
                        String value = getValue(cell);

                        query+= testMap.get(cellIndex) + ',';
                        valueQuery+= value + ',';

                        System.out.println( rowNo + "번 행 : " + cellIndex + "번 열 값은: " + value);
                    }

                    query = query.substring(0,query.length()-1);
                    valueQuery= valueQuery.substring(0,valueQuery.length()-1);

                    query+=") ";
                    valueQuery+=")";
                    query+= valueQuery;

                    log.debug("query : {}",query);

                    em.createNativeQuery(query).executeUpdate();
                    em.flush();

                }
            }

            tx.commit();
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void test(){
        String s = "%";
        s += "asdf";
        s += "%";
        log.debug(s);

    }


}