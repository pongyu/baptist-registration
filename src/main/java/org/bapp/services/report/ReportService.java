package org.bapp.services.report;

import java.util.Map;

public interface ReportService {

    String createReport(String filename, Map<String, Object> param, String reportType);

}
