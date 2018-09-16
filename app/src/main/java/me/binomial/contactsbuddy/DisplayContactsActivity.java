package me.binomial.contactsbuddy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import me.binomial.contacts.ContactInfo;
import me.binomial.contacts.ContactsFetcher;
import me.binomial.contactsbuddy.adapters.ContactsRecyclerAdapter;

public class DisplayContactsActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contacts);

        checkContactsPermission();
    }

    private void checkContactsPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        else
            displayContactsList();
    }

    private void displayContactsList()
    {
        ArrayList<ContactInfo> contactsList = new ContactsFetcher(this).fetchAll();

        TextView countTV = findViewById(R.id.contactsListCount);
        countTV.setText("Count: " + contactsList.size());
        RecyclerView contactsView = findViewById(R.id.contactsRecyclerView);
        ContactsRecyclerAdapter adapter = new ContactsRecyclerAdapter(contactsList, this, R.layout.phone_list_item);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        contactsView.setLayoutManager(llm);
        contactsView.setAdapter(adapter);
    }

    /* callback: When permission request is either accepted or rejected */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    displayContactsList();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission or ask for permission again.
                    ActivityCompat.requestPermissions(DisplayContactsActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                }
                return;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_search)
        {
            startActivity(new Intent(this, UniqueContactsActivity.class));
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_corner_menu, menu);
        return true;
    }
}
