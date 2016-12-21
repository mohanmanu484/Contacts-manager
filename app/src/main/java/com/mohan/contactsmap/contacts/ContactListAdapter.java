package com.mohan.contactsmap.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mohan.contactsmap.R;
import com.mohan.contactsmap.model.Contacts;
import com.mohan.contactsmap.util.ImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mohan on 21/12/16.
 */

public class ContactListAdapter extends BaseAdapter {


    private ArrayList<Contacts> contactsArrayList;
    private Context context;
    private ImageLoader mImageLoader;

    public ContactListAdapter(ArrayList<Contacts> contactsArrayList) {
        this.contactsArrayList = contactsArrayList;
    }

    public void updateContactList(ArrayList<Contacts> contactsArrayList){
        this.contactsArrayList.addAll(contactsArrayList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return contactsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return contactsArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return contactsArrayList.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view==null) view=LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_view_item,parent,false);

        CircleImageView contactImage= (CircleImageView) view.findViewById(R.id.ivContactImage);
        TextView contactName= (TextView) view.findViewById(R.id.tvContactName);
        TextView noImageIcon= (TextView) view.findViewById(R.id.tvContactImage);

        Contacts contacts=contactsArrayList.get(position);
        contactName.setText(contacts.getName());
        contactImage.setImageBitmap(null);
        noImageIcon.setText(""+contacts.getName().charAt(0));
        return view;
    }

    public void clear() {
        contactsArrayList.clear();
    }
}
