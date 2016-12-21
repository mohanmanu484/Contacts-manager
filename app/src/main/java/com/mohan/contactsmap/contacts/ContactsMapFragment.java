package com.mohan.contactsmap.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mohan.contactsmap.App;
import com.mohan.contactsmap.R;
import com.mohan.contactsmap.model.Contacts;
import com.mohan.contactsmap.model.ContactsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

/**
 * Created by mohan on 21/12/16.
 */

public class ContactsMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    MapView mMapView;

    LinkedHashMap<Integer,Contacts> contactsArrayList=new LinkedHashMap<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.contacts_map_layout,container,false);
        mMapView= (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);
        fetchContacts();


        return view;
    }

    private void fetchContacts() {
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
                        /*contactObj.getString("email");
                        contactObj.getLong("phone");
                        contactObj.getLong("officePhone");*/
                        contacts.setLattitude(contactObj.getDouble("latitude"));
                        contacts.setLongitude(contactObj.getDouble("longitude"));
                        contactsArrayList.put(i,contacts);


                    }
                    for (int i = 0; i < contactsArrayList.size(); i++) {
                        Contacts contacts=contactsArrayList.get(i);
                        LatLng location = new LatLng(contacts.getLattitude(), contacts.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(location).title(contacts.getName()).snippet(""+i));
                    }
                    mMap.setOnMarkerClickListener(ContactsMapFragment.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        App.getVolleyQueue().add(contactsApi);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap=googleMap;
        // Add a marker in Sydney and move the camera

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int id=Integer.valueOf(marker.getSnippet());
        Contacts contacts=contactsArrayList.get(id);
        Toast.makeText(getContext(), contacts.getName(), Toast.LENGTH_SHORT).show();
        return true;
    }


}
