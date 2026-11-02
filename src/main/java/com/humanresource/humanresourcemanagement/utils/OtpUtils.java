package com.humanresource.humanresourcemanagement.utils;

import com.humanresource.humanresourcemanagement.entities.Otp;

import java.time.LocalDateTime;

public class OtpUtils {

    public static int generateOtp(){
        int randomNumber = (int) (Math.random() * 900000) + 100000;
        return randomNumber;
    }

    public static boolean checkIfExpired(Otp otp){
        LocalDateTime now = LocalDateTime.now();
        if(!now.isBefore(otp.getExpiredAt())){
            return true;
        }else {
            return false;
        }
    }

}