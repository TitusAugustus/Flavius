package com.titus.flavius.Contacts;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumber implements Serializable{
    static Pattern phoneNumberPattern = Pattern.compile("^\\+(\\d*)(\\d{3})(\\d{7})$");

    private short countryCode, areaCode;
    private long mainNumber;


    public PhoneNumber(String phone){
        Matcher matcher = phoneNumberPattern.matcher(phone);
        if(matcher.matches()) {
            countryCode = Short.parseShort(matcher.group(1));
            areaCode = Short.parseShort(matcher.group(2));
            mainNumber = Long.parseLong(matcher.group(3));
        }
    }

    @Override
    public String toString() {
        String phoneText = "";
        if(countryCode != 1)
            phoneText += "+" + countryCode + " ";
        phoneText += "("+areaCode+") ";
        phoneText += mainNumber;
        return phoneText;
    }
}
