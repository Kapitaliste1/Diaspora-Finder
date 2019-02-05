package com.esprit.diasporafinder.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.esprit.diasporafinder.R;
import com.esprit.diasporafinder.fragments.OwnerDetailsFragment;
import com.esprit.diasporafinder.util.LocationAddress;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bourgeois on 24/04/2016.
 */
public class CardViewFinder  extends RecyclerView.Adapter<CardViewFinder.ViewHolder> {
    private List<BackendlessUser> mDataset = new ArrayList<>();
    private Context mcontext;
    private float distance;
    private Activity applicationZK;
    LocationAddress locationAddress = new LocationAddress();
    private String country ;
    public static String PREFERENCE_FILENAME = "Id_User";



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardViewFinder(Context context,List<BackendlessUser> userBackendlessCollection,Activity application) {
        mDataset = userBackendlessCollection;
        mcontext = context;
        applicationZK = application;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardViewFinder.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_finder, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }





    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // holder.mView.setText(mDataset[position]);
        //locationAddress.getAddressFromLocation((Double) mDataset.get(position).getProperty("Latitude"), (Double) mDataset.get(position).getProperty("Longitude"),holder.mView.getContext(), new GeocoderHandler());


        final LinearLayout card_user;
        final ImageView profilFinder,groupState;
        final TextView nameFind,countryUser,precentageFinde;

        //Linear
        card_user = (LinearLayout) holder.mView.findViewById(R.id.card_user);

        //TexteView
        nameFind = (TextView) holder.mView.findViewById(R.id.nameFind);
        countryUser = (TextView) holder.mView.findViewById(R.id.countryUser);
        precentageFinde = (TextView) holder.mView.findViewById(R.id.precentageFinde);

        //ImageView
        profilFinder = (ImageView) holder.mView.findViewById(R.id.profilFinder);
        groupState = (ImageView) holder.mView.findViewById(R.id.groupState);


        Location locationA = new Location("point A");
        locationA.setLatitude((Double) Backendless.UserService.CurrentUser().getProperty("Latitude"));
        locationA.setLongitude((Double) Backendless.UserService.CurrentUser().getProperty("Longitude"));
        Location locationB = new Location("point B");
        locationB.setLatitude((Double) mDataset.get(position).getProperty("Latitude"));
        locationB.setLongitude((Double) mDataset.get(position).getProperty("Longitude"));
        distance =locationA.distanceTo(locationB)/1000 ;
        if(Math.round(distance)==0.0)
        {
            float qp = distance * 1000;
            precentageFinde.setText(Math.round(qp) + " Meters from your current position !");

        }

        if(Math.round(distance)>=0.0){
            precentageFinde.setText(Math.round(distance) + " Kilometre from your current position !");

        }

        nameFind.setText(mDataset.get(position).getProperty("name").toString());
        countryUser.setText("From " + mDataset.get(position).getProperty("Nationality").toString());
        if (mDataset.get(position).getProperty("type").toString().equalsIgnoreCase("Groupe")){
            groupState.setVisibility(View.VISIBLE);
        }
        Picasso.with(mcontext)
                .load("" + mDataset.get(position).getProperty("picture"))
                .placeholder(R.drawable.tum)
                .into(profilFinder);

        card_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((FragmentActivity)mcontext).getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                OwnerDetailsFragment startFragment = new OwnerDetailsFragment();
                SharedPreferences reportingPref = holder.mView.getContext().getSharedPreferences(PREFERENCE_FILENAME, holder.mView.getContext().MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = reportingPref.edit();
                prefEditor.putString("idOwner", mDataset.get(position).getObjectId());
                prefEditor.commit();
                transaction.replace(R.id.linearLayoutBase,startFragment);
                transaction.addToBackStack("Owner");
                transaction.commit();

            }
        });



    }




    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            country = locationAddress;

        }
    }

}
