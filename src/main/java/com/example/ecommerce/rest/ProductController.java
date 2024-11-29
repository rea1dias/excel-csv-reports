package com.example.ecommerce.rest;

import com.example.ecommerce.model.ProductDto;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;
    private final ReportService reportService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable Long id, @RequestBody ProductDto dto) {
        ProductDto createdProduct = service.get(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto) {
        ProductDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @RequestBody ProductDto dto) {
        ProductDto updated = service.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/excel")
    public void generateExcelReport(HttpServletResponse response) throws Exception {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=products.xlsx";

        response.setHeader(headerKey, headerValue);

        reportService.generateExcelReport(response);
    }

    @GetMapping("/csv")
    public void generateCsvReport(HttpServletResponse response) throws Exception {
        reportService.generateCsvReport(response);
    }
}
