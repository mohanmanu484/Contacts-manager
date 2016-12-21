package com.mohan.contactsmap.contacts;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.mohan.contactsmap.R;
import com.mohan.contactsmap.model.Contacts;
import com.mohan.contactsmap.model.ContactsManager;
import com.mohan.contactsmap.widget.EndlessScrollListener;
import com.mohan.contactsmap.widget.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.content.ContentValues.TAG;

/**
 * Created by mohan on 20/12/16.
 */

public class ContactListFragment extends BaseFragment implements EndlessScrollListener.OnLoadMoreListener,EasyPermissions.PermissionCallbacks {

    private static final int READ_CONTACT = 100;
    LoadMoreListView contactsList;
    ContactListAdapter contactsAdapter;

    private int LIMIT=20;
    private ImageLoader mImageLoader;

    public ContactListFragment() {
    }

    @AfterPermissionGranted(READ_CONTACT)
    private void fetchContactsList() {
        String[] perms = {android.Manifest.permission.READ_CONTACTS};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            loadContacts();
            Log.d(TAG, "fetchContactsList: ");
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.read_contacts),
                    READ_CONTACT, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.contact_list_layout,container,false);
        contactsList= (LoadMoreListView) view.findViewById(R.id.rvContactsList);
        contactsAdapter=new ContactListAdapter(new ArrayList<Contacts>(0));
        contactsList.setAdapter(contactsAdapter);
        contactsList.setOnLoadMoreListener(this);
        contactsAdapter.clear();
        fetchContactsList();
        return view;
    }

    private void loadContacts() {
        new LoadContacts(getContext()).execute();

    }


    @Override
    public void onLoadMore() {
        LIMIT=LIMIT+20;
        loadContacts();

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        loadContacts();
        Log.d(TAG, "onPermissionsGranted: ");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        Log.d(TAG, "onPermissionsDenied: ");
    }

    public class LoadContacts extends AsyncTask<Void,Void,ArrayList<Contacts>>{

        private Context context;

        public LoadContacts(Context context) {
            this.context = context;
        }

        @Override
        protected ArrayList<Contacts> doInBackground(Void... voids) {
            return ContactsManager.loadContacts(context,LIMIT);
        }

        @Override
        protected void onPostExecute(ArrayList<Contacts> aVoid) {
            super.onPostExecute(aVoid);
            contactsAdapter.updateContactList(aVoid);
            if(LIMIT!=20){
                contactsList.onLoadMoreComplete();
            }
        }
    }
}
