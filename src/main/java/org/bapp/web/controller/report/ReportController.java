package org.bapp.web.controller.report;

import org.bapp.services.report.ReportServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ReportController
{
    private final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Value("${bapp.eventname}")
    private String eventName;

    @Autowired
    private ReportServiceImpl service;
//
//    @GetMapping("/report/invoice")
//    public void invoice(@RequestParam(name = "churchid") String churchid,
//                           HttpServletResponse response){
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("church_id", churchid);
//        map.put("event", eventName);
//        map.put("eventName", eventName);
//        map.put("eventDateAndPlace", "November 13-15, 2018 Camp Highlands, Iba, Zambales");
//        map.put("eventEmailOrSite", "bmp@bmp.com");
//        map.put("eventContact", "123-45678");
//
//        String file = service.createReport("Invoice", map, "pdf");
//
//        System.out.println("***** "+file);
//
//        File rpt;
//
//        ClassPathResource resource = new ClassPathResource(file);
//
//        try {
//
//            System.out.println(">>>>>>> "+resource.getFile().getName());
//
//            rpt = resource.getFile();
//
//            String mimeType = URLConnection.guessContentTypeFromName(rpt.getName());
//            if (mimeType == null) {
//                logger.debug("mimetype is not detectable, will take default");
//                mimeType = "application/pdf";
//            }
//            logger.debug("mimetype : {}", mimeType);
//            response.setContentType(mimeType);
//            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", rpt.getName()));
//            response.setContentLength( (int) rpt.length());
//            InputStream inputStream = new BufferedInputStream(new FileInputStream(rpt));
//            FileCopyUtils.copy(inputStream, response.getOutputStream());
//
//
//        } catch (IOException e) {
//            logger.debug("rpt null : {}");
//        }
//
//
//    }

    @RequestMapping(value = "/report/invoice", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> invoice(@RequestParam(name = "churchid") String churchid) throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("church_id", churchid);
        map.put("event", eventName);
        map.put("eventName", eventName);
        map.put("eventDateAndPlace", "November 13-15, 2018 Camp Highlands, Iba, Zambales");
        map.put("eventEmailOrSite", "bmp@bmp.com");
        map.put("eventContact", "123-45678");

        String fileName = service.createReport("Invoice", map, "pdf");

        Resource pdfFile = new ClassPathResource("printtemp/"+fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment; filename="+fileName);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfFile.getInputStream()));
    }

    public ResponseEntity<InputStreamResource> getPDF(String filename, String disposition, Resource resource) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", disposition+"; filename="+filename);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(resource.getInputStream()));
    }

}
