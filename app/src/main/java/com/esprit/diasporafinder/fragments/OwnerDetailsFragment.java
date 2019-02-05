package com.esprit.diasporafinder.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.esprit.diasporafinder.adapters.CardViewOwnerDetailsAdapter;
import com.esprit.diasporafinder.models.Followers;
import com.esprit.diasporafinder.models.Historique;
import com.esprit.diasporafinder.models.Posts;
import com.esprit.diasporafinder.util.Defaults;
import com.esprit.diasporafinder.util.LocationAddress;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class OwnerDetailsFragment extends Fragment {

    public static String idOwner;
    double longi;
    double lati;
    private GoogleMap mMap;
    boolean onceSend = false;
    private Button contacter;
    public static String PREFERENCE_FILENAME = "Id_User";
    public static String PREFERENCE_MESSAGE = "idMessage";
    boolean followOnce = false;


    //adapter
    private CardViewOwnerDetailsAdapter cardViewOwnerDetailsAdapter;
    //Recylcler
    private RecyclerView all_post_recycler_view;
    private RecyclerView.LayoutManager mLayoutManager;

    //button
    private Button followButton;

    private String phoneID = null;
    private static View rootView;

    //images
    private ImageView coverDetails, profilDetails;

    //TexteViews
    private TextView nameDetails, countryDetails, emailDetails, followersDetails, followingDetails, TownDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(this.getActivity(), Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION);
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_owner_details, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        SharedPreferences reportingPref = getContext().getSharedPreferences(PREFERENCE_FILENAME, getContext().MODE_PRIVATE);
        idOwner = reportingPref.getString("idOwner", "");


        contacter = (Button) rootView.findViewById(R.id.contacter);

        all_post_recycler_view = (RecyclerView) rootView.findViewById(R.id.all_post_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        all_post_recycler_view.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        all_post_recycler_view.setLayoutManager(mLayoutManager);

        //button
        followButton = (Button) rootView.findViewById(R.id.followButton);

        if (idOwner.equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId())) {
            followButton.setVisibility(View.GONE);
        }

        //Image
        coverDetails = (ImageView) rootView.findViewById(R.id.coverDetails);
        profilDetails = (ImageView) rootView.findViewById(R.id.profilDetails);

        //TextView

        followersDetails = (TextView) rootView.findViewById(R.id.followersDetails);
        nameDetails = (TextView) rootView.findViewById(R.id.nameDetails);
        countryDetails = (TextView) rootView.findViewById(R.id.countryDetails);
        emailDetails = (TextView) rootView.findViewById(R.id.emailDetails);
        followersDetails = (TextView) rootView.findViewById(R.id.followersDetails);
        followingDetails = (TextView) rootView.findViewById(R.id.followingDetails);
        TownDetails = (TextView) rootView.findViewById(R.id.TownDetails);
        mMap = ((SupportMapFragment) (getChildFragmentManager().findFragmentById(R.id.map123))).getMap();

        //Linear


        this.CountingFollow();


        Backendless.Data.of(BackendlessUser.class).find(new AsyncCallback<BackendlessCollection<BackendlessUser>>() {
            @Override
            public void handleResponse(BackendlessCollection<BackendlessUser> users) {
                Iterator<BackendlessUser> userIterator2 = users.getCurrentPage().iterator();

                while (userIterator2.hasNext()) {
                    BackendlessUser user2 = userIterator2.next();

                    if (user2.getObjectId().equalsIgnoreCase(idOwner)) {

                        nameDetails.setText(user2.getProperty("name").toString());
                        countryDetails.setText("From " + user2.getProperty("Nationality").toString());
                        emailDetails.setText(user2.getEmail());
                        try {
                            phoneID = user2.getProperty("phoneID").toString();
                            lati = (double) user2.getProperty("Latitude");
                            longi = (double) user2.getProperty("Longitude");
                        } catch (Exception e) {

                        }
                        Picasso.with(getContext())
                                .load(user2.getProperty("picture").toString())
                                .into(profilDetails);
                        Picasso.with(getContext())
                                .load(user2.getProperty("Cover").toString())
                                .fit()
                                .into(coverDetails);

                        LatLng anomalie = new LatLng(lati, longi);
                        mMap.addMarker(new MarkerOptions().position(anomalie).title(user2.getProperty("name").toString()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle_black_18dp)));

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(anomalie));
                        LocationAddress locationAddress = new LocationAddress();
                        locationAddress.getAddressFromLocation(lati, longi, getContext(), new GeocoderHandler());

                    }
                }
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
            }
        });


        contacter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (onceSend == false) {
                    RechercheHistorique();
                    onceSend = true;
                }


            }
        });


        followButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(followOnce == false){
                    followOnce = true;
                    BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                    dataQuery.setWhereClause("ownerId = '" + Backendless.UserService.CurrentUser().getObjectId() + "' and IdFollowing = '" + idOwner + "'");

                    QueryOptions queryOptions = new QueryOptions();
                    dataQuery.setQueryOptions(queryOptions);

                    Backendless.Data.of(Followers.class).find(dataQuery, new AsyncCallback<BackendlessCollection<Followers>>() {
                        @Override
                        public void handleResponse(BackendlessCollection<Followers> nycPeople) {
                            List<Followers> lisTa = nycPeople.getCurrentPage();
                            if (lisTa.size() > 0) {
                                if (lisTa.get(0).getState() == true) {
                                    lisTa.get(0).setState(false);


                                    Backendless.Persistence.save(lisTa.get(0), new AsyncCallback<Followers>() {
                                        public void handleResponse(Followers response) {

                                            CountingFollow();
                                            followOnce = false;
                                        }

                                        public void handleFault(BackendlessFault fault) {
                                            Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                                        }
                                    });


                                } else if (lisTa.get(0).getState() == false) {



                                    lisTa.get(0).setState(true);


                                    Backendless.Persistence.save(lisTa.get(0), new AsyncCallback<Followers>() {
                                        public void handleResponse(Followers response) {
                                            // LikesDez();
                                            CountingFollow();
                                            followOnce = false;

                                        }

                                        public void handleFault(BackendlessFault fault) {
                                            Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                                        }
                                    });


                                }
                            }
                            if (lisTa.size() == 0) {

                                Followers md = new Followers();

                                md.setState(true);
                                md.setOwnerId(Backendless.UserService.CurrentUser().getObjectId());
                                md.setIdFollowing(idOwner);
                                Backendless.Persistence.save(md, new AsyncCallback<Followers>() {
                                    public void handleResponse(Followers response) {
                                        //LikesDez();
                                        CountingFollow();
                                        followOnce = false;

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


        this.PostsNumberFunction();


        return rootView;
    }

    public void CountingFollow() {


        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause("ownerId = '" + Backendless.UserService.CurrentUser().getObjectId() + "' and idFollowing = '" + idOwner + "'");

        QueryOptions queryOptions = new QueryOptions();
        dataQuery.setQueryOptions(queryOptions);

        Backendless.Data.of(Followers.class).find(dataQuery, new AsyncCallback<BackendlessCollection<Followers>>() {

            @Override
            public void handleResponse(BackendlessCollection<Followers> followersBackendlessCollection) {
                List<Followers> firstPage = followersBackendlessCollection.getCurrentPage();

                if(firstPage.size() >0){

                if (firstPage.get(0).getState() == true) {
                        followButton.setText("Unfollow");

                    } else if (firstPage.get(0).getState() == false) {
                        followButton.setText("Follow");

                    }
                } else {
                    followButton.setText("Follow");
                }
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

            }
        });





            AsyncCallback<BackendlessCollection<Followers>> callback2 = new AsyncCallback<BackendlessCollection<Followers>>() {
            @Override
            public void handleResponse(BackendlessCollection<Followers> response) {
                List<Followers> firstPage = response.getCurrentPage();
                int a = 0;
                int b = 0;
                for (int j = 0; j < firstPage.size(); j++) {
                    if (firstPage.get(j).getIdFollowing().equalsIgnoreCase(idOwner)) {
                        if (firstPage.get(j).getState() == true) {
                            a++;

                        }
                    }
                    if (firstPage.get(j).getOwnerId().equalsIgnoreCase(idOwner)) {
                        b++;
                    }

                }

                if(a >=1){
                    a--;
                }
                if (b >= 1){
                    b--;
                }
                followersDetails.setText(a + " Followers");
                followingDetails.setText(b + " Following");
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

            }
        };

        Backendless.Persistence.of(Followers.class).find(callback2);

    }


    public void PostsNumberFunction() {
        AsyncCallback<BackendlessCollection<Posts>> callback = new AsyncCallback<BackendlessCollection<Posts>>() {
            @Override
            public void handleResponse(BackendlessCollection<Posts> response) {
                final List<Posts> DeatilsList = response.getCurrentPage();
                List<Posts> lo = new ArrayList<>();
                for (int q = 0; q < DeatilsList.size(); q++) {
                    if (DeatilsList.get(q).getOwnerId().equalsIgnoreCase(idOwner)) {
                        lo.add(DeatilsList.get(q));
                    }
                }



              /*  cardViewPostAdapter = new CardViewPostAdapter(getContext(),firstPage,getActivity());
                mRecyclerView.setAdapter(cardViewPostAdapter);*/

                cardViewOwnerDetailsAdapter = new CardViewOwnerDetailsAdapter(getContext(), lo, getActivity());
                all_post_recycler_view.setAdapter(cardViewOwnerDetailsAdapter);

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

            }
        };

        Backendless.Persistence.of(Posts.class).find(callback);


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
            TownDetails.setText(locationAddress);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        onBackPressed();
    }

    public void onBackPressed() {
        if (getView() != null) {
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


    public void RechercheHistorique() {

        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause("ownerId = '" + Backendless.UserService.CurrentUser().getObjectId() + "' and idReceiver = '" + idOwner + "'");

        QueryOptions queryOptions = new QueryOptions();
        dataQuery.setQueryOptions(queryOptions);

        Backendless.Data.of(Historique.class).find(dataQuery, new AsyncCallback<BackendlessCollection<Historique>>() {

            @Override
            public void handleResponse(BackendlessCollection<Historique> historiqueBackendlessCollection) {

                List <Historique> hist = historiqueBackendlessCollection.getCurrentPage();

                if(hist.size() > 0){
                     FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    MessageFragment startFragment = new MessageFragment();
                    SharedPreferences reportingPref =getContext().getSharedPreferences(PREFERENCE_MESSAGE,  getContext().MODE_PRIVATE);
                    SharedPreferences.Editor prefEditor = reportingPref.edit();
                    prefEditor.putString("idDiscussion", hist.get(0).getIdDiscussion());
                    prefEditor.commit();
                    transaction.replace(R.id.linearLayoutBase,startFragment);
                    transaction.addToBackStack("Owner");
                    transaction.commit();
                }
                else {

                    BackendlessDataQuery dataQuery2 = new BackendlessDataQuery();
                    dataQuery2.setWhereClause("idReceiver = '" + Backendless.UserService.CurrentUser().getObjectId() + "' and ownerId = '" + idOwner + "'");

                    QueryOptions queryOptions2 = new QueryOptions();
                    dataQuery2.setQueryOptions(queryOptions2);

                    Backendless.Data.of(Historique.class).find(dataQuery2, new AsyncCallback<BackendlessCollection<Historique>>() {

                        @Override
                        public void handleResponse(BackendlessCollection<Historique> historiqueBackendlessCollection) {

                            List <Historique> hist2 = historiqueBackendlessCollection.getCurrentPage();

                            if(hist2.size() > 0){
                                 FragmentManager fm = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fm.beginTransaction();
                                MessageFragment startFragment = new MessageFragment();
                                SharedPreferences reportingPref =getContext().getSharedPreferences(PREFERENCE_MESSAGE,  getContext().MODE_PRIVATE);
                                SharedPreferences.Editor prefEditor = reportingPref.edit();
                                prefEditor.putString("idDiscussion", hist2.get(0).getIdDiscussion());
                                prefEditor.commit();
                                transaction.replace(R.id.linearLayoutBase,startFragment);
                                transaction.addToBackStack("Owner");
                                transaction.commit();
                            }
                            else {


                                 Historique historique = new Historique();

                                historique.setIdDiscussion(Backendless.UserService.CurrentUser().getObjectId()+""+idOwner);
                                historique.setIdReceiver(idOwner);
                                historique.setOwnerId(Backendless.UserService.CurrentUser().getObjectId());


                                Backendless.Persistence.save(historique, new AsyncCallback<Historique>() {
                                    public void handleResponse(Historique response) {

                                        FragmentManager fm = getActivity().getSupportFragmentManager();
                                        FragmentTransaction transaction = fm.beginTransaction();
                                        MessageFragment startFragment = new MessageFragment();
                                        SharedPreferences reportingPref =getContext().getSharedPreferences(PREFERENCE_MESSAGE,  getContext().MODE_PRIVATE);
                                        SharedPreferences.Editor prefEditor = reportingPref.edit();
                                        prefEditor.putString("idDiscussion", response.getIdDiscussion());
                                        prefEditor.commit();
                                        transaction.replace(R.id.linearLayoutBase,startFragment);
                                        transaction.addToBackStack("Owner");
                                        transaction.commit();


                                    }

                                    public void handleFault(BackendlessFault fault) {
                                        // an error has occurred, the error code can be retrieved with fault.getCode()
                                        Toast.makeText( getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG ).show();

                                    }
                                });


                            }

                        }

                        @Override
                        public void handleFault(BackendlessFault backendlessFault) {
                            Toast.makeText( getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG ).show();

                        }
                    });



                }

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Toast.makeText( getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG ).show();

            }
        });


    }
}
