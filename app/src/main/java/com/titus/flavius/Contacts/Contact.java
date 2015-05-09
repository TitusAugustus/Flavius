package com.titus.flavius.Contacts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Contact implements Serializable, Comparable<Contact>{
    //TODO add multiples, more info
    private String name = "";
    private ArrayList<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
    private ArrayList<Email> emails = new ArrayList<Email>();


    private int countContactedPhone = 0;
    private Date lastContactedPhone = null;


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
    public String getPhoneNumberString(){
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
    public String getEmailString(){
        if(emails.isEmpty()) return "";
        return getEmail().toString();
    }
    public void addEmail(Email email) { emails.add(email); }
    public void addEmail(String emailAddr) { addEmail(new Email(emailAddr)); }

    public int getCountContactedPhone() {
        return countContactedPhone;
    }
    public void setCountContactedPhone(int countContactedPhone) {
        this.countContactedPhone = countContactedPhone;
    }

    public Date getLastContactedPhone() {
        return lastContactedPhone;
    }
    public String getLastContactedPhoneString() {
        if(lastContactedPhone.getTime() == 0) return "no contact";
        return getLastContactedPhone().toString();
    }
    public void setLastContactedPhone(Date lastContactedPhone) {
        this.lastContactedPhone = lastContactedPhone;
    }


    @Override
    public int compareTo(Contact compContact) {
        long contactDiff = compContact.lastContactedPhone.getTime() - lastContactedPhone.getTime();
        if(contactDiff != 0)
            return (contactDiff < 0)?-1:1;
        //uses number of times contacted instead of how recent it was
//        int contactDiff = compContact.countContactedPhone - countContactedPhone;
//        if(contactDiff != 0)
//            return contactDiff;
        return name.compareTo(compContact.name);
    }

    @Override
    public String toString() {
        return name + " " + getPhoneNumberString() + " " + getEmailString();
    }
}
