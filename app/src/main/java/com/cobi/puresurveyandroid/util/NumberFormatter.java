package com.cobi.puresurveyandroid.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumberFormatter {

    public static String formatCurrencyNumber(double value, String currency) {
        return currency + formatNumber(value);
    }

    public static String formatNumber(Double value) {
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        formatSymbols.setDecimalSeparator('.');
        formatSymbols.setGroupingSeparator(',');

        DecimalFormat formatter = new DecimalFormat("###,###,##0.00", formatSymbols);
        return formatter.format(value);
    }

    public static String formatCurrencyNumberNoDecimals(double value, String currency) {
        return currency + Integer.toString((int) value);
    }

    public static String formatPhoneNumber(String number){
        if(number.length()==10) {
            StringBuilder sb = new StringBuilder(number).insert(3, " ").insert(7, " ");
            return sb.toString();
        }
        return number;
    }
}
