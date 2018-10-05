package org.bapp.web.controller.cashier;

import org.bapp.mapper.RegistrantMapper;
import org.bapp.model.Church;
import org.bapp.model.Registrant;
import org.bapp.services.assessment.CampFeeImpl;
import org.bapp.services.billing.BillingServiceImpl;
import org.bapp.services.church.ChurchServiceImpl;
import org.bapp.web.dto.BillingInfo;
import org.bapp.web.dto.RegistrantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("billing")
public class BillingController {

    @Value("${bapp.eventid}")
    private Integer eventId;

    @Value("${bapp.eventname}")
    private String eventName;

    @ModelAttribute("event")
    public String getEventName(){

        String event;


        if(eventName.equals("BMPSYMP")){
            event = "BMP-Symposium ";
        } else {
            event = "BMP-Sunday School Camp ";
        }

        return event;
    }

    @ModelAttribute("eventId")
    public Integer eventType(){
        return this.eventId;
    }

    @Autowired
    private BillingServiceImpl service;

    @Autowired
    private ChurchServiceImpl churchService;

    @Autowired
    private CampFeeImpl campFee;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("bill", new BillingInfo());
        model.addAttribute("registrants", new ArrayList<Registrant>());
        return "cashier/index";
    }

    @PostMapping("info")
    @ResponseBody
    public BillingInfo church(@RequestBody String churchId){

        Church c = churchService.findByAppStatusAndChurchId("1", churchId);

        if(c == null){
            return new BillingInfo();
        }

        if(!c.getEventName().equals(eventName)){
            return null;
        }

        BillingInfo bill = new BillingInfo();
        if(c != null){

            bill.setChurchId(c.getChurchId());
            bill.setChurchName(c.getChurchName());

            double discount = 0.00;
            double roomFee = 0.00;
            double registrantTotalFee = 0.00;
            double subtotal;
            double total;

            for(Registrant r : c.getRegistrants()){

                if(r.getSubsidy() != null){
                    double campfee = campFee.calculateCampFee(r.getSubsidy(), null);
                    double d = campFee.getCampFee() - campfee;
                    discount += d;
                }
                if(r.getRoomType() != null){
                    roomFee += campFee.otherFee(r.getRoomType());
                }
                //sum all fee without discount
                registrantTotalFee += campFee.getCampFee();
            }
            subtotal = registrantTotalFee + roomFee;
            total = subtotal - discount;

            bill.setDiscount(discount);
            bill.setRoomFee(roomFee);
            bill.setRegistrantTotalFee(registrantTotalFee);
            bill.setSubtotal(subtotal);
            bill.setTotal(total);

        }

        return bill;

    }

    @GetMapping("delegates")
    @ResponseBody
    public List<RegistrantDTO> delegates(@RequestParam(name = "churchId") String churchId){

        Church c = churchService.findByAppStatusAndChurchId("1", churchId);

        if(c != null && c.getEventName().equals(eventName)){

            List<Registrant> ar = c.getRegistrants();
            for(Registrant r : ar){
                if(r.getSubsidy() != null){
                    double campfee = campFee.calculateCampFee(r.getSubsidy(), null);
                    double discount = campFee.getCampFee() - campfee;
                    r.setSubsidy(Double.toString(discount));
                }
                if(r.getRoomType() != null){
                    r.setRoomType(Double.toString(campFee.otherFee(r.getRoomType())));
                }
            }

            return RegistrantMapper.INSTANCE.registrantToRegistrantDtoList(ar);
        }

        return new ArrayList<>();

    }

    @PostMapping("payout")
    @ResponseBody
    public String toRegistered(@RequestBody String churchId){

        service.submitToRegistered(churchId);

        String status = churchService.findByChurchId(churchId).getAppStatus();

        if(status.equals("2")){
            return "success";
        }

        return "failed";

    }

}
