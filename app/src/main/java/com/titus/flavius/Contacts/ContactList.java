package com.titus.flavius.Contacts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ContactList implements Serializable{
    private volatile ArrayList<Contact> contacts = new ArrayList<Contact>();


    public ArrayList<Contact> getAllContacts() {
        return contacts;
    }
    public void setAllContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public Contact getContactFromName(String name){
        int index = Collections.binarySearch(contacts, new Contact(name));
        if(index < 0)
            return null;
        return contacts.get(index);
    }
    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void sort() {
        Collections.sort(contacts);
    }

    public ArrayList<String> getContactNames(){
        ArrayList<String> result = new ArrayList<String>();
        for (Contact c : contacts)
            result.add(c.getName());
        return result;
    }

    @Override
    public String toString() {
        String res = "";
        for(Contact contact : contacts)
            res += "\n" + contact.toString();
        return res.substring(1);
    }
}
