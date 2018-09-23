package me.binomial.contactsbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import me.binomial.contacts.ContactInfo;
import me.binomial.contacts.ContactsFetcher;
import me.binomial.contacts.PhoneInfo;
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

        Set<ContactInfo> set = new LinkedHashSet<>(contactsList);
        ArrayList<ContactInfo> uniqueContactList = new ArrayList<>(set);

        ArrayList<ContactInfo> testUniqueList = getUniqueContacts(contactsList);

        TextView countView = findViewById(R.id.uniqueContactsCount);
        countView.setText(new StringBuilder().append("Count: ").append(testUniqueList.size()).toString());

        RecyclerView contactsView = findViewById(R.id.uniqueContactsRecyclerView);
        ContactsRecyclerAdapter adapter = new ContactsRecyclerAdapter(testUniqueList, this, R.layout.phone_list_item);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        contactsView.setLayoutManager(llm);
        contactsView.setAdapter(adapter);
    }

    private ArrayList<ContactInfo> getUniqueContacts(ArrayList<ContactInfo> contactsList)
    {
        // maintain a hashmap of numbers and a uniqueContactList
        // Add contact in the hashmap if the number isn't already present in the hashmap, else don't add

        HashMap<ArrayList<PhoneInfo>, ContactInfo> map = new HashMap<>();
        ArrayList<ContactInfo> uniqueContactList = new ArrayList<>();

        for (ContactInfo contactInfo: contactsList)
        {
            // get list of numbers
            ArrayList<PhoneInfo> numbersList = contactInfo.getPhoneNumbers();
            if (map.get(contactInfo.getPhoneNumbers()) == null)
            {
                // number array wasn't present so, adding them in map as well as in the list
                map.put(contactInfo.getPhoneNumbers(), contactInfo);
                uniqueContactList.add(contactInfo);
            }
            else
            {
                map.put(contactInfo.getPhoneNumbers(), contactInfo);
            }
        }
        return uniqueContactList;
    }

    // the number list of a contact may have multiple same numbers with diff in +91 or 0 appended
    private ArrayList<PhoneInfo> getUniqueNumbers(ArrayList<PhoneInfo> numbersList)
    {
        // construct a dictionary of string, boolean
        HashMap<String, Boolean> uniqueNums = new HashMap<>();

        for (int i = 0; i < numbersList.size(); ++i)
        {
            String num = numbersList.get(i).getNumber();
        }

        return null;
    }
}
