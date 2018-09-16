package me.binomial.contactsbuddy.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import me.binomial.contacts.ContactInfo;
import me.binomial.contacts.EmailInfo;
import me.binomial.contacts.PhoneInfo;
import me.binomial.contactsbuddy.R;

public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsViewHolder> {


    private ArrayList<ContactInfo> contactsList;
    private Context context;
    private int resource;

    public ContactsRecyclerAdapter(ArrayList<ContactInfo> contactsList, Context context, int resource) {
        this.contactsList = contactsList;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ContactsViewHolder(LayoutInflater.from(context).inflate(resource, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int i) {

        // get the data
        ContactInfo contactInfo = contactsList.get(i);
        holder.personName.setText(contactInfo.getPersonName());
        // if there are multiple numbers
        StringBuilder numbers = new StringBuilder();
        ArrayList<PhoneInfo> numberList = contactInfo.getPhoneNumbers();
        for (PhoneInfo phoneInfo: numberList)
             numbers.append(":").append(phoneInfo.getNumber());
        holder.personNumbers.setText(numbers.toString());
        // there might be more than one email of the person
        StringBuilder emails = new StringBuilder();
        ArrayList<EmailInfo> emailList = contactInfo.getEmailAddresses();
        for (EmailInfo emailInfo: emailList)
            emails.append(":").append(emailInfo.getAddress());
        holder.personEmails.setText(emails.toString());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }
}

class ContactsViewHolder extends RecyclerView.ViewHolder
{
    TextView personName, personNumbers, personEmails;

    public ContactsViewHolder(@NonNull View itemView) {
        super(itemView);
        personName = itemView.findViewById(R.id.personName);
        personNumbers = itemView.findViewById(R.id.personNumber);
        personEmails = itemView.findViewById(R.id.personEmail);
    }
}
