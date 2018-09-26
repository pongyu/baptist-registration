package org.bapp.services.assessment;

public interface CampFee {

    double getCampFee();

    double getDiscount(String subsidy);

    double otherFee(String otherFee);

    double calculateCampFee(String subsidy, String otherFee);

}