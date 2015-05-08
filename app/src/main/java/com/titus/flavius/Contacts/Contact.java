package com.titus.flavius.Contacts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Contact implements Serializable, Comparable<Contact>{
    //TODO add multiples, more info
    private String name = "";
    private ArrayList<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
    private ArrayList<Email> emails = new ArrayList<Email>();


    private int numTimesContactedPhone = 0;
    private Date lastTimeContactedPhone = null;


    public Contact(){}
    public Contact(String name){ this.name = name; }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumbers.get(0);
    }
    public String getPhoneNumberText(){
        if(phoneNumbers.isEmpty()) return "";
        return getPhoneNumber().toString();
    }
    public void addPhoneNumber(PhoneNumber phoneNumber) {
        phoneNumbers.add(phoneNumber);
    }
    public void addPhoneNumber(String phone) { addPhoneNumber(new PhoneNumber(phone)); }

    public Email getEmail() {
        return emails.get(0);
    }
    public String getEmailText(){
        if(emails.isEmpty()) return "";
        return getEmail().toString();
    }
    public void addEmail(Email email) { emails.add(email); }
    public void addEmail(String emailAddr) { addEmail(new Email(emailAddr)); }

    public int getNumTimesContactedPhone() {
        return numTimesContactedPhone;
    }
    public void setNumTimesContactedPhone(int numTimesContactedPhone) {
        this.numTimesContactedPhone = numTimesContactedPhone;
    }

    public Date getLastTimeContactedPhone() {
        return lastTimeContactedPhone;
    }
    public void setLastTimeContactedPhone(Date lastTimeContactedPhone) {
        this.lastTimeContactedPhone = lastTimeContactedPhone;
    }


    @Override
    public int compareTo(Contact compContact) {
        return name.compareTo(compContact.name);
    }

    @Override
    public String toString() {
        return name + " " + getPhoneNumberText() + " " + getEmailText();
    }
}
