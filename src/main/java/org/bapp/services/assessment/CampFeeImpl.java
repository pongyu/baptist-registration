package org.bapp.services.assessment;

import org.bapp.model.CodetableId;
import org.bapp.services.codetable.CodetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CampFeeImpl implements CampFee{

    private String otherFee;

    private String subsidy;

    @Value("${bapp.eventname}")
    private String eventName;


    public CampFeeImpl() {
    }


    @Autowired
    private CodetableService service;

    @Override
    public double getCampFee() {
        String event;
        if(eventName.equals("BMPSYMP")){
            event = "BMP-SYMPOSIUM";
        }else {
            event = "BMP-SSTA";
        }

        String fee = service.findOne(new CodetableId("CAMPFEE", event)).getDesc1();

        return Double.parseDouble(fee);
    }

    @Override
    public double getDiscount() {

        if(subsidy == null || subsidy.equals("")){
            return 0;
        }

        String discount = service.findOne(new CodetableId("SUBSIDY", subsidy)).getDesc1();

        if(discount == null){
            return 0;
        }

        return Double.parseDouble(discount);
    }

    @Override
    public double otherFee(){
        if(otherFee == null || otherFee.equals("")){
            return 0;
        }

        String other = service.findOne(new CodetableId("ADDITIONALFEE", otherFee)).getDesc1();

        if(other == null){
            return 0;
        }

        return Double.parseDouble(other);
    }

    @Override
    public double calculateCampFee(String subsidy, String otherFee) {

        this.subsidy = subsidy;

        this.otherFee = otherFee;

        double totalFee = 0.00;

        try{

            double fee = getCampFee();
            double discount = getDiscount();

            double dTotal = discount * fee / 100;

            totalFee = fee - dTotal;

        } catch (Exception e){
            e.printStackTrace();
        }
        return totalFee + otherFee();
    }

}
