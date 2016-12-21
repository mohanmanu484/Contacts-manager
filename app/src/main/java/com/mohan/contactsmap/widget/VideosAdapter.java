package com.mohan.contactsmap.widget;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mohan.contactsmap.R;
import com.mohan.contactsmap.model.Contacts;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by etiennelawlor on 5/23/15.
 */

public class VideosAdapter extends BaseAdapter<Contacts> {

    // region Member Variables
    private FooterViewHolder footerViewHolder;
    // endregion

    // region Constructors
    public VideosAdapter() {
        super();
    }
    // endregion

    @Override
    public int getItemViewType(int position) {
        return (isLastPosition(position) && isFooterAdded) ? FOOTER : ITEM;
    }

    @Override
    protected RecyclerView.ViewHolder createHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_view_item, parent, false);

        return new ContactsHolder(v);
    }

    @Override
    protected RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_footer, parent, false);

        final FooterViewHolder holder = new FooterViewHolder(v);

        return holder;
    }

    @Override
    protected void bindHeaderViewHolder(RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final ContactsHolder holder = (ContactsHolder) viewHolder;

        final Contacts contacts = getItem(position);
        if (contacts != null) {
            holder.contactName.setText(contacts.getName());
            holder.contactImage.setImageBitmap(null);
            holder.noImageIcon.setText(""+contacts.getName().charAt(0));

            int adapterPos = holder.getAdapterPosition();
            ViewCompat.setTransitionName(holder.contactName,"myTransition"+adapterPos);
        }
    }

    @Override
    protected void bindFooterViewHolder(RecyclerView.ViewHolder viewHolder) {
        FooterViewHolder holder = (FooterViewHolder) viewHolder;
        footerViewHolder = holder;
    }

    @Override
    protected void displayLoadMoreFooter() {
        if(footerViewHolder!= null){
            footerViewHolder.progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void displayErrorFooter() {
        if(footerViewHolder!= null){
            footerViewHolder.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void addFooter() {
        isFooterAdded = true;
      //  add(new Video());
    }

    // endregion

    // region Inner Classes

    public class ContactsHolder extends RecyclerView.ViewHolder{

        private TextView contactName;
        private CircleImageView contactImage;
        private TextView noImageIcon;


        public ContactsHolder(View itemView) {
            super(itemView);
            contactImage= (CircleImageView) itemView.findViewById(R.id.ivContactImage);
            contactName= (TextView) itemView.findViewById(R.id.tvContactName);
            noImageIcon= (TextView) itemView.findViewById(R.id.tvContactImage);
        }
    }
    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        // region Views
       ProgressBar progressBar;
        // endregion

        // region Constructors
        public FooterViewHolder(View view) {
            super(view);
            progressBar= (ProgressBar) view.findViewById(R.id.progressBar);
        }
        // endregion
    }

    // endregion

}