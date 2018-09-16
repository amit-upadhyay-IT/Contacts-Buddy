package me.binomial.contactsbuddy.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/* NOTE: Don't use this class, need improvements */

public class CheckPermissions {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    private static Context context;


    public static void setContext(Context context) {
        context = context;
    }

    public static boolean isContactsPermission()
    {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            return false;
        else
            return true;
    }

    public static void askForContactPermission()
    {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        else
            throw new IllegalArgumentException("Permission is already given");  // this state shouldn't be reached.
    }

}
