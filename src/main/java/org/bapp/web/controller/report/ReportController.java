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

    @RequestMapping(value = "/report/invoice", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> invoice(@RequestParam(name = "churchid") String churchid) throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("church_id", churchid);
        map.put("event", eventName);
        map.put("eventName", eventName);
        map.put("eventDateAndPlace", "November 13-15, 2018 Camp Highlands, Iba, Zambales");
        map.put("eventEmailOrSite", "bmp@bmp.com");
        map.put("eventContact", "123-45678");

        service.setPrefix(churchid);

        String fileName = service.createReport("Invoice", map, "pdf");

        if(fileName == null){
            return null;
        }

        return downloadRpt(churchid+fileName);
    }

    private ResponseEntity<InputStreamResource> downloadRpt(String filename) throws IOException {

        File rptFile =  new File("C:\\printtemp\\"+filename);

        InputStream inputStream = new FileInputStream(rptFile);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment; filename="+filename);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(inputStream));
    }

}
