package com.esprit.diasporafinder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.esprit.diasporafinder.R;
import com.esprit.diasporafinder.adapters.CardViewHistoriqueMessagesAdapter;
import com.esprit.diasporafinder.models.Historique;
import com.esprit.diasporafinder.util.Defaults;

import java.util.ArrayList;
import java.util.List;


public class HistoriqueFragment extends Fragment {


    private CardViewHistoriqueMessagesAdapter cardViewHistoriqueMessagesAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(this.getActivity(), Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION);
        View rootView =  inflater.inflate(R.layout.fragment_historique, container, false);



        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.historique_recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        HistoriqueFunction();
        return rootView;
    }



    public void HistoriqueFunction(){

        AsyncCallback<BackendlessCollection<Historique>> callback = new AsyncCallback<BackendlessCollection<Historique>>() {
            @Override
            public void handleResponse(BackendlessCollection<Historique> response) {
                final List<Historique> lp = new ArrayList<Historique>();
                final List<Historique> DeatilsList = response.getCurrentPage();
                for (int q = 0; q < DeatilsList.size(); q++) {

                    if (DeatilsList.get(q).getIdReceiver().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId())){
                        lp.add(DeatilsList.get(q));
                    }

                    if(DeatilsList.get(q).getOwnerId().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId())){
                        lp.add(DeatilsList.get(q));
                    }


                    cardViewHistoriqueMessagesAdapter = new CardViewHistoriqueMessagesAdapter(getContext(),lp,getActivity());
                    mRecyclerView.setAdapter(cardViewHistoriqueMessagesAdapter);
                }

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

            }
        };

        Backendless.Persistence.of(Historique.class).find(callback);



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
