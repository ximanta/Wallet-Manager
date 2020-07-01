package com.kodilla.walletmanager.tools;

import org.springframework.stereotype.Component;

@Component
public class ToolsManager {

    public static double positiveTenthRoundDouble(double d){
        if (d < 0){
            System.out.println("Amount cannot be negative");
            return 0;
        }
        else if((d * 100) - (long)(d * 100) != 0) {
            double tmp = Math.round(d) * 100;
            return tmp / 100 ;
        }
        else {
            return d;
        }
    }
}
