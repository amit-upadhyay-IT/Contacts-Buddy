package me.binomial.contacts;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

/*
 A Bean class for Contact details
 */
public class ContactInfo implements Parcelable {

    /* properties to be fetched in a contact */
    private String id;
    private String personName;
    private ArrayList<PhoneInfo> phoneNumbers;
    private ArrayList<EmailInfo> emailAddresses;

    public ContactInfo() {
    }

    public ContactInfo(String id, String personName) {
        this.id = id;
        this.personName = personName;
        this.phoneNumbers = new ArrayList<PhoneInfo>();
        this.emailAddresses = new ArrayList<EmailInfo>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public ArrayList<PhoneInfo> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(ArrayList<PhoneInfo> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public ArrayList<EmailInfo> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(ArrayList<EmailInfo> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    /* For deserializing, used by Creator*/
    protected ContactInfo(Parcel in) {
        id = in.readString();
        personName = in.readString();
    }

    public static final Creator<ContactInfo> CREATOR = new Creator<ContactInfo>() {
        @Override
        public ContactInfo createFromParcel(Parcel in) {
            return new ContactInfo(in);
        }

        @Override
        public ContactInfo[] newArray(int size) {
            return new ContactInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(personName);
    }

    @Override
    public String toString() {
        String result = personName;
        if (phoneNumbers.size() > 0) {
            PhoneInfo phoneNumber = phoneNumbers.get(0);
            result += " (" + phoneNumber.getNumber() + " - " + phoneNumber.getType() + ")";
        }
        if (emailAddresses.size() > 0) {
            EmailInfo emailInfo = emailAddresses.get(0);
            result += " [" + emailInfo.getAddress() + " - " + emailInfo.getType() + "]";
        }
        return result;
    }

    public void addEmail(String address, String type) {
        emailAddresses.add(new EmailInfo(address, type));
    }

    public void addNumber(String number, String type) {
        phoneNumbers.add(new PhoneInfo(number, type));
    }
}
