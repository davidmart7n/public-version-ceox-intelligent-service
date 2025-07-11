package com.ceox.intelligence.controller;

import com.ceox.intelligence.model.WeeklyDataDTO;
import com.ceox.intelligence.model.WeeklyReportResponse;
import com.ceox.intelligence.service.GeminiReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReportController {

    private final GeminiReportService reportService;

    @Autowired
    public ReportController(GeminiReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/summary")
    public WeeklyReportResponse generateFullReport(@RequestBody WeeklyDataDTO data) {
        return reportService.generateFullReport(data);
    }
}
