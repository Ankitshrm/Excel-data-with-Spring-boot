package com.exceldata.helper;

import com.exceldata.models.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HelperClass {

    //check whether the file is excel format or not
    public static Boolean checkExcelFormat (MultipartFile file){
        String contentType =file.getContentType();
        if(contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            return true;
        }
        return false;
    }

    //convert excel to list of products
    public static List<Product> convertExcelToList(InputStream is){
        List<Product> list = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                Iterator<Cell> cells =row.iterator();
                int cid =0;
                Product p = new Product();
                while(cells.hasNext()){
                    Cell cell = cells.next();
                    switch (cid){
                        case 0:
                            p.setProduct_id((int) cell.getNumericCellValue());
                            break;
                        case 1:
                            p.setProduct_name(cell.getStringCellValue());
                            break;
                        case 2:
                            p.setProduct_desc(cell.getStringCellValue());
                            break;
                        case 3:
                            p.setProduct_price(cell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                    cid++;
                }
                list.add(p);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return list;
    }


}
