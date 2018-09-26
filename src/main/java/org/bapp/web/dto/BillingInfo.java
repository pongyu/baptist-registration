package org.bapp.web.dto;

public class BillingInfo {

    private String churchId;

    private String churchName;

    private double discount;

    private double roomFee;

    private double registrantTotalFee;

    private double subtotal;

    private double total;

    public BillingInfo() {
    }

    public String getChurchId() {
        return churchId;
    }

    public void setChurchId(String churchId) {
        this.churchId = churchId;
    }

    public String getChurchName() {
        return churchName;
    }

    public void setChurchName(String churchName) {
        this.churchName = churchName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getRoomFee() {
        return roomFee;
    }

    public void setRoomFee(double roomFee) {
        this.roomFee = roomFee;
    }

    public double getRegistrantTotalFee() {
        return registrantTotalFee;
    }

    public void setRegistrantTotalFee(double registrantTotalFee) {
        this.registrantTotalFee = registrantTotalFee;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
