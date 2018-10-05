package org.bapp.services.report;

import org.bapp.RegistrationApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RegistrationApplication.class)
public class JasperReportTest {


    @Autowired
    ReportServiceImpl reportService;

    @Test
    public void testRpt(){

        Map<String, Object> map = new HashMap<>();
        map.put("church_id", "2180000001");
        reportService.createReport("test", map, "pdf");
    }

}
