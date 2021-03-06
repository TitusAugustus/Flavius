package com.titus.flavius;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.titus.flavius.Bubbles.BubbleViewGroup;
import com.titus.flavius.Contacts.Contact;
import com.titus.flavius.Contacts.ContactList;
import com.titus.flavius.ReadingWriting.Reader;
import com.titus.flavius.ReadingWriting.Writer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private final String ALL_CONTACTS_NAME = "allcontacts";

    ReloadListReceiver mReceiver = new ReloadListReceiver();
    private ViewFlipper viewFlipper;
    private final float screenPctgNeededForFlip = .90f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        final BubbleViewGroup bubbleVG = (BubbleViewGroup) findViewById(R.id.BubbleVG);
        bubbleVG.addBubble(this);

        //if there's old info, reuse it
        File storedList = new File(getFilesDir(), ALL_CONTACTS_NAME);
        if(storedList.exists()){
            ContactList list = Reader.readContactList(storedList);
            ((TitusApplication) getApplication()).getContactList().setAllContacts(list.getAllContacts());
            reloadContactsList();
        }

        //start get contact info from phone
        Intent intent = new Intent(getApplicationContext(), GetContactsService.class);
        getApplicationContext().startService(intent);

        //set up reciever for reload list broadcasts
        IntentFilter filter = new IntentFilter();
        filter.addAction(ReloadListReceiver.RELOAD_CONTACTS_LIST_MSG);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mReceiver, filter);
    }

    public void reloadContactsList(){
        ListView contactListView = (ListView) findViewById(R.id.listView);

        ArrayList<String> list = ((TitusApplication) getApplication()).getContactList().getContactNames();
        list.add(((TitusApplication) getApplication()).getContactList().getAllContacts().size() + "");
        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        contactListView.setAdapter(adapter);

        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                Contact currContact = ((TitusApplication) getApplication()).getContactList().getContactFromName(item);
                ((TextView) findViewById(R.id.nameTextView)).setText(currContact.getName());
                ((TextView) findViewById(R.id.phoneTextView)).setText(currContact.getPhoneNumberText());
                ((TextView) findViewById(R.id.emailTextView)).setText(currContact.getEmailText());
                flipNext();
            }
        });
    }

    private void flipNext(){
        if (viewFlipper.getDisplayedChild() == 2) return;

        viewFlipper.setInAnimation(this, R.anim.in_right);
        viewFlipper.setOutAnimation(this, R.anim.out_left);
        viewFlipper.showNext();
    }
    private void flipPrevious(){
        if (viewFlipper.getDisplayedChild() == 0) return;

        viewFlipper.setInAnimation(this, R.anim.in_left);
        viewFlipper.setOutAnimation(this, R.anim.out_right);
        viewFlipper.showPrevious();
    }

    @Override
    public boolean onTouchEvent(MotionEvent touchEvent) {
        if(touchEvent.getAction() == MotionEvent.ACTION_DOWN){
            float currentX = touchEvent.getX();
            if (currentX < getWindow().getDecorView().getWidth() / 2)
                flipPrevious();
            else
                flipNext();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }


    public class ReloadListReceiver extends BroadcastReceiver {
        public static final String RELOAD_CONTACTS_LIST_MSG = "titusproject.reloadcontactslist";
        @Override
        public void onReceive(Context context, Intent intent){
            if(intent.getAction().equals(RELOAD_CONTACTS_LIST_MSG)){
                reloadContactsList();
                File file = new File(context.getFilesDir(), ALL_CONTACTS_NAME);
                ContactList list = ((TitusApplication) getApplication()).getContactList();
                Writer.writeContactList(file, list);
            }
        }
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {
        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
