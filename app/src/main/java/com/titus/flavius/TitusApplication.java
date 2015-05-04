package com.titus.flavius;

import android.app.Application;
import com.titus.flavius.Contacts.ContactList;

public class TitusApplication extends Application {
    private ContactList allContacts = new ContactList();

    public ContactList getAllContacts() {
        return allContacts;
    }
}
