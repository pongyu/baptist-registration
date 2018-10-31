package org.bapp.services.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService{

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    private String prefix = "";


    public ReportServiceImpl(){

    }

    private static Connection conn(String url, String username, String password){

        Connection jdbcConnection = null;

        url ="jdbc:mysql://localhost/registrar?allowPublicKeyRetrieval=true&useSSL=false";

        try{

            Class.forName("com.mysql.jdbc.Driver");
            jdbcConnection = DriverManager.getConnection(url, username, password);

        } catch (Exception e){
            String connectMsg = "Jasper reports, could not connect to the database: " +
                    e.getMessage() + " " + e.getLocalizedMessage();
            logger.debug(connectMsg);
        }

        return jdbcConnection;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String createReport(String filename, Map<String, Object> param, String reportType) {

        File jasper = null;

        try{

            Resource resource = new ClassPathResource("static/jasper/"+filename+".jasper");

            jasper = resource.getFile();

        } catch (MalformedURLException e){
                logger.debug("{}", e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JasperReport report;

        JasperPrint jasperPrint = null;

        try {

            assert jasper != null;
            report = (JasperReport) JRLoader.loadObject(jasper);

            Connection jdbcConnection = conn(null,"root", "P@ssword01");

            jasperPrint = JasperFillManager.fillReport(report, param, jdbcConnection);


        } catch (JRException e){
            logger.debug("{}", e);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File temp = new File("src\\main\\resources\\static\\printtemp\\");

        File tempFile = null;

        File rptDir = new File("C:\\printtemp");

        if(!rptDir.exists()){
            rptDir.mkdir();
        }

        try{
            tempFile = File.createTempFile(filename, "."+reportType, temp);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert tempFile != null;
//        tempFile.deleteOnExit();

        String tempFileName = tempFile.getName();

        switch (reportType) {
            case "pdf": {
                JRPdfExporter exporter = new JRPdfExporter();

                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

                if (tempFile.delete()){
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\printtemp\\" +prefix+tempFileName));
                }

                SimplePdfReportConfiguration reportConfig
                        = new SimplePdfReportConfiguration();
                reportConfig.setSizePageToContent(true);
                reportConfig.setForceLineBreakPolicy(false);

                SimplePdfExporterConfiguration exportConfig
                        = new SimplePdfExporterConfiguration();
                exportConfig.setMetadataAuthor("pongyu");
                exportConfig.setAllowedPermissionsHint("PRINTING");

                exporter.setConfiguration(reportConfig);
                exporter.setConfiguration(exportConfig);

                try {
                    exporter.exportReport();

                } catch (JRException e) {
                    logger.debug("error exporting report pdf " + e);
                }

                break;
            }
            case "csv": {

                JRCsvExporter exporter = new JRCsvExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(
                        new SimpleWriterExporterOutput("C:\\printtemp\\" +prefix+tempFileName));

                try {
                    exporter.exportReport();
                } catch (JRException e) {
                    logger.debug("error exporting report csv " + e);
                }

                break;
            }
            case "xlsx": {
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:\\printtemp\\" +prefix+tempFileName));

                // Set input and output ...
                SimpleXlsxReportConfiguration reportConfig
                        = new SimpleXlsxReportConfiguration();
                reportConfig.setOnePagePerSheet(false);
                reportConfig.setSheetNames(new String[]{filename});

                exporter.setConfiguration(reportConfig);

                try {
                    exporter.exportReport();
                } catch (JRException e) {
                    logger.debug("error exporting report xls " + e);
                }
                break;
            }
        }

        return tempFileName;

    }

}
