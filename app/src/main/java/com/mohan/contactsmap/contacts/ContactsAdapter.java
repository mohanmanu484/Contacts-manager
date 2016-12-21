package com.mohan.contactsmap.contacts;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mohan.contactsmap.R;
import com.mohan.contactsmap.model.Contacts;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mohan on 20/12/16.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsHolder> {


    private ArrayList<Contacts> contactsArrayList;

    private static final String TAG = "ContactsAdapter";

    public ContactsAdapter(ArrayList<Contacts> contactsArrayList) {
        this.contactsArrayList = contactsArrayList;
    }

    public void updateContactList(ArrayList<Contacts> contactsArrayList) {
        this.contactsArrayList = contactsArrayList;
        notifyDataSetChanged();
    }

    @Override
    public ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_view_item,parent,false);
        return new ContactsHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsHolder holder, int position) {

        Contacts contacts=contactsArrayList.get(position);
        holder.contactName.setText(contacts.getName());
        holder.contactImage.setImageBitmap(null);
        holder.noImageIcon.setText(""+contacts.getName().charAt(0));
        Log.d(TAG, "onBindViewHolder: "+contacts.getImageURI());

    }

    @Override
    public int getItemCount() {
        return contactsArrayList.size();
    }

    public class ContactsHolder extends RecyclerView.ViewHolder{

        private TextView contactName;
        private CircleImageView contactImage;
        private TextView noImageIcon;


        public ContactsHolder(View itemView) {
            super(itemView);
            contactImage= (CircleImageView) itemView.findViewById(R.id.ivContactImage);
            contactName= (TextView) itemView.findViewById(R.id.tvContactName);
            noImageIcon= (TextView) itemView.findViewById(R.id.tvContactImage);
        }
    }
}
