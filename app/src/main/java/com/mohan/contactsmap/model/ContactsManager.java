package com.mohan.contactsmap.model;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.mohan.contactsmap.util.Utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * Created by mohan on 30/8/16.
 */
public class ContactsManager{


    private Context context;
    private static final String TAG = "ContactsManager";

    public ContactsManager(Context context) {
        this.context = context;
    }

    public static ArrayList<Contacts> loadContacts(Context context,int limit){
        LinkedHashMap<Integer,Contacts> data=new LinkedHashMap<>();
        TreeMap<String,Contacts> data2=new TreeMap<>();
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, Utils.hasHoneycomb() ? ContactsContract.Contacts.SORT_KEY_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME+" LIMIT 10");

      phones=  context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
              ContactsContract.Contacts.DISPLAY_NAME+" limit 20"+" OFFSET "+limit);
        ArrayList<Contacts> contactsArrayList=new ArrayList<>();
        if(phones.moveToFirst()) {
            for (int i = 0; i < phones.getCount(); i++) {

                Log.d(TAG, "loadContacts: "+phones.getColumnNames());;
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO));
                String contactImage = phones.getString(1);
                int id=phones.getInt(60);

                Log.d(TAG, "click: name=" + name + " phone=" + phoneNumber);

                Contacts contacts= data.get(id);
                if(contacts!=null){
                    contacts.setContactNumber(phoneNumber);
                }else {
                    contacts=new Contacts(id);
                    contacts.setName(name);
                    contacts.setImageURI(contactImage);
                    data.put(id,contacts);
                }
                phones.moveToNext();
            }
            contactsArrayList.addAll(data.values());
        }

        phones.close();
        return contactsArrayList;
    }
}
