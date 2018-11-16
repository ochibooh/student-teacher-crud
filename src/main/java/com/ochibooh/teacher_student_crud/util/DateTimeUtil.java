package com.ochibooh.teacher_student_crud.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public String getCurrentDateTime(){
        Date date = new Date();
        String strDateFormat = "YYYY-mm-dd HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        return dateFormat.format(date);
    }
}
