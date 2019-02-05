package com.esprit.diasporafinder.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.esprit.diasporafinder.models.Discussion;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Bourgeois on 21/03/2016.
 */
public class CardViewMessageAdapter extends RecyclerView.Adapter<CardViewMessageAdapter.ViewHolder> {
    private List<Discussion> mDataset = new ArrayList<>();
    private Context mcontext;
    DateFormat df = new SimpleDateFormat(" HH:mm");
     private Activity applicationZK;
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
    public CardViewMessageAdapter(Context context,List<Discussion> postsBackendlessCollection,Activity application) {
        mDataset = postsBackendlessCollection;
        mcontext = context;
        applicationZK = application;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public CardViewMessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_chat, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }





    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // holder.mView.setText(mDataset[position]);

        final   ImageView senderPic;
        final TextView senderContent,senderName;

        senderName = (TextView) holder.mView.findViewById(R.id.senderName);

        senderPic = (ImageView) holder.mView.findViewById(R.id.senderPic);

        senderContent = (TextView) holder.mView.findViewById(R.id.senderContent);

        senderContent.setText(mDataset.get(position).getContent());

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
                            .into(senderPic);
                    //   new DownloadImageTask((ImageView) holder.mView.findViewById(R.id.profilPost)).execute();
                    senderName.setText("" + user.getProperty("name"));
                 }
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Toast.makeText(holder.mView.getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
            }
        });

    }





    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
