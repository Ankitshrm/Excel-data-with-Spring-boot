package com.exceldata.service;

import com.exceldata.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    void save(MultipartFile file);
    List<Product> getAllProducts();
}
