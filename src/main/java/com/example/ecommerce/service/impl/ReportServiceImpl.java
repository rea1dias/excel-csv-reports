package com.example.ecommerce.service.impl;

import org.apache.poi.ss.usermodel.*;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ReportService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ProductRepository repository;

    @Override
    public void generateExcelReport(HttpServletResponse response) throws IOException {

        try (Workbook workbook = new XSSFWorkbook();

             ServletOutputStream outputStream = response.getOutputStream()) {
            Sheet sheet = workbook.createSheet("Products");

            XSSFFont descriptionFont = (XSSFFont) workbook.createFont();
            descriptionFont.setFontHeightInPoints((short) 16);

            XSSFFont boldFont = (XSSFFont) workbook.createFont();
            boldFont.setBold(true);

            XSSFFont categoryFont = (XSSFFont) workbook.createFont();
            categoryFont.setItalic(true);

            XSSFCellStyle descriptionStyle = (XSSFCellStyle) workbook.createCellStyle();
            descriptionStyle.setFont(descriptionFont);

            XSSFCellStyle boldStyle = (XSSFCellStyle) workbook.createCellStyle();
            boldStyle.setFont(boldFont);

            XSSFCellStyle yellowStyle = (XSSFCellStyle) workbook.createCellStyle();
            yellowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            yellowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFCellStyle categoryStyle = (XSSFCellStyle) workbook.createCellStyle();
            categoryStyle.setFont(categoryFont);

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Description");
            headerRow.createCell(3).setCellValue("Price");
            headerRow.createCell(4).setCellValue("Quantity");
            headerRow.createCell(5).setCellValue("Category");

            headerRow.getCell(2).setCellStyle(descriptionStyle);
            headerRow.getCell(3).setCellStyle(boldStyle);
            headerRow.getCell(4).setCellStyle(yellowStyle);
            headerRow.getCell(5).setCellStyle(categoryStyle);

            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));


            List<Product> products = repository.findAll();
            int rowNum = 1;
            for (Product product : products) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getDescription());
                row.createCell(3).setCellValue(product.getPrice().doubleValue());
                row.createCell(4).setCellValue(product.getQuantity().doubleValue());
                row.createCell(5).setCellValue(product.getCategory().name());
            }

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(outputStream);

        } catch (Exception e) {
            throw new IOException("Failed to generate Excel report", e);
        }
    }

    @Override
    public void generateCsvReport(HttpServletResponse response) throws IOException {
        List<Product> products = repository.findAll();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"products.csv\"");

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()))) {
            writer.write("ID,Name,Description,Price,Quantity,Category");
            writer.newLine();

            for (Product product : products) {
                writer.write(String.format("%d,%s,%s,%.2f,%d,%s",
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice().doubleValue(),
                        product.getQuantity(),
                        product.getCategory().name()));
                writer.newLine();
            }
        }



    }
}
