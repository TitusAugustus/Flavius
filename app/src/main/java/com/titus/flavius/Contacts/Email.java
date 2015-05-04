package com.titus.flavius.Contacts;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email implements Serializable {
    static Pattern emailPattern = Pattern.compile("^(.*)@(.*)$");

    private String name, domain;


    public Email(String emailAddr){
        Matcher matcher = emailPattern.matcher(emailAddr);
        if(matcher.matches()) {
            name = matcher.group(1);
            domain = matcher.group(2);
        }
    }

    @Override
    public String toString() {
        return name + "@" + domain;
    }
}
