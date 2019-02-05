package com.esprit.diasporafinder.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.esprit.diasporafinder.adapters.CardViewMessageAdapter;
import com.esprit.diasporafinder.models.Discussion;
import com.esprit.diasporafinder.util.DefaultCallback2;
import com.esprit.diasporafinder.util.DefaultMessages;
import com.esprit.diasporafinder.util.Defaults;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageFragment extends Fragment {


    public static String  idDiscussion;
    public static String idOtherUser;
    private CardViewMessageAdapter cardViewMessageAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button SendMessage;
    private EditText contentMessage;
    public static String PREFERENCE_FILENAME = "idMessage";
    boolean sendOnce = false;
    private SwipeRefreshLayout swipeRefreshLayout  ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(this.getActivity(), Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION);
        View rootView =  inflater.inflate(R.layout.fragment_message, container, false);

        SendMessage = (Button) rootView.findViewById(R.id.SendMessage);
        contentMessage = (EditText) rootView.findViewById(R.id.contentMessage);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);



        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view_messages);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        final SharedPreferences reportingPref = getContext().getSharedPreferences(PREFERENCE_FILENAME, getContext().MODE_PRIVATE);
        idDiscussion = reportingPref.getString("idDiscussion", "");



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });



        GetAllMessagesFunction();
        SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sendOnce == false){
                    sendOnce = true;
                    if(contentMessage.getText().toString().trim().length() > 0){
                        Discussion discussion = new Discussion();

                        discussion.setContent(contentMessage.getText().toString());
                        discussion.setOwnerId(Backendless.UserService.CurrentUser().getObjectId());
                        discussion.setIdDicussion(reportingPref.getString("idDiscussion", ""));
                        discussion.setIdReceiver(reportingPref.getString("idInterlocuteur", ""));


                        Backendless.Persistence.save(discussion, new AsyncCallback<Discussion>() {
                            public void handleResponse(Discussion response) {

                                GetAllMessagesFunction();
                                sendOnce=false;
                                contentMessage.setText(null);
                                final PublishOptions publishOptions = new PublishOptions();
                                publishOptions.putHeader("android-ticker-text", "You just got a private push notification!");
                                publishOptions.putHeader("android-content-title", "Diaspora Finder");
                               // publishOptions.putHeader("android-content-interface", "message");
                                //publishOptions.putHeader("android-content-idPost",reportingPref.getString("idDiscussion", ""));
                                publishOptions.putHeader("android-content-text", "New message from " + Backendless.UserService.CurrentUser().getProperty("name").toString());


                                BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                                dataQuery.setWhereClause("objectId = '" + response.getIdReceiver() + "'");

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
                                            Toast.makeText(getContext(), user.getProperty("phoneID").toString(), Toast.LENGTH_LONG).show();


                                            Backendless.Messaging.publish(Defaults.DEFAULT_CHANNEL, DefaultMessages.ACCEPT_CHAT_MESSAGE, publishOptions, deliveryOptions, new DefaultCallback2<MessageStatus>(getActivity()) {
                                                @Override
                                                public void handleResponse(MessageStatus response) {
                                                    super.handleResponse(response);
                                                    if (response.getStatus() == PublishStatusEnum.SCHEDULED) {
                                                        Toast.makeText(getActivity(), "good", Toast.LENGTH_LONG).show();

                                                    } else {
                                                        Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
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

                            public void handleFault(BackendlessFault fault) {
                                // an error has occurred, the error code can be retrieved with fault.getCode()
                                Toast.makeText( getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

                            }
                        });
                    }

                }


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

    void refreshItems() {
        // Load items
        // ...
        GetAllMessagesFunction();

        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
    }
    public void GetAllMessagesFunction(){

        AsyncCallback<BackendlessCollection<Discussion>> callback = new AsyncCallback<BackendlessCollection<Discussion>>() {
            @Override
            public void handleResponse(BackendlessCollection<Discussion> response) {
                final List<Discussion> lp = new ArrayList<Discussion>();
                final List<Discussion> DeatilsList = response.getCurrentPage();
                for (int q = 0; q < DeatilsList.size(); q++) {

                    if (DeatilsList.get(q).getIdDicussion().equalsIgnoreCase(idDiscussion)){
                        lp.add(DeatilsList.get(q));
                    }



                    cardViewMessageAdapter = new CardViewMessageAdapter(getContext(),lp,getActivity());
                    mRecyclerView.setAdapter(cardViewMessageAdapter);
                }

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

            }
        };

        Backendless.Persistence.of(Discussion.class).find(callback);



    }
}
