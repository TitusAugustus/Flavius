package com.titus.flavius.Contacts;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class ContactList {
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
}
