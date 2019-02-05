package com.esprit.diasporafinder.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.esprit.diasporafinder.R;
import com.esprit.diasporafinder.activite.UploadingActivity;
import com.esprit.diasporafinder.models.Comments;
import com.esprit.diasporafinder.models.Posts;
import com.esprit.diasporafinder.util.Defaults;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UpdatePostFragment extends Fragment {

    private static String idObject ;
    public static final int RESULT_OK           = -1;
    private static String PREFERENCE_FILENAME = "Id_User";
    private ImageView imagePostEdit;
    private Button uploadPhoto,deletePost,saveChangedPost;
    private EditText editContentPost;
    private String postString, OldPostContent,oldLink;
    boolean editeOnce = false;
    private ProgressDialog progressDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(this.getActivity(), Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION );
        View rootView =  inflater.inflate(R.layout.fragment_update_post, container, false);

        SharedPreferences reportingPref = getContext().getSharedPreferences(PREFERENCE_FILENAME, getContext().MODE_PRIVATE);
        idObject = reportingPref.getString("idObject", "");

        imagePostEdit = (ImageView) rootView.findViewById(R.id.imagePostEdit);
        uploadPhoto = (Button) rootView.findViewById(R.id.uploadPhoto);
        deletePost = (Button) rootView.findViewById(R.id.deletePost);
        saveChangedPost = (Button) rootView.findViewById(R.id.saveChangedPost);
        editContentPost = (EditText) rootView.findViewById(R.id.editContentPost);
        postString = null;




        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause("objectId = '" + idObject + "'");

        QueryOptions queryOptions = new QueryOptions();
        dataQuery.setQueryOptions(queryOptions);

        Backendless.Data.of(Posts.class).find(dataQuery, new AsyncCallback<BackendlessCollection<Posts>>() {

            @Override
            public void handleResponse(BackendlessCollection<Posts> postsBackendlessCollection) {
                List<Posts> listPost = postsBackendlessCollection.getCurrentPage();

                Picasso.with(getContext())
                        .load(listPost.get(0).getLink())
                        .fit()
                        .into(imagePostEdit);
                editContentPost.setText(listPost.get(0).getContent());
                OldPostContent = listPost.get(0).getContent();
                oldLink = listPost.get(0).getLink();
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {

            }
        });




        deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete all Post
                progressDialog = ProgressDialog.show( getContext(), "", "Deleting Post...", true );

                BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                    dataQuery.setWhereClause("objectId = '" + idObject + "'");

                    QueryOptions queryOptions = new QueryOptions();
                    dataQuery.setQueryOptions(queryOptions);

                    Backendless.Data.of(Posts.class).find(dataQuery, new AsyncCallback<BackendlessCollection<Posts>>() {
                                @Override
                                public void handleResponse(BackendlessCollection<Posts> nycPeople) {
                                    List<Posts> lisTa = nycPeople.getCurrentPage();


                                    Backendless.Persistence.of(Posts.class).remove(lisTa.get(0), new AsyncCallback<Long>() {
                                        public void handleResponse(Long response) {
                                            Toast.makeText(getContext(), "Post Deleted!", Toast.LENGTH_LONG).show();
                                            progressDialog.cancel();

                                            FragmentManager fm = getActivity().getSupportFragmentManager();
                                            FragmentTransaction transaction = fm.beginTransaction();
                                            NewsFeedFragment startFragment = new NewsFeedFragment();
                                            transaction.replace(R.id.linearLayoutBase, startFragment);
                                            transaction.addToBackStack("edit");
                                            transaction.commit();

                                        }

                                        public void handleFault(BackendlessFault fault) {
                                            Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                                        }
                                    });


                                    BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                                    dataQuery.setWhereClause("PostID = '" + idObject + "'");

                                    QueryOptions queryOptions = new QueryOptions();
                                    dataQuery.setQueryOptions(queryOptions);

                                    Backendless.Data.of(Comments.class).find(dataQuery, new AsyncCallback<BackendlessCollection<Comments>>() {

                                        @Override
                                        public void handleResponse(BackendlessCollection<Comments> commentsBackendlessCollection) {

                                            List<Comments> listComment = commentsBackendlessCollection.getCurrentPage();
                                            if (listComment.size() > 0) {
                                                Backendless.Persistence.of(Comments.class).remove(listComment.get(0), new AsyncCallback<Long>() {
                                                    public void handleResponse(Long response) {


                                                    }

                                                    public void handleFault(BackendlessFault fault) {
                                                        Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }


                                        }

                                        @Override
                                        public void handleFault(BackendlessFault backendlessFault) {

                                        }
                                    });


                                }

                                @Override
                                public void handleFault(BackendlessFault backendlessFault) {

                                }
                            }
                    );







                }


        });








            saveChangedPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog = ProgressDialog.show( getContext(), "", "Updating Post...", true );


                    if (editeOnce == false) {
                        editeOnce = true;


                        //editPhoto Post
                        if (postString != null) {
                            Posts posts = new Posts();
                            posts.setContent(OldPostContent);
                            posts.setOwnerId(Backendless.UserService.CurrentUser().getObjectId());
                            posts.setObjectId(idObject);
                            posts.setLink(postString);
                            Backendless.Persistence.save(posts, new AsyncCallback<Posts>() {
                                public void handleResponse(Posts response) {


                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                    FragmentTransaction transaction = fm.beginTransaction();
                                    NewsFeedFragment startFragment = new NewsFeedFragment();
                                    transaction.replace(R.id.linearLayoutBase, startFragment);
                                    transaction.addToBackStack("edits");
                                    transaction.commit();
                                    progressDialog.cancel();
                                }

                                public void handleFault(BackendlessFault fault) {
                                    // an error has occurred, the error code can be retrieved with fault.getCode()
                                    Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

                                }
                            });
                        }

                        if(OldPostContent.trim().length() != editContentPost.getText().toString().trim().length()){
                            Posts posts = new Posts();
                            posts.setContent(editContentPost.getText().toString());
                            posts.setOwnerId(Backendless.UserService.CurrentUser().getObjectId());
                            posts.setObjectId(idObject);
                            posts.setLink(oldLink);
                            Backendless.Persistence.save(posts, new AsyncCallback<Posts>() {
                                public void handleResponse(Posts response) {


                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                    FragmentTransaction transaction = fm.beginTransaction();
                                    NewsFeedFragment startFragment = new NewsFeedFragment();
                                    transaction.replace(R.id.linearLayoutBase, startFragment);
                                    transaction.addToBackStack("edits");
                                    transaction.commit();
                                    progressDialog.cancel();
                                }

                                public void handleFault(BackendlessFault fault) {
                                    // an error has occurred, the error code can be retrieved with fault.getCode()
                                    Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

                                }
                            });



                        }







                    }
                }
            });

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Defaults.CAMERA_REQUEST);
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

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        if( resultCode != RESULT_OK )
            return;

        switch( requestCode )
        {
            case Defaults.CAMERA_REQUEST:
                data.setClass( getActivity().getBaseContext(), UploadingActivity.class );
                startActivityForResult( data, Defaults.URL_REQUEST );
                break;

            case Defaults.URL_REQUEST:
                     postString=  (String) data.getExtras().get( Defaults.DATA_TAG );
                Picasso.with(getContext())
                        .load(postString)
                        .fit()
                        .into(imagePostEdit);


        }
    }
}
