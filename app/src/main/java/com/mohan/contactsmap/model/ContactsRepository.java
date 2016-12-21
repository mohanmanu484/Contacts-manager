package com.mohan.contactsmap.model;

import android.support.annotation.NonNull;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mohan.contactsmap.App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

/**
 * Created by mohan on 20/12/16.
 */

public class ContactsRepository implements ContactDataSource {

    private static final String TAG = "ContactsRepository";

    public void loadContactsFromNetwork(@NonNull final LoadContactsCallback callback) {

        final LinkedHashMap<Integer,Contacts> contactsList=new LinkedHashMap<>();
        ContactsApi contactsApi=new ContactsApi(new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONObject jsonObject=response.getJSONObject(0);
                    JSONArray contactsArray=jsonObject.getJSONArray("contacts");
                    for (int i = 0; i < contactsArray.length(); i++) {
                        JSONObject contactObj=contactsArray.getJSONObject(i);
                        Contacts contacts=new Contacts(i);
                        contacts.setName(contactObj.getString("name"));
                        contacts.setEmail(contactObj.optString("email"));
                        contacts.setContactNumber1(""+contactObj.optLong("phone"));
                        contacts.setContactNumber2(""+contactObj.optLong("officePhone"));
                        contacts.setLattitude(contactObj.getDouble("latitude"));
                        contacts.setLongitude(contactObj.getDouble("longitude"));
                        contactsList.put(i,contacts);


                    }
                    if(contactsList.size()>0){
                        callback.onContactsLoaded(contactsList);
                    }else {
                        callback.onDataNotAvailable();;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onNetworkError("Unknown error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof NetworkError){
                    callback.onNetworkError("Please check Internet connection.");
                }else {
                    callback.onNetworkError("Unknown error");
                }
            }
        });
        App.getVolleyQueue().add(contactsApi);


    }

    @Override
    public void getContacts(@NonNull LoadContactsCallback callback) {

        loadContactsFromNetwork(callback);
    }
}
