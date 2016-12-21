package com.mohan.contactsmap.contacts;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import com.mohan.contactsmap.R;

/**
 * Created by mohan on 5/10/16.
 */

public class BaseFragment extends Fragment {

    ProgressDialog progressDialog;

   public void showProgress(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(getContext());
        }
        progressDialog.setTitle(getContext().getString(R.string.please_wait));
        progressDialog.show();

    }

   public void hideProgress(){

        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
