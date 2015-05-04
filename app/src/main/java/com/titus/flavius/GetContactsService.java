package com.titus.flavius;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.titus.flavius.Contacts.Contact;
import com.titus.flavius.Contacts.ContactList;

public class GetContactsService extends IntentService {
    public GetContactsService() {
        super("Get Contacts");
    }

    @Override
    public void onHandleIntent(Intent i) {
        readContacts();
    }

    public void readContacts(){
        ContactList allContacts = new ContactList();

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cur.getCount() <= 0) return;
        while (cur.moveToNext()) {
            Contact tempContact = new Contact();
            String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));

            String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            tempContact.setName(name);

            //phone
            if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (pCur.moveToNext()) {
                    String phone = pCur.getString(
                            pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER));
                    if(phone == null) continue;
                    tempContact.setPhoneNumber(phone);
                }
                pCur.close();
            }

            //email
            Cursor emailCur = cr.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                    new String[]{id}, null);
            while (emailCur.moveToNext()) {
                String email = emailCur.getString(
                        emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                tempContact.setEmail(email);
            }
            emailCur.close();

            allContacts.addContact(tempContact);
        }
        allContacts.sort();
        ((TitusApplication) this.getApplication()).getContactList().setAllContacts(allContacts.getAllContacts());

        //let the main activity know that the list is updated
        Intent tmpIntent = new Intent();
        tmpIntent.setAction(MainActivity.ReloadListReceiver.RELOAD_CONTACTS_LIST_MSG);
        tmpIntent.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(tmpIntent);
    }
}
