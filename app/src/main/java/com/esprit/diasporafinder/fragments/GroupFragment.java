package com.esprit.diasporafinder.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.esprit.diasporafinder.adapters.CardViewPostAdapter;
import com.esprit.diasporafinder.models.Followers;
import com.esprit.diasporafinder.models.Posts;
import com.esprit.diasporafinder.util.Defaults;

import java.util.ArrayList;
import java.util.List;

public class GroupFragment  extends Fragment {


    private CardViewPostAdapter cardViewPostAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView nonFollow;
    final List<Posts> lp = new ArrayList<Posts>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp(this.getActivity(), Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION );

        View rootView =  inflater.inflate(R.layout.fragment_news_feed, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        nonFollow = (TextView) rootView.findViewById(R.id.nonFollowGroups);
        mRecyclerView.setVisibility(View.GONE);
        // specify an adapter (see also next example)

        PostsNumberFunction();


        return rootView;

    }





    public void PostsNumberFunction(){





        BackendlessDataQuery dataQuery3 = new BackendlessDataQuery();
        dataQuery3.setWhereClause("ownerId='" + Backendless.UserService.CurrentUser().getObjectId() + "'");

        QueryOptions queryOptions2 = new QueryOptions();
        dataQuery3.setQueryOptions(queryOptions2);

        Backendless.Data.of(Followers.class).find(dataQuery3, new AsyncCallback<BackendlessCollection<Followers>>() {

            @Override
            public void handleResponse(BackendlessCollection<Followers> followersBackendlessCollection) {
                List<Followers> followersList = followersBackendlessCollection.getCurrentPage();
                if (followersList.size() == 1) {
                    nonFollow.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                            nonFollow.setVisibility(View.GONE);
                        }
                    }, 20000);

                }


            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {

            }
        });







        AsyncCallback<BackendlessCollection<Posts>> callback = new AsyncCallback<BackendlessCollection<Posts>>() {
            @Override
            public void handleResponse(BackendlessCollection<Posts> response) {
                final List<Posts> DeatilsList = response.getCurrentPage();
                for (int q = 0; q < DeatilsList.size(); q++) {


                    BackendlessDataQuery dataQuery2 = new BackendlessDataQuery();
                    dataQuery2.setWhereClause("type='Groupe' and objectId='"+DeatilsList.get(q).getOwnerId()+"'");

                    QueryOptions queryOptions2 = new QueryOptions();
                    dataQuery2.setQueryOptions(queryOptions2);

                    final int finalQ = q;
                    Backendless.Data.of(BackendlessUser.class).find(dataQuery2, new AsyncCallback<BackendlessCollection<BackendlessUser>>() {

                        @Override
                        public void handleResponse(BackendlessCollection<BackendlessUser> userBackendlessCollection) {

                            List<BackendlessUser> listUsers = userBackendlessCollection.getCurrentPage();

                            if(listUsers.size()>0){
                                BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                                dataQuery.setWhereClause("idFollowing = '" + listUsers.get(0).getObjectId() + "' and ownerId='" + Backendless.UserService.CurrentUser().getObjectId() + "'");

                                QueryOptions queryOptions = new QueryOptions();
                                dataQuery.setQueryOptions(queryOptions);

                                Backendless.Data.of(Followers.class).find(dataQuery, new AsyncCallback<BackendlessCollection<Followers>>() {

                                    @Override
                                    public void handleResponse(BackendlessCollection<Followers> followersBackendlessCollection) {
                                        List<Followers> followersList = followersBackendlessCollection.getCurrentPage();
                                        if (followersList.size() > 0) {
                                            if (followersList.get(0).getState() == true) {

                                                lp.add(DeatilsList.get(finalQ));
                                                mRecyclerView.setVisibility(View.VISIBLE);
                                            }
                                        }


                                    }

                                    @Override
                                    public void handleFault(BackendlessFault backendlessFault) {

                                    }
                                });

                            }

                        }

                        @Override
                        public void handleFault(BackendlessFault backendlessFault) {
                            Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

                        }
                    });

                    cardViewPostAdapter = new CardViewPostAdapter(getContext(),lp,getActivity());
                    mRecyclerView.setAdapter(cardViewPostAdapter);

                }

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

            }
        };

        Backendless.Persistence.of(Posts.class).find(callback);



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
