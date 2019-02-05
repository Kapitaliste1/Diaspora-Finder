package com.esprit.diasporafinder.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.DeliveryOptions;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.backendless.messaging.PublishStatusEnum;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.esprit.diasporafinder.R;
import com.esprit.diasporafinder.adapters.CardViewDetailsAdapter;
import com.esprit.diasporafinder.models.Comments;
import com.esprit.diasporafinder.models.LikesMotion;
import com.esprit.diasporafinder.models.Posts;
import com.esprit.diasporafinder.util.DefaultCallback2;
import com.esprit.diasporafinder.util.DefaultMessages;
import com.esprit.diasporafinder.util.Defaults;
import com.esprit.diasporafinder.util.DownloadImageTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class DetailsFragment extends Fragment {


    public CardViewDetailsAdapter cardViewDetailsAdapter;
    public static String idObject ;
    private TextView namePost,nationalityPostDetails,timePostDetails,likeNumberDetails,commentNumberDetails,contentPostDetails;
    private ImageView profilPostDetails,imagePostDetails;
    private EditText NewcommentDetailsContent;
    private Button NewCommentDetailsButton,editPostDetails,likePostDetails;
     private RecyclerView details_recycler_view;
     private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayout ownenDetails;
    boolean publishOnce= false;
     public static String PREFERENCE_FILENAME = "Id_User";
    boolean likeOnce = false;
    private String idOwnerPost;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(this.getActivity(), Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION );
        View rootView =  inflater.inflate(R.layout.fragment_details, container, false);

        SharedPreferences reportingPref = getContext().getSharedPreferences(PREFERENCE_FILENAME, getContext().MODE_PRIVATE);
        idObject = reportingPref.getString("idObject", "");

        details_recycler_view = (RecyclerView) rootView.findViewById(R.id.details_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        details_recycler_view.setHasFixedSize(true);
        ownenDetails = (LinearLayout) rootView.findViewById(R.id.ownenDetails);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        details_recycler_view.setLayoutManager(mLayoutManager);


        likePostDetails = (Button) rootView.findViewById(R.id.likePostDetails);
        //Button
                NewCommentDetailsButton = (Button)rootView.findViewById(R.id.NewCommentDetailsButton);
        editPostDetails = (Button) rootView.findViewById(R.id.editPostDetails);

        editPostDetails.setVisibility(View.GONE);



        //TextViews
        namePost = (TextView) rootView.findViewById(R.id.namePost2);
        nationalityPostDetails = (TextView) rootView.findViewById(R.id.nationalityPostDetails);
        timePostDetails = (TextView) rootView.findViewById(R.id.timePostDetails);
        likeNumberDetails = (TextView) rootView.findViewById(R.id.likeNumberDetails);
        commentNumberDetails = (TextView) rootView.findViewById(R.id.commentNumberDetails);
        contentPostDetails = (TextView) rootView.findViewById(R.id.contentPostDetails);

        //Images
        profilPostDetails = (ImageView) rootView.findViewById(R.id.profilPostDetails);
        imagePostDetails = (ImageView) rootView.findViewById(R.id.imagePostDetails);

        //EditText
        NewcommentDetailsContent = (EditText) rootView.findViewById(R.id.NewcommentDetailsContent);

        //Button
        NewCommentDetailsButton = (Button) rootView.findViewById(R.id.NewCommentDetailsButton);


        CommentNumberFunction();
        LikesDez(idObject);

        ownenDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                OwnerDetailsFragment startFragment = new OwnerDetailsFragment();
                SharedPreferences reportingPref = getContext().getSharedPreferences(PREFERENCE_FILENAME, getContext().MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = reportingPref.edit();
                prefEditor.putString("idOwner", idObject);
                prefEditor.commit();
                transaction.replace(R.id.linearLayoutBase, startFragment);
                transaction.addToBackStack("Owner");
                transaction.commit();

            }
        });



        likePostDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (likeOnce == false) {
                    likeOnce = true;
                    BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                    dataQuery.setWhereClause("ownerId = '" + Backendless.UserService.CurrentUser().getObjectId() + "' and IdPost = '" + idObject + "'");

                    QueryOptions queryOptions = new QueryOptions();
                    dataQuery.setQueryOptions(queryOptions);

                    Backendless.Data.of(LikesMotion.class).find(dataQuery, new AsyncCallback<BackendlessCollection<LikesMotion>>() {
                        @Override
                        public void handleResponse(BackendlessCollection<LikesMotion> nycPeople) {
                            List<LikesMotion> lisTa = nycPeople.getCurrentPage();

                            if (lisTa.size() > 0) {
                                if (lisTa.get(0).getState() == true) {
                                    lisTa.get(0).setState(false);


                                    Backendless.Persistence.save(lisTa.get(0), new AsyncCallback<LikesMotion>() {
                                        public void handleResponse(LikesMotion response) {

                                            LikesDez(idObject);
                                            likeOnce = false;


                                        }

                                        public void handleFault(BackendlessFault fault) {
                                            Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                } else if (lisTa.get(0).getState() == false) {


                                    lisTa.get(0).setState(true);


                                    Backendless.Persistence.save(lisTa.get(0), new AsyncCallback<LikesMotion>() {
                                        public void handleResponse(LikesMotion response) {
                                            LikesDez(idObject);
                                            pushMe(idOwnerPost);
                                            likeOnce = false;


                                        }

                                        public void handleFault(BackendlessFault fault) {
                                            Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                                        }
                                    });


                                }
                            }
                            if (lisTa.size() == 0) {

                                LikesMotion md = new LikesMotion();

                                md.setState(true);
                                md.setOwnerId(Backendless.UserService.CurrentUser().getObjectId());
                                md.setIdPost(idObject);
                                Backendless.Persistence.save(md, new AsyncCallback<LikesMotion>() {
                                    public void handleResponse(LikesMotion response) {
                                        LikesDez(idObject);
                                        pushMe(idOwnerPost);

                                        likeOnce = false;

                                    }

                                    public void handleFault(BackendlessFault fault) {
                                        Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
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
        });












        NewCommentDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if ( NewcommentDetailsContent.getText().length() == 0 )
                    {
                        Toast.makeText(getActivity(), "Comment field can not be empty! ", Toast.LENGTH_SHORT).show();

                        return;
                    }
                    else {
                        if(publishOnce ==false){

                            publishOnce = true;
                            Comments md = new Comments();
                            md.setContentComment(NewcommentDetailsContent.getText().toString());
                            md.setPostID(idObject);
                            md.setOwnerId(Backendless.UserService.CurrentUser().getObjectId());
                            Backendless.Persistence.save(md, new AsyncCallback<Comments>() {
                                public void handleResponse(Comments response) {


                                    // Toast.makeText(getActivity(), "We made it!", Toast.LENGTH_LONG).show();
                                    NewcommentDetailsContent.setText(null);

                                    CommentNumberFunction();

                                    publishOnce = false;

                                }

                                public void handleFault(BackendlessFault fault) {
                                    // an error has occurred, the error code can be retrieved with fault.getCode()

                                }
                            });

                        }

                    }


                } catch (BackendlessException e) {
                    System.out.println("Erreur Comment");
                }


            }
        });



        return rootView;

    }



    public void CommentNumberFunction(){
        AsyncCallback<BackendlessCollection<Posts>> callback = new AsyncCallback<BackendlessCollection<Posts>>()
        {
            @Override
            public void handleResponse( BackendlessCollection<Posts> response )
            {
                final List<Posts> DeatilsList = response.getCurrentPage();
                for (int i = 0; i< DeatilsList.size();i++) {
                    if(DeatilsList.get(i).getObjectId().equalsIgnoreCase(idObject)){

                        final int ml = i;

                        contentPostDetails.setText(DeatilsList.get(i).getContent());

                        idOwnerPost = DeatilsList.get(i).getOwnerId();



                        AsyncCallback<BackendlessCollection<Comments>> callback3 = new AsyncCallback<BackendlessCollection<Comments>>()
                        {
                            @Override
                            public void handleResponse( BackendlessCollection<Comments> response )
                            {
                                List<Comments> firstPage2 = response.getCurrentPage();
                                List<Comments> lo = new ArrayList<>();
                                for (int q =0 ; q < firstPage2.size();q++){
                                    if(firstPage2.get(q).getPostID().equalsIgnoreCase(idObject)){
                                        lo.add(firstPage2.get(q));
                                    }
                                }


                                cardViewDetailsAdapter = new CardViewDetailsAdapter(getContext(),lo,idObject);
                                details_recycler_view.setAdapter(cardViewDetailsAdapter);
                            }
                            @Override
                            public void handleFault( BackendlessFault fault )
                            {
                                Toast.makeText( getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG ).show();

                            }
                        };

                        Backendless.Persistence.of( Comments.class ).find(callback3);



                        if(DeatilsList.get(i).getOwnerId().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId())){
                            editPostDetails.setVisibility(View.VISIBLE);


                         }

                        final int finalI = i;
                        editPostDetails.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fm.beginTransaction();
                                UpdatePostFragment startFragment = new UpdatePostFragment();
                                SharedPreferences reportingPref2 =  getContext().getSharedPreferences(PREFERENCE_FILENAME,  getContext().MODE_PRIVATE);
                                SharedPreferences.Editor prefEditor2 = reportingPref2.edit();
                                prefEditor2.putString("idObject", DeatilsList.get(finalI).getObjectId());
                                prefEditor2.commit();
                                transaction.replace(R.id.linearLayoutBase,startFragment);
                                transaction.addToBackStack("news");
                                transaction.commit();

                            }
                        });




                        Backendless.Data.of(BackendlessUser.class).find(new AsyncCallback<BackendlessCollection<BackendlessUser>>() {
                            @Override
                            public void handleResponse(BackendlessCollection<BackendlessUser> users) {
                                Iterator<BackendlessUser> userIterator = users.getCurrentPage().iterator();

                                while (userIterator.hasNext()) {
                                    BackendlessUser user = userIterator.next();

                                    if (user.getObjectId().equalsIgnoreCase(DeatilsList.get(ml).getOwnerId())) {
                                        new DownloadImageTask(profilPostDetails).execute("" + user.getProperty("picture"));
                                        namePost.setText("" + user.getProperty("name"));
                                        nationalityPostDetails.setText("From " + user.getProperty("Nationality"));
                                    }
                                }
                            }

                            @Override
                            public void handleFault(BackendlessFault backendlessFault) {
                                Toast.makeText( getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG ).show();
                            }
                        });



                        long diff = new Date().getTime() - DeatilsList.get(i).getCreated().getTime();
                        long diffMinutes = diff / (60 * 1000);
                        long diffHours = diff / (60 * 60 * 1000);
                        int diffInDays = (int) ((diff) / (1000 * 60 * 60 * 24));

                        if (diffInDays > 1) {
                            System.err.println("Difference in number of days (2) : " + diffInDays);
                            timePostDetails.setText(diffInDays+" Days ago");

                        }
                        else  if (diffInDays <= 1) {

                            if ((int)diffHours < 24) {

                                if((int)diffMinutes < 60){
                                    timePostDetails.setText((int) diffMinutes+" Minutes ago");

                                }else if (((int)diffMinutes >= 60)) {
                                    timePostDetails.setText(diffHours+" Hours ago");

                                }



                            } else if ((diffHours == 24) && (diffMinutes >= 1)) {
                                timePostDetails.setText(diffInDays+" Days ago");

                            }

                        }

                        try {
                             Picasso.with(getContext())
                                    .load("" +DeatilsList.get(i).getLink())
                                    .placeholder(R.drawable.tum)
                                    .fit()
                                    .into(imagePostDetails);

                        }
                        catch (BackendlessException e){
                            System.out.println("Imaaage!!!!");
                        }





                        AsyncCallback<BackendlessCollection<Comments>> callback4 = new AsyncCallback<BackendlessCollection<Comments>>()
                        {
                            @Override
                            public void handleResponse( BackendlessCollection<Comments> response )
                            {
                                List<Comments> firstPage = response.getCurrentPage();
                                int a = 0 ;
                                for(int j = 0; j < firstPage.size(); j ++){
                                    if(firstPage.get(j).getPostID().equalsIgnoreCase(DeatilsList.get(ml).getObjectId())){
                                        a++;

                                    }
                                }
                                commentNumberDetails.setText(a+" Comments");
                            }
                            @Override
                            public void handleFault( BackendlessFault fault )
                            {
                                Toast.makeText( getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG ).show();

                            }
                        };

                        Backendless.Persistence.of( Comments.class ).find(callback4);





                    }
                }

            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText( getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG ).show();

            }
        };

        Backendless.Persistence.of( Posts.class).find(callback);


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
                        likePostDetails.setBackgroundResource(R.drawable.ic_thumb_down_black_48dp);




                    }
                    else {
                        likePostDetails.setBackgroundResource(R.drawable.ic_thumb_up_black_48dp);

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
                likeNumberDetails.setText(a+" Likes");
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


                    Backendless.Messaging.publish(Defaults.DEFAULT_CHANNEL, DefaultMessages.ACCEPT_CHAT_MESSAGE, publishOptions, deliveryOptions, new DefaultCallback2<MessageStatus>(getActivity()) {
                        @Override
                        public void handleResponse(MessageStatus response) {
                            super.handleResponse(response);
                            if (response.getStatus() == PublishStatusEnum.SCHEDULED) {

                            } else {
                                Toast.makeText( getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        onBackPressed();
    }

    public void onBackPressed()
    {
        if(getView()!=null)
        {
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                        getFragmentManager().popBackStack();
                        return true;

                    }
                    return false;
                }
            });

        }
    }


}
