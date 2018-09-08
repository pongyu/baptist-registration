package org.bapp.controller.common;

import org.bapp.model.Codetable;
import org.bapp.services.codetable.CodetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ChurchRestController {

    private class Church{
        private String value;
        private String data;

        public Church(String value, String data) {
            this.value = value;
            this.data = data;
        }

        public Church() {
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
    @Autowired
    CodetableService service;

    @GetMapping("church/list")
    public List<Church> listChurch(){
        List<Codetable> codetables = service.findAllById("church");
        List<Church> churches = new ArrayList<>();
        for (Codetable c : codetables){
            churches.add(new Church(c.getDesc1(),c.getId().getCodeValue()));
        }
        return churches;
    }
}
