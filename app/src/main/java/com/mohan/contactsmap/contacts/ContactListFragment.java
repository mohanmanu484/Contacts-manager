package com.mohan.contactsmap.contacts;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.BuildConfig;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mohan.contactsmap.R;
import com.mohan.contactsmap.model.Contacts;
import com.mohan.contactsmap.model.ContactsManager;
import com.mohan.contactsmap.util.ImageLoader;
import com.mohan.contactsmap.util.Utils;
import com.mohan.contactsmap.widget.EndlessScrollListener;
import com.mohan.contactsmap.widget.LoadMoreListView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by mohan on 20/12/16.
 */

public class ContactListFragment extends BaseFragment implements EndlessScrollListener.OnLoadMoreListener {

    LoadMoreListView contactsList;
    ContactListAdapter contactsAdapter;

    private int LIMIT=20;
    private ImageLoader mImageLoader;

    public ContactListFragment() {
    }

    private int getListPreferredItemHeight() {
        final TypedValue typedValue = new TypedValue();

        // Resolve list item preferred height theme attribute into typedValue
        getActivity().getTheme().resolveAttribute(
                android.R.attr.listPreferredItemHeight, typedValue, true);

        // Create a new DisplayMetrics object
        final DisplayMetrics metrics = new DisplayMetrics();

        // Populate the DisplayMetrics
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Return theme value based on DisplayMetrics
        return (int) typedValue.getDimension(metrics);
    }

    private Bitmap loadContactPhotoThumbnail(String photoData, int imageSize) {

        // Ensures the Fragment is still added to an activity. As this method is called in a
        // background thread, there's the possibility the Fragment is no longer attached and
        // added to an activity. If so, no need to spend resources loading the contact photo.
        if (!isAdded() || getActivity() == null) {
            return null;
        }

        // Instantiates an AssetFileDescriptor. Given a content Uri pointing to an image file, the
        // ContentResolver can return an AssetFileDescriptor for the file.
        AssetFileDescriptor afd = null;

        // This "try" block catches an Exception if the file descriptor returned from the Contacts
        // Provider doesn't point to an existing file.
        try {
            Uri thumbUri;
            // If Android 3.0 or later, converts the Uri passed as a string to a Uri object.
            if (Utils.hasHoneycomb()) {
                thumbUri = Uri.parse(photoData);
            } else {
                // For versions prior to Android 3.0, appends the string argument to the content
                // Uri for the Contacts table.
                final Uri contactUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, photoData);

                // Appends the content Uri for the Contacts.Photo table to the previously
                // constructed contact Uri to yield a content URI for the thumbnail image
                thumbUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
            }
            // Retrieves a file descriptor from the Contacts Provider. To learn more about this
            // feature, read the reference documentation for
            // ContentResolver#openAssetFileDescriptor.
            afd = getActivity().getContentResolver().openAssetFileDescriptor(thumbUri, "r");

            // Gets a FileDescriptor from the AssetFileDescriptor. A BitmapFactory object can
            // decode the contents of a file pointed to by a FileDescriptor into a Bitmap.
            FileDescriptor fileDescriptor = afd.getFileDescriptor();

            if (fileDescriptor != null) {
                // Decodes a Bitmap from the image pointed to by the FileDescriptor, and scales it
                // to the specified width and height
                return ImageLoader.decodeSampledBitmapFromDescriptor(
                        fileDescriptor, imageSize, imageSize);
            }
        } catch (FileNotFoundException e) {
            // If the file pointed to by the thumbnail URI doesn't exist, or the file can't be
            // opened in "read" mode, ContentResolver.openAssetFileDescriptor throws a
            // FileNotFoundException.
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Contact photo thumbnail not found for contact " + photoData
                        + ": " + e.toString());
            }
        } finally {
            // If an AssetFileDescriptor was returned, try to close it
            if (afd != null) {
                try {
                    afd.close();
                } catch (IOException e) {
                    // Closing a file descriptor might cause an IOException if the file is
                    // already closed. Nothing extra is needed to handle this.
                }
            }
        }

        // If the decoding failed, returns null
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImageLoader = new ImageLoader(getActivity(), getListPreferredItemHeight()) {
            @Override
            protected Bitmap processBitmap(Object data) {
                // This gets called in a background thread and passed the data from
                // ImageLoader.loadImage().
                return loadContactPhotoThumbnail((String) data, getImageSize());
            }
        };

        // Set a placeholder loading image for the image loader
        mImageLoader.setLoadingImage(R.drawable.ic_perm_contact_calendar_black_36dp);

        // Add a cache to the image loader
        mImageLoader.addImageCache(getActivity().getSupportFragmentManager(), 0.1f);
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
        loadContacts();
        return view;
    }

    private void loadContacts() {
        new LoadContacts(getContext()).execute();

    }


    @Override
    public void onLoadMore() {
        LIMIT=LIMIT+20;
        new LoadContacts(getContext()).execute();

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
            if(LIMIT==20){

            }else {
                contactsList.onLoadMoreComplete();
            }
           //
        }
    }
}
