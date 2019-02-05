package com.esprit.diasporafinder.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.esprit.diasporafinder.R;
import com.esprit.diasporafinder.fragments.MessageFragment;
import com.esprit.diasporafinder.models.Historique;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Bourgeois on 14/05/2016.
 */
public class CardViewHistoriqueMessagesAdapter extends RecyclerView.Adapter<CardViewHistoriqueMessagesAdapter.ViewHolder> {
    private List<Historique> mDataset = new ArrayList<>();
     private Context mcontext;
     DateFormat df = new SimpleDateFormat(" HH:mm");
    private LinearLayout rawMessage;
    private Activity applicationZK;
    public int pose = 0;
    public static String PREFERENCE_FILENAME = "idMessage";


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
    public CardViewHistoriqueMessagesAdapter(Context context,List<Historique> postsBackendlessCollection,Activity application) {
        mDataset = postsBackendlessCollection;
        mcontext = context;
        applicationZK = application;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public CardViewHistoriqueMessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_messages, parent, false);
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

        final ImageView profilMessages;
        final TextView nameSenderMessage,citySender;


        profilMessages = (ImageView) holder.mView.findViewById(R.id.profilMessages);
        nameSenderMessage = (TextView) holder.mView.findViewById(R.id.nameSenderMessage);
        citySender = (TextView) holder.mView.findViewById(R.id.citySender);
        rawMessage = (LinearLayout) holder.mView.findViewById(R.id.rawMessage);

        if(mDataset.get(position).getIdReceiver().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId()) ){
            BackendlessDataQuery dataQuery = new BackendlessDataQuery();
            dataQuery.setWhereClause("objectId = '" + mDataset.get(position).getOwnerId() + "'");

            QueryOptions queryOptions = new QueryOptions();
            dataQuery.setQueryOptions(queryOptions);


            Backendless.Data.of(BackendlessUser.class).find(dataQuery, new AsyncCallback<BackendlessCollection<BackendlessUser>>() {
                @Override
                public void handleResponse(BackendlessCollection<BackendlessUser> users) {
                    Iterator<BackendlessUser> userIterator = users.getCurrentPage().iterator();

                    while (userIterator.hasNext()) {
                        BackendlessUser user = userIterator.next();

                        Picasso.with(mcontext)
                                .load("" + user.getProperty("picture"))
                                .placeholder(R.drawable.tum)
                                .into(profilMessages);
                        //   new DownloadImageTask((ImageView) holder.mView.findViewById(R.id.profilPost)).execute();
                        nameSenderMessage.setText("" + user.getProperty("name"));
                        citySender.setText("From " + user.getProperty("Nationality"));
                    }
                }

                @Override
                public void handleFault(BackendlessFault backendlessFault) {
                    Toast.makeText(holder.mView.getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                }
            });

        } else  if(mDataset.get(position).getOwnerId().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId()) ){
            BackendlessDataQuery dataQuery = new BackendlessDataQuery();
            dataQuery.setWhereClause("objectId = '" + mDataset.get(position).getIdReceiver() + "'");

            QueryOptions queryOptions = new QueryOptions();
            dataQuery.setQueryOptions(queryOptions);


            Backendless.Data.of(BackendlessUser.class).find(dataQuery, new AsyncCallback<BackendlessCollection<BackendlessUser>>() {
                @Override
                public void handleResponse(BackendlessCollection<BackendlessUser> users) {
                    Iterator<BackendlessUser> userIterator = users.getCurrentPage().iterator();

                    while (userIterator.hasNext()) {
                        BackendlessUser user = userIterator.next();

                        Picasso.with(mcontext)
                                .load("" + user.getProperty("picture"))
                                .placeholder(R.drawable.tum)
                                .into(profilMessages);
                        //   new DownloadImageTask((ImageView) holder.mView.findViewById(R.id.profilPost)).execute();
                        nameSenderMessage.setText("" + user.getProperty("name"));
                        citySender.setText("From " + user.getProperty("Nationality"));
                    }
                }

                @Override
                public void handleFault(BackendlessFault backendlessFault) {
                    Toast.makeText(holder.mView.getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                }
            });

        }








        rawMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(mDataset.get(position).getIdReceiver().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId()) ){
                    FragmentManager fm = ((FragmentActivity)mcontext).getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    MessageFragment startFragment = new MessageFragment();
                    SharedPreferences reportingPref2 = holder.mView.getContext().getSharedPreferences(PREFERENCE_FILENAME, holder.mView.getContext().MODE_PRIVATE);
                    SharedPreferences.Editor prefEditor2 = reportingPref2.edit();
                    prefEditor2.putString("idDiscussion", mDataset.get(position).getIdDiscussion());
                    prefEditor2.putString("idInterlocuteur", mDataset.get(position).getOwnerId());

                    prefEditor2.commit();
                    transaction.replace(R.id.linearLayoutBase,startFragment);
                    transaction.addToBackStack("news");
                    transaction.commit();

                }else   if(mDataset.get(position).getOwnerId().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId()) ){
                    FragmentManager fm = ((FragmentActivity)mcontext).getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    MessageFragment startFragment = new MessageFragment();
                    SharedPreferences reportingPref2 = holder.mView.getContext().getSharedPreferences(PREFERENCE_FILENAME, holder.mView.getContext().MODE_PRIVATE);
                    SharedPreferences.Editor prefEditor2 = reportingPref2.edit();
                    prefEditor2.putString("idDiscussion", mDataset.get(position).getIdDiscussion());
                    prefEditor2.putString("idInterlocuteur", mDataset.get(position).getIdReceiver());

                    prefEditor2.commit();
                    transaction.replace(R.id.linearLayoutBase,startFragment);
                    transaction.addToBackStack("news");
                    transaction.commit();

                }




            }
        });

    }





    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
