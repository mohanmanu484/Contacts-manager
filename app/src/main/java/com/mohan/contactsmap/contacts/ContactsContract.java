package com.mohan.contactsmap.contacts;

import android.graphics.Movie;

import java.util.List;

/**
 * Created by mohan on 20/12/16.
 */

public interface ContactsContract {


    interface ContactsListView extends BaseView<ContactsListPresenter>{
        void showProgress();
        void hideProgress();
        void setTitle(String title);
        void showContactList(List<Movie> movies);
        boolean isActive();
    }

    interface ContactsMapView extends BaseView<ContactsListPresenter>{
        void showProgress();
        void hideProgress();
        void setTitle(String title);
        boolean isActive();
        void showNetworkError(String message);
    }
    interface ContactsListPresenter extends BasePresenter{
        void loadMore();
        void showDetails();
    }


}
