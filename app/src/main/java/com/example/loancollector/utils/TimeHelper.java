package com.example.loancollector.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeHelper {

    public static String calculateSettledDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date date = new Date();
        return formatter.format(date);
    }

    public static String formatCurrency(String priceString) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        Double priceDouble = Double.parseDouble(priceString);
        return formatter.format(priceDouble);
    }
}
