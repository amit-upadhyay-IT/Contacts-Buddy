package me.binomial.contactsbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import me.binomial.contacts.ContactInfo;
import me.binomial.contacts.ContactsFetcher;
import me.binomial.contactsbuddy.adapters.ContactsRecyclerAdapter;

public class UniqueContactsActivity extends AppCompatActivity {

    // TODO: have permission check here, I am assuming before the permission for contacts is given

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unique_contacts);

        displayUniqueContactsList();
    }

    /*
    * TODO: they need to be made unique on the basis of phone number, not on the basis of display name.
    * */
    private void displayUniqueContactsList()
    {
        ArrayList<ContactInfo> contactsList = new ContactsFetcher(this).fetchAll();

        // doing this will remove the ordering
        Set<ContactInfo> set = new HashSet<ContactInfo>(contactsList);

        ArrayList<ContactInfo> uniqueContactList = new ArrayList<>(set);

        TextView countView = findViewById(R.id.uniqueContactsCount);
        countView.setText("Count: "+ uniqueContactList.size());

        RecyclerView contactsView = findViewById(R.id.uniqueContactsRecyclerView);
        ContactsRecyclerAdapter adapter = new ContactsRecyclerAdapter(uniqueContactList, this, R.layout.phone_list_item);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        contactsView.setLayoutManager(llm);
        contactsView.setAdapter(adapter);
    }
}
