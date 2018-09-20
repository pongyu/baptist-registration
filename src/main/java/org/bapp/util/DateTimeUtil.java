package org.bapp.util;

import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

    public static int getAge(Date date){

        Calendar dob = Calendar.getInstance();
        if(date!=null){
            dob.setTime(date);
        }else{
            dob.setTime(new Date());
        }
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
            age--;
        } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }

}
