package com.titus.flavius.Contacts;

public class Contact implements Comparable<Contact>{
    //TODO add multiples, more info
    private String name = null;
    private PhoneNumber phoneNumber = null;
    private Email email = null;

    public Contact(){}
    public Contact(String name){ this.name = name; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }
    public String getPhoneNumberText(){
        if(phoneNumber == null) return "";
        return phoneNumber.toString();
    }
    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setPhoneNumber(String phone) {
        this.phoneNumber = new PhoneNumber(phone);
    }


    public Email getEmail() {
        return email;
    }
    public String getEmailText(){
        if(email == null) return "";
        return email.toString();
    }
    public void setEmail(Email email) {
        this.email = email;
    }
    public void setEmail(String emailAddr) {
        this.email = new Email(emailAddr);
    }

    @Override
    public int compareTo(Contact compContact) {
        return name.compareTo(compContact.name);
    }
}
