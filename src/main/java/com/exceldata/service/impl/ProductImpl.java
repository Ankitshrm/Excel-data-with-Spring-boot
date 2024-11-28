package com.exceldata.service.impl;

import com.exceldata.helper.HelperClass;
import com.exceldata.models.Product;
import com.exceldata.repositories.ProductRepo;
import com.exceldata.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ProductImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public void save(MultipartFile file) {
        try {
            List<Product> allProduct =HelperClass.convertExcelToList(file.getInputStream());
            this.productRepo.saveAll(allProduct);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepo.findAll();
    }
}
