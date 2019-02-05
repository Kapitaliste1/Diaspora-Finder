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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.DeliveryOptions;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.backendless.messaging.PublishStatusEnum;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.esprit.diasporafinder.R;
import com.esprit.diasporafinder.fragments.DetailsFragment;
import com.esprit.diasporafinder.fragments.OwnerDetailsFragment;
import com.esprit.diasporafinder.fragments.UpdatePostFragment;
import com.esprit.diasporafinder.models.Comments;
import com.esprit.diasporafinder.models.LikesMotion;
import com.esprit.diasporafinder.models.Posts;
import com.esprit.diasporafinder.util.DefaultCallback2;
import com.esprit.diasporafinder.util.DefaultMessages;
import com.esprit.diasporafinder.util.Defaults;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Bourgeois on 18/03/2016.
 */
public class CardViewPostAdapter extends RecyclerView.Adapter<CardViewPostAdapter.ViewHolder> {
     private List<Posts> mDataset = new ArrayList<>();
    private Button likes,comments,editPost;
    private Context mcontext;
    private TextView likeNumber,commentNumber;
    private ImageView imagePostDetails;
    DateFormat df = new SimpleDateFormat(" HH:mm");
    private LinearLayout OwnerContains;
    private Activity applicationZK;
    public int pose = 0;
    public static String PREFERENCE_FILENAME = "Id_User";
    boolean likeOnce = false;


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
    public CardViewPostAdapter(Context context,List<Posts> postsBackendlessCollection,Activity application) {
        mDataset = postsBackendlessCollection;
        mcontext = context;
        applicationZK = application;
     }

    // Create new views (invoked by the layout manager)
    @Override
    public CardViewPostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_feed, parent, false);
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
        pose = position;
        likes = (Button) holder.mView.findViewById(R.id.likePost);
        comments = (Button) holder.mView.findViewById(R.id.commentPost);
         commentNumber = (TextView) holder.mView.findViewById(R.id.commentNumber);
        likeNumber = (TextView) holder.mView.findViewById(R.id.likeNumber);
        imagePostDetails = (ImageView) holder.mView.findViewById(R.id.imagePost);

         editPost = (Button) holder.mView.findViewById(R.id.editPost);
        OwnerContains = (LinearLayout) holder.mView.findViewById(R.id.OwnerContains);

         editPost.setVisibility(View.GONE);

        if(mDataset.get(position).getOwnerId().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId())){
             editPost.setVisibility(View.VISIBLE);
        }

        likes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(likeOnce == false){
                    likeOnce = true;
                    BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                    dataQuery.setWhereClause("ownerId = '"+Backendless.UserService.CurrentUser().getObjectId()+"' and IdPost = '"+mDataset.get(position).getObjectId()+"'");

                    QueryOptions queryOptions = new QueryOptions();
                    dataQuery.setQueryOptions(queryOptions);

                    Backendless.Data.of(LikesMotion.class).find(dataQuery, new AsyncCallback<BackendlessCollection<LikesMotion>>() {
                        @Override
                        public void handleResponse(BackendlessCollection<LikesMotion> nycPeople) {
                            List<LikesMotion> lisTa = nycPeople.getCurrentPage();

                            if (lisTa.size()>0){
                                if(lisTa.get(0).getState() == true){
                                    lisTa.get(0).setState(false);


                                    Backendless.Persistence.save(lisTa.get(0), new AsyncCallback<LikesMotion>() {
                                        public void handleResponse(LikesMotion response) {

                                            LikesDez(mDataset.get(position).getObjectId());
                                            likeOnce = false;


                                        }

                                        public void handleFault(BackendlessFault fault) {
                                            Toast.makeText(holder.mView.getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }
                                else if(lisTa.get(0).getState() == false){



                                    lisTa.get(0).setState(true);


                                    Backendless.Persistence.save(lisTa.get(0), new AsyncCallback<LikesMotion>() {
                                        public void handleResponse(LikesMotion response) {
                                            LikesDez(mDataset.get(position).getObjectId());
                                            pushMe(mDataset.get(position).getOwnerId());
                                            likeOnce = false;


                                        }

                                        public void handleFault(BackendlessFault fault) {
                                            Toast.makeText(holder.mView.getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                                        }
                                    });




                                }
                            }
                            if(lisTa.size() == 0) {

                                LikesMotion md = new LikesMotion();

                                md.setState(true);
                                md.setOwnerId(Backendless.UserService.CurrentUser().getObjectId());
                                md.setIdPost(mDataset.get(position).getObjectId());
                                Backendless.Persistence.save(md, new AsyncCallback<LikesMotion>() {
                                    public void handleResponse(LikesMotion response) {
                                        LikesDez(mDataset.get(position).getObjectId());
                                        pushMe(mDataset.get(position).getOwnerId());

                                        likeOnce = false;

                                    }

                                    public void handleFault(BackendlessFault fault) {
                                        Toast.makeText(holder.mView.getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                                    }
                                });

                            }


                        }

                        @Override
                        public void handleFault(BackendlessFault backendlessFault) {
                            System.out.println("Server reported an error - " + backendlessFault.getMessage());
                        }
                    });

                }

            }
        } );


        editPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((FragmentActivity)mcontext).getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                UpdatePostFragment startFragment = new UpdatePostFragment();
                SharedPreferences reportingPref2 = holder.mView.getContext().getSharedPreferences(PREFERENCE_FILENAME, holder.mView.getContext().MODE_PRIVATE);
                SharedPreferences.Editor prefEditor2 = reportingPref2.edit();
                prefEditor2.putString("idObject", mDataset.get(position).getObjectId());
                prefEditor2.commit();
                transaction.replace(R.id.linearLayoutBase,startFragment);
                transaction.addToBackStack("news");
                transaction.commit();

            }
        });

        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((FragmentActivity)mcontext).getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                DetailsFragment startFragment = new DetailsFragment();
                SharedPreferences reportingPref2 = holder.mView.getContext().getSharedPreferences(PREFERENCE_FILENAME, holder.mView.getContext().MODE_PRIVATE);
                SharedPreferences.Editor prefEditor2 = reportingPref2.edit();
                prefEditor2.putString("idObject", mDataset.get(position).getObjectId());
                prefEditor2.commit();
                transaction.replace(R.id.linearLayoutBase,startFragment);
                transaction.addToBackStack("news");
                transaction.commit();

            }
        });
        imagePostDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((FragmentActivity)mcontext).getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                DetailsFragment startFragment = new DetailsFragment();
                SharedPreferences reportingPref1 = holder.mView.getContext().getSharedPreferences(PREFERENCE_FILENAME, holder.mView.getContext().MODE_PRIVATE);
                SharedPreferences.Editor prefEditor1 = reportingPref1.edit();
                prefEditor1.putString("idObject", mDataset.get(position).getObjectId());
                prefEditor1.commit();
                 transaction.replace(R.id.linearLayoutBase,startFragment);
                transaction.addToBackStack("news");
                transaction.commit();

            }
        });
        OwnerContains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((FragmentActivity)mcontext).getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                OwnerDetailsFragment startFragment = new OwnerDetailsFragment();
                SharedPreferences reportingPref = holder.mView.getContext().getSharedPreferences(PREFERENCE_FILENAME, holder.mView.getContext().MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = reportingPref.edit();
                prefEditor.putString("idOwner", mDataset.get(position).getOwnerId());
                 prefEditor.commit();
                   transaction.replace(R.id.linearLayoutBase,startFragment);
                transaction.addToBackStack("Owner");
                transaction.commit();

            }
        });

        final TextView nom = (TextView) holder.mView.findViewById(R.id.namePost);
        final TextView pays = (TextView) holder.mView.findViewById(R.id.nationalityPost);
        TextView tim = (TextView) holder.mView.findViewById(R.id.timePost);

        Backendless.Data.of( BackendlessUser.class ).find(new AsyncCallback<BackendlessCollection<BackendlessUser>>() {
            @Override
            public void handleResponse(BackendlessCollection<BackendlessUser> users) {
                Iterator<BackendlessUser> userIterator = users.getCurrentPage().iterator();

                while (userIterator.hasNext()) {
                    BackendlessUser user = userIterator.next();

                    if (user.getObjectId().equalsIgnoreCase(mDataset.get(position).getOwnerId())) {
                        Picasso.with(mcontext)
                                .load("" + user.getProperty("picture"))
                                .placeholder(R.drawable.tum)
                                .into((ImageView) holder.mView.findViewById(R.id.profilPost));
                     //   new DownloadImageTask((ImageView) holder.mView.findViewById(R.id.profilPost)).execute();
                        nom.setText("" + user.getProperty("name"));
                        pays.setText("From " + user.getProperty("Nationality"));
                    }
                }
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Toast.makeText(holder.mView.getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
            }
        });





        long diff = new Date().getTime() - mDataset.get(position).getCreated().getTime();
         long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        int diffInDays = (int) ((diff) / (1000 * 60 * 60 * 24));

        if (diffInDays > 1) {
            System.err.println("Difference in number of days (2) : " + diffInDays);
            tim.setText(diffInDays+" Days ago");

        }
        else  if (diffInDays <= 1) {

            if ((int)diffHours < 24) {

                if((int)diffMinutes < 60){
                    tim.setText((int) diffMinutes+" Minutes ago");

                }else if (((int)diffMinutes >= 60)) {
                    tim.setText(diffHours+" Hours ago");

                }



            } else if ((diffHours == 24) && (diffMinutes >= 1)) {
                tim.setText(diffInDays+" Days ago");

            }

        }


        Picasso.with(mcontext)
                .load("" + mDataset.get(position).getLink())
                .placeholder(R.drawable.tum)
                .fit()
                 .into((ImageView) holder.mView.findViewById(R.id.imagePost));


        LikesDez(mDataset.get(position).getObjectId());


        AsyncCallback<BackendlessCollection<Comments>> callback3 = new AsyncCallback<BackendlessCollection<Comments>>()
        {
            @Override
            public void handleResponse( BackendlessCollection<Comments> response )
            {
                List<Comments> firstPage = response.getCurrentPage();
                int a = 0 ;
                for(int j = 0; j < firstPage.size(); j ++){
                    if(firstPage.get(j).getPostID().equalsIgnoreCase(mDataset.get(position).getObjectId())){
                             a++;

                    }
                }
                commentNumber.setText(a+" Comments");
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText(holder.mView.getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

            }
        };

        Backendless.Persistence.of( Comments.class ).find(callback3);






    }


    public void LikesDez(final String idLike ){


        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause("ownerId = '"+Backendless.UserService.CurrentUser().getObjectId()+"' and IdPost = '"+idLike+"'");

        QueryOptions queryOptions = new QueryOptions();
        dataQuery.setQueryOptions(queryOptions);

        Backendless.Data.of(LikesMotion.class).find(dataQuery, new AsyncCallback<BackendlessCollection<LikesMotion>>() {

            @Override
            public void handleResponse(BackendlessCollection<LikesMotion> likesMotionBackendlessCollection) {

                List<LikesMotion> likesMotions = likesMotionBackendlessCollection.getCurrentPage();

                if (likesMotions.size() > 0  ){
                    if (likesMotions.get(0).getState() == true){
                        likes.setBackgroundResource(R.drawable.ic_thumb_down_black_48dp);




                    }
                    else {
                        likes.setBackgroundResource(R.drawable.ic_thumb_up_black_48dp);

                    }
                }
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {

            }
        });





            AsyncCallback<BackendlessCollection<LikesMotion>> callback2 = new AsyncCallback<BackendlessCollection<LikesMotion>>()
        {
            @Override
            public void handleResponse( BackendlessCollection<LikesMotion> response )
            {
                List<LikesMotion> firstPage = response.getCurrentPage();
                int a = 0 ;
                for(int j = 0; j < firstPage.size(); j ++){
                    if(firstPage.get(j).getIdPost().equalsIgnoreCase(idLike)){
                        if(firstPage.get(j).getState()==true){
                            a++;
                         }
                    }
                }
                likeNumber.setText(a+" Likes");
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {

            }
        };

        Backendless.Persistence.of( LikesMotion.class ).find(callback2);
    }


    public void pushMe(String idOwner){

        final PublishOptions publishOptions = new PublishOptions();
        publishOptions.putHeader("android-ticker-text", "You just got a private push notification!");
        publishOptions.putHeader("android-content-title", "Diaspora Finder");
        publishOptions.putHeader("android-content-text",  Backendless.UserService.CurrentUser().getProperty("name").toString()+" Just liked your Post!");

        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause("objectId = '" + idOwner + "'");

        QueryOptions queryOptions = new QueryOptions();
        dataQuery.setQueryOptions(queryOptions);


        Backendless.Data.of(BackendlessUser.class).find(dataQuery, new AsyncCallback<BackendlessCollection<BackendlessUser>>() {
            @Override
            public void handleResponse(BackendlessCollection<BackendlessUser> users) {
                Iterator<BackendlessUser> userIterator = users.getCurrentPage().iterator();

                while (userIterator.hasNext()) {
                    BackendlessUser user = userIterator.next();
                    DeliveryOptions deliveryOptions = new DeliveryOptions();
                    deliveryOptions.addPushSinglecast(user.getProperty("phoneID").toString());


                    Backendless.Messaging.publish(Defaults.DEFAULT_CHANNEL, DefaultMessages.ACCEPT_CHAT_MESSAGE, publishOptions, deliveryOptions, new DefaultCallback2<MessageStatus>(applicationZK) {
                        @Override
                        public void handleResponse(MessageStatus response) {
                            super.handleResponse(response);
                            if (response.getStatus() == PublishStatusEnum.SCHEDULED) {

                            } else {
                                Toast.makeText( mcontext, "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Toast.makeText(mcontext, "Internet connexion is needed!", Toast.LENGTH_LONG).show();
            }
        });


    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}