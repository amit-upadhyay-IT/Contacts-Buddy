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

        ArrayList<ContactInfo> testUniqueList = getUniqueContactsBasedOnNumbers(contactsList);

        TextView countView = findViewById(R.id.uniqueContactsCount);
        countView.setText(new StringBuilder().append("Count: ").append(testUniqueList.size()).toString());

        RecyclerView contactsView = findViewById(R.id.uniqueContactsRecyclerView);
        ContactsRecyclerAdapter adapter = new ContactsRecyclerAdapter(testUniqueList, this, R.layout.phone_list_item);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        contactsView.setLayoutManager(llm);
        contactsView.setAdapter(adapter);
    }


    /*
    Algo:
        - Maintain a HashMap<ArrayList<String>, ContactInfo>
        - Maintain a ArrayList<ContactInfo> for unique contacts list
        - Add the ContactInfo object in unique contact list + HashMap if it's numbers are not already present in the HashMap
        NOTE: While inserting the contactInfo object in list, make sure you are comparing the core value of the Numbers i.e. removing preceding '0' and '+91'
     */

    private ArrayList<ContactInfo> getUniqueContactsBasedOnNumbers(ArrayList<ContactInfo> contactsList)
    {
        HashMap<ArrayList<String>, ContactInfo> map = new HashMap<>();
        ArrayList<ContactInfo> uniqueContactList = new ArrayList<>();

        for (ContactInfo contact: contactsList)
        {
            // get list of numbers
            ArrayList<PhoneInfo> numbers = contact.getPhoneNumbers();
            // process this list of numbers and get numbers without preceding '0' or '+91'
            ArrayList<String> uniqueBaseNumberList = getBaseNumbersFromList(numbers);

            if (map.get(uniqueBaseNumberList) == null)
            {
                // number array wasn't present so, adding them in map as well as in the list
                map.put(uniqueBaseNumberList, contact);
                uniqueContactList.add(contact);
            }
        }
        return uniqueContactList;
    }

    /*
    Algo:
        - Construct a new ArrayList<String> for storing uniqueBaseNumbers
        - Construct a HashMap<String, Boolean> for storing the numbers in it (it will store the unique numbers)
        - Read the numbers list and get the base number:
            - if base number isn't already present in the above HashMap, then insert it in map and the uniqueBaseNumbers list
            - Else, just insert in the
     */
    private ArrayList<String> getBaseNumbersFromList(ArrayList<PhoneInfo> numbers)
    {
        ArrayList<String> uniqueBaseNumbers = new ArrayList<>();
        HashMap<String, Boolean> map  = new HashMap<>();

        for (int i = 0; i < numbers.size(); ++i)
        {
            String baseNumber = getBaseNumber(numbers.get(i).getNumber());
            if (map.get(baseNumber) == null)
            {
                map.put(baseNumber, true);
                uniqueBaseNumbers.add(baseNumber);
            }
        }
        return uniqueBaseNumbers;
    }

    /*
    Algo:
        - Check digits in number
        - If digit is greater than 10, then only go for extracting the base number
            - Check the substring (0, 3) if it's equal to '+91':
                - return the base number
            - Check the substring (0, 1) if it's equal to '0':
                - return the base number
     */
    private String getBaseNumber(String number)
    {
        if (number.length() == 10)
            return number;
        else
        {
            if (number.substring(0, 3).equals("+91") && number.length() == 13)
                return number.substring(3, number.length()-3);
            if (number.substring(0, 1).equals("0") && number.length() == 11)
                return number.substring(1, number.length()-1);
            else
                return null;
        }
    }
}
