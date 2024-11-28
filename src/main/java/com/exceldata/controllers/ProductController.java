package com.exceldata.controllers;

import com.exceldata.helper.HelperClass;
import com.exceldata.models.Product;
import com.exceldata.service.ProductService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.Port;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file")MultipartFile file){
        if (HelperClass.checkExcelFormat(file)){
            this.productService.save(file);
            return ResponseEntity.ok(Map.of("message","file is uploaded and data is saved in db"));
        }
        return ResponseEntity.badRequest().body("Please upload excel file");
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> all= this.productService.getAllProducts();
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/download-excel")
    public ResponseEntity<byte[]> downloadExcel() throws IOException {
        // Create a workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a sheet
        Sheet sheet = workbook.createSheet("Data");

        // Create header row
        Row headerRow = sheet.createRow(0);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("ID");
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Name");
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Age");

        // Create data rows
        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(1);
        dataRow.createCell(1).setCellValue("John Doe");
        dataRow.createCell(2).setCellValue(30);

        dataRow = sheet.createRow(2);
        dataRow.createCell(0).setCellValue(2);
        dataRow.createCell(1).setCellValue("Jane Smith");
        dataRow.createCell(2).setCellValue(25);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        byte[] excelBytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);
    }
}
