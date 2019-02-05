package com.esprit.diasporafinder.fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.esprit.diasporafinder.R;
import com.esprit.diasporafinder.adapters.CardViewFinder;
import com.esprit.diasporafinder.util.Defaults;

import java.util.ArrayList;
import java.util.List;

public class FinderFragment extends Fragment {

    private float distance;
    private CardViewFinder cardViewFinder;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button showAll,sameCountry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(this.getActivity(), Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION);
        View rootView = inflater.inflate(R.layout.fragment_finder, container, false);

        sameCountry = (Button) rootView.findViewById(R.id.sameCountry);
        showAll = (Button) rootView.findViewById(R.id.showAll);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view_finder);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);



        //ALL Countries
        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Backendless.Data.of( BackendlessUser.class ).find(new AsyncCallback<BackendlessCollection<BackendlessUser>>() {
                    @Override
                    public void handleResponse(BackendlessCollection<BackendlessUser> users) {
                        final List<BackendlessUser> lp = new ArrayList<BackendlessUser>();
                        final List<BackendlessUser> DeatilsList = users.getCurrentPage();
                        for (int q = 0; q < DeatilsList.size(); q++) {


                                    lp.add(DeatilsList.get(q));

                            cardViewFinder = new CardViewFinder(getContext(), lp, getActivity());
                            mRecyclerView.setAdapter(cardViewFinder);
                        }


                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {
                        Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


        //SAME COUNTRY
        sameCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Backendless.Data.of( BackendlessUser.class ).find(new AsyncCallback<BackendlessCollection<BackendlessUser>>() {
                    @Override
                    public void handleResponse(BackendlessCollection<BackendlessUser> users) {
                        final List<BackendlessUser> lp = new ArrayList<BackendlessUser>();
                        final List<BackendlessUser> DeatilsList = users.getCurrentPage();
                        for (int q = 0; q < DeatilsList.size(); q++) {

                            if (DeatilsList.get(q).getProperty("Nationality").toString().equalsIgnoreCase(Backendless.UserService.CurrentUser().getProperty("Nationality").toString())) {
                                try {
                                    Location locationA = new Location("point A");
                                    locationA.setLatitude((Double) Backendless.UserService.CurrentUser().getProperty("Latitude"));
                                    locationA.setLongitude((Double) Backendless.UserService.CurrentUser().getProperty("Longitude"));
                                    Location locationB = new Location("point B");
                                    locationB.setLatitude((Double) DeatilsList.get(q).getProperty("Latitude"));
                                    locationB.setLongitude((Double) DeatilsList.get(q).getProperty("Longitude"));
                                    distance = locationA.distanceTo(locationB) / 1000;
                                } catch (Exception e) {
                                    System.out.println("sick!!!!!!!!!!!");
                                }

                                if (distance <= 100.00) {
                                    lp.add(DeatilsList.get(q));
                                }
                            }


                            cardViewFinder = new CardViewFinder(getContext(), lp, getActivity());
                            mRecyclerView.setAdapter(cardViewFinder);
                        }


                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {
                        Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });




        Backendless.Data.of( BackendlessUser.class ).find(new AsyncCallback<BackendlessCollection<BackendlessUser>>() {
            @Override
            public void handleResponse(BackendlessCollection<BackendlessUser> users) {
                final List<BackendlessUser> lp = new ArrayList<BackendlessUser>();
                final List<BackendlessUser> DeatilsList = users.getCurrentPage();
                for (int q = 0; q < DeatilsList.size(); q++) {

                    if (DeatilsList.get(q).getProperty("Nationality").toString().equalsIgnoreCase(Backendless.UserService.CurrentUser().getProperty("Nationality").toString())) {
                        try {
                            Location locationA = new Location("point A");
                            locationA.setLatitude((Double) Backendless.UserService.CurrentUser().getProperty("Latitude"));
                            locationA.setLongitude((Double) Backendless.UserService.CurrentUser().getProperty("Longitude"));
                            Location locationB = new Location("point B");
                            locationB.setLatitude((Double) DeatilsList.get(q).getProperty("Latitude"));
                            locationB.setLongitude((Double) DeatilsList.get(q).getProperty("Longitude"));
                            distance = locationA.distanceTo(locationB) / 1000;
                        } catch (Exception e) {
                            System.out.println("sick!!!!!!!!!!!");
                        }

                        if (distance <= 100.00) {
                            lp.add(DeatilsList.get(q));
                        }
                    }


                    cardViewFinder = new CardViewFinder(getContext(), lp, getActivity());
                    mRecyclerView.setAdapter(cardViewFinder);
                }


            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
            }
        });



        return rootView;
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
