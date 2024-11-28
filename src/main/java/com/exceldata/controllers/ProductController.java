package com.exceldata.controllers;

import com.exceldata.helper.HelperClass;
import com.exceldata.models.Product;
import com.exceldata.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.Port;
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
}
