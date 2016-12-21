package com.mohan.contactsmap.model;

import android.support.annotation.NonNull;

import java.util.LinkedHashMap;

/**
 * Created by mohan on 20/12/16.
 */

public interface ContactDataSource {

    interface LoadContactsCallback {

        void onContactsLoaded(LinkedHashMap<Integer,Contacts> contacts);

        void onDataNotAvailable();

        void onNetworkError(String message);
    }

    void getContacts(@NonNull LoadContactsCallback callback);
}
