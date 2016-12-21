package com.mohan.contactsmap.contacts;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mohan.contactsmap.R;
import com.mohan.contactsmap.model.ContactDataSource;
import com.mohan.contactsmap.model.Contacts;
import com.mohan.contactsmap.model.ContactsRepository;

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

    LinkedHashMap<Integer, Contacts> contactsArrayList = new LinkedHashMap<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contacts_map_layout, container, false);
        mMapView = (MapView) view.findViewById(R.id.mapView);
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

        ContactsRepository contactsRepository = new ContactsRepository();
        contactsRepository.getContacts(new ContactDataSource.LoadContactsCallback() {
            @Override
            public void onContactsLoaded(LinkedHashMap<Integer, Contacts> contactsArrayList) {
                ContactsMapFragment.this.contactsArrayList = contactsArrayList;
                for (int i = 0; i < contactsArrayList.size(); i++) {
                    Contacts contacts = contactsArrayList.get(i);
                    LatLng location = new LatLng(contacts.getLattitude(), contacts.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(location).title(contacts.getName()).snippet("" + i));
                }
                mMap.setOnMarkerClickListener(ContactsMapFragment.this);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(contactsArrayList.get(0).getLattitude(), contactsArrayList.get(0).getLongitude())));
            }

            @Override
            public void onDataNotAvailable() {

            }

            @Override
            public void onNetworkError(String message) {

                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
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

        mMap = googleMap;
        // Add a marker in Sydney and move the camera
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int id = Integer.valueOf(marker.getSnippet());
        Contacts contacts = contactsArrayList.get(id);
        showContactDetailfragment(contacts);
        return true;
    }

    public void showContactDetailfragment(Contacts contacts) {
        ContactDetailfragment newFragment = new ContactDetailfragment();
        newFragment.setContacts(contacts);
        newFragment.show(getFragmentManager(), null);
    }


    public static class ContactDetailfragment extends DialogFragment {

        private Contacts contacts;

        public void setContacts(Contacts contacts) {
            this.contacts = contacts;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View view = LayoutInflater.from(getContext()).inflate(R.layout.contact_detail_layout, null);
            TextView name = (TextView) view.findViewById(R.id.tvName);
            TextView email = (TextView) view.findViewById(R.id.tvEmail);
            TextView phone1 = (TextView) view.findViewById(R.id.tvPhone);
            TextView phone2 = (TextView) view.findViewById(R.id.tvPhone2);
            builder.setView(view);
            if (contacts != null) {
                name.setText(contacts.getName());
                email.setText(contacts.getEmail());
                phone1.setVisibility(contacts.getContactNumber1()==null?View.GONE:View.VISIBLE);
                phone2.setVisibility(contacts.getContactNumber2()==null?View.GONE:View.VISIBLE);
                email.setVisibility(contacts.getEmail()==null?View.GONE:View.VISIBLE);
                phone1.setText("" + contacts.getContactNumber1());
                phone2.setText("" + contacts.getContactNumber2());
            }
            return builder.create();
        }


    }


}
