package org.bapp.services.assessment;

public interface CampFee {

    double getCampFee();

    double getDiscount();

    double otherFee();

    double calculateCampFee(String subsidy, String otherFee);

}