package me.binomial.contactsbuddy;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import me.binomial.contacts.ContactInfo;
import me.binomial.contacts.ContactsFetcher;

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
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        else
        {
            displayContactsList();
        }
    }

    private void displayContactsList()
    {
        ArrayList<ContactInfo> contactsList = new ContactsFetcher(this).fetchAll();

        // TODO: display in recyclerview
        Toast.makeText(this, "Count of contacts: " + contactsList.size(), Toast.LENGTH_SHORT).show();
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
}
