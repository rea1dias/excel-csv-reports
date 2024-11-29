package com.example.ecommerce.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ReportService {
    void generateExcelReport(HttpServletResponse response) throws IOException;
    void generateCsvReport(HttpServletResponse response) throws IOException;
}
