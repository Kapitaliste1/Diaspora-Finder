package com.esprit.diasporafinder.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
import com.backendless.BackendlessUser;
import com.backendless.IDataStore;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.esprit.diasporafinder.R;
import com.esprit.diasporafinder.activite.LoginActivity;
import com.esprit.diasporafinder.activite.UploadingActivity;
import com.esprit.diasporafinder.models.Comments;
import com.esprit.diasporafinder.models.Followers;
import com.esprit.diasporafinder.models.ImageEntity;
import com.esprit.diasporafinder.models.LikesMotion;
import com.esprit.diasporafinder.models.Posts;
import com.esprit.diasporafinder.util.Defaults;
import com.esprit.diasporafinder.util.DownloadImageTask;

import java.util.Iterator;
import java.util.List;


public class ToolsFragment extends Fragment {
    /** Standard activity result: operation canceled. */
    public static final int RESULT_CANCELED    = 0;
    /** Standard activity result: operation succeeded. */
    public static final int RESULT_OK           = -1;
    /** Start of user-defined activity results. */
    public static final int RESULT_FIRST_USER   = 1;
    private ProgressDialog progressDialog;

    private boolean trust = false;
    private EditText emailSetting,nameSetting,password2,password3;
    private Button saveChanges,updatePicture,updateCover,deleteCover,deletePicture,deleteProfil;
    private ImageView imageProfil, imageCover;
    private EditText existingEmail;
    private String profilString,CoverString;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(this.getActivity(), Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION );
         View rootView =  inflater.inflate(R.layout.fragment_tools, container, false);
        profilString = null;
        CoverString = null;
        //EditText
         existingEmail = (EditText) rootView.findViewById(R.id.existingEmail);
        emailSetting = (EditText) rootView.findViewById(R.id.emailSetting);
        nameSetting = (EditText) rootView.findViewById(R.id.nameSetting);
         password2 = (EditText) rootView.findViewById(R.id.passwod2);
        password3  = (EditText) rootView.findViewById(R.id.passwod3);

        //Images
        imageProfil = (ImageView) rootView.findViewById(R.id.profilSetting);
        imageCover = (ImageView) rootView.findViewById(R.id.coverSetting);

        //Button
        updatePicture =(Button) rootView.findViewById(R.id.updatePicture);
        saveChanges = (Button) rootView.findViewById(R.id.saveChanges);
        deletePicture = (Button) rootView.findViewById(R.id.deletePicture);
        updateCover = (Button) rootView.findViewById(R.id.updateCover);
        deleteCover = (Button) rootView.findViewById(R.id.deleteCover);
        deleteProfil = (Button) rootView.findViewById(R.id.deleteProfil);

        new DownloadImageTask( imageProfil).execute("" + Backendless.UserService.CurrentUser().getProperty("picture"));
        new DownloadImageTask( imageCover).execute("" + Backendless.UserService.CurrentUser().getProperty("Cover"));

        deleteProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {



                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked

                                progressDialog = ProgressDialog.show( view.getContext(), "", "Deleting Account...", true );

                                //delete all comments
                                AsyncCallback<BackendlessCollection<Comments>> callback3 = new AsyncCallback<BackendlessCollection<Comments>>()
                                {
                                    @Override
                                    public void handleResponse( BackendlessCollection<Comments> response )
                                    {
                                        List<Comments> firstPage = response.getCurrentPage();
                                        for(int j = 0; j < firstPage.size(); j ++){
                                            if(firstPage.get(j).getOwnerId().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId())){

                                                Backendless.Persistence.of( Comments.class ).remove( firstPage.get(j), new AsyncCallback<Long>()
                                                {
                                                    public void handleResponse( Long response )
                                                    {
                                                        // Contact has been deleted. The response is a time in milliseconds when the object was deleted
                                                    }
                                                    public void handleFault( BackendlessFault fault )
                                                    {
                                                        // an error has occurred, the error code can be retrieved with fault.getCode()
                                                    }
                                                } );
                                            }
                                        }
                                    }
                                    @Override
                                    public void handleFault( BackendlessFault fault )
                                    {
                                        Toast.makeText( getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

                                    }
                                };

                                Backendless.Persistence.of( Comments.class ).find(callback3);



                                //delete all Post
                                AsyncCallback<BackendlessCollection<Posts>> callback9 = new AsyncCallback<BackendlessCollection<Posts>>()
                                {
                                    @Override
                                    public void handleResponse( BackendlessCollection<Posts> response )
                                    {
                                        List<Posts> firstPage = response.getCurrentPage();
                                        for(int j = 0; j < firstPage.size(); j ++){
                                            if(firstPage.get(j).getOwnerId().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId())){

                                                Backendless.Persistence.of( Posts.class ).remove( firstPage.get(j), new AsyncCallback<Long>()
                                                {
                                                    public void handleResponse( Long response )
                                                    {
                                                        // Contact has been deleted. The response is a time in milliseconds when the object was deleted
                                                    }
                                                    public void handleFault( BackendlessFault fault )
                                                    {
                                                        // an error has occurred, the error code can be retrieved with fault.getCode()
                                                    }
                                                } );
                                            }
                                        }
                                    }
                                    @Override
                                    public void handleFault( BackendlessFault fault )
                                    {
                                        Toast.makeText( getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

                                    }
                                };

                                Backendless.Persistence.of( Posts.class ).find(callback9);

                                //delete all Likes
                                AsyncCallback<BackendlessCollection<LikesMotion>> callback8 = new AsyncCallback<BackendlessCollection<LikesMotion>>()
                                {
                                    @Override
                                    public void handleResponse( BackendlessCollection<LikesMotion> response )
                                    {
                                        List<LikesMotion> firstPage = response.getCurrentPage();
                                        for(int j = 0; j < firstPage.size(); j ++){
                                            if(firstPage.get(j).getOwnerId().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId())){

                                                Backendless.Persistence.of( LikesMotion.class ).remove( firstPage.get(j), new AsyncCallback<Long>()
                                                {
                                                    public void handleResponse( Long response )
                                                    {
                                                        // Contact has been deleted. The response is a time in milliseconds when the object was deleted
                                                    }
                                                    public void handleFault( BackendlessFault fault )
                                                    {
                                                        // an error has occurred, the error code can be retrieved with fault.getCode()
                                                    }
                                                } );
                                            }
                                        }
                                    }
                                    @Override
                                    public void handleFault( BackendlessFault fault )
                                    {
                                        Toast.makeText( getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

                                    }
                                };

                                Backendless.Persistence.of( LikesMotion.class ).find(callback8);


                                //delete all Follow
                                AsyncCallback<BackendlessCollection<Followers>> callback7 = new AsyncCallback<BackendlessCollection<Followers>>()
                                {
                                    @Override
                                    public void handleResponse( BackendlessCollection<Followers> response )
                                    {
                                        List<Followers> firstPage = response.getCurrentPage();
                                        for(int j = 0; j < firstPage.size(); j ++){
                                            if(firstPage.get(j).getOwnerId().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId())){

                                                Backendless.Persistence.of( Followers.class ).remove( firstPage.get(j), new AsyncCallback<Long>()
                                                {
                                                    public void handleResponse( Long response )
                                                    {
                                                        // Contact has been deleted. The response is a time in milliseconds when the object was deleted
                                                    }
                                                    public void handleFault( BackendlessFault fault )
                                                    {
                                                        // an error has occurred, the error code can be retrieved with fault.getCode()
                                                    }
                                                } );
                                            }

                                            if(firstPage.get(j).getIdFollowing().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId())){

                                                Backendless.Persistence.of( Followers.class ).remove( firstPage.get(j), new AsyncCallback<Long>()
                                                {
                                                    public void handleResponse( Long response )
                                                    {
                                                        // Contact has been deleted. The response is a time in milliseconds when the object was deleted
                                                    }
                                                    public void handleFault( BackendlessFault fault )
                                                    {
                                                        // an error has occurred, the error code can be retrieved with fault.getCode()
                                                    }
                                                } );
                                            }
                                        }
                                    }
                                    @Override
                                    public void handleFault( BackendlessFault fault )
                                    {
                                        Toast.makeText( getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

                                    }
                                };

                                Backendless.Persistence.of( Followers.class ).find(callback7);


                                //delete all Images
                                AsyncCallback<BackendlessCollection<ImageEntity>> callback6 = new AsyncCallback<BackendlessCollection<ImageEntity>>()
                                {
                                    @Override
                                    public void handleResponse( BackendlessCollection<ImageEntity> response )
                                    {
                                        List<ImageEntity> firstPage = response.getCurrentPage();
                                        for(int j = 0; j < firstPage.size(); j ++){
                                            if(firstPage.get(j).getOwnerId().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId())){

                                                Backendless.Persistence.of( ImageEntity.class ).remove( firstPage.get(j), new AsyncCallback<Long>()
                                                {
                                                    public void handleResponse( Long response )
                                                    {
                                                        // Contact has been deleted. The response is a time in milliseconds when the object was deleted
                                                    }
                                                    public void handleFault( BackendlessFault fault )
                                                    {
                                                        // an error has occurred, the error code can be retrieved with fault.getCode()
                                                    }
                                                } );
                                            }
                                        }
                                    }
                                    @Override
                                    public void handleFault( BackendlessFault fault )
                                    {
                                        Toast.makeText( getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

                                    }
                                };

                                Backendless.Persistence.of( ImageEntity.class ).find(callback6);





                                final IDataStore<BackendlessUser> dataStore = Backendless.Data.of( BackendlessUser.class );
                                dataStore.findById(Backendless.UserService.CurrentUser().getObjectId(), new AsyncCallback<BackendlessUser>() {
                                    @Override
                                    public void handleResponse(BackendlessUser backendlessUser) {
                                        dataStore.remove(backendlessUser, new AsyncCallback<Long>() {
                                            @Override
                                            public void handleResponse(Long aLong) {



                                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                                getActivity().finish();
                                            }

                                            @Override
                                            public void handleFault(BackendlessFault backendlessFault) {
                                                System.out.println("Server reported an error " + backendlessFault.getMessage());
                                            }
                                        });
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault backendlessFault) {
                                        System.out.println("Server reported an error " + backendlessFault.getMessage());
                                    }
                                });
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                Toast.makeText(getActivity(), "No !", Toast.LENGTH_LONG).show();

                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();



            }


        });

        deletePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackendlessUser user2 = Backendless.UserService.CurrentUser();

                user2.setProperty("picture", "https://goo.gl/MSNTU7");
                Backendless.UserService.update(user2, new AsyncCallback<BackendlessUser>() {
                    public void handleResponse(BackendlessUser user) {
                        profilString = null;
                        Toast.makeText(getActivity(), "Profil Picture removed successfully!", Toast.LENGTH_LONG).show();

                    }

                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                    }
                });
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                NewsFeedFragment fragment2 = new NewsFeedFragment();
                fragmentTransaction2.replace(R.id.linearLayoutBase, fragment2);
                fragmentTransaction2.addToBackStack(null);
                fragmentTransaction2.commit();
            }


        });

        deleteCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackendlessUser user2 = Backendless.UserService.CurrentUser();

                user2.setProperty("Cover", "https://goo.gl/k7xgdC");
                Backendless.UserService.update(user2, new AsyncCallback<BackendlessUser>() {
                    public void handleResponse(BackendlessUser user) {
                        CoverString = null;
                        Toast.makeText(getActivity(), "Cover Picture removed successfully!", Toast.LENGTH_LONG).show();

                    }

                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                    }
                });
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                NewsFeedFragment fragment2 = new NewsFeedFragment();
                fragmentTransaction2.replace(R.id.linearLayoutBase, fragment2);
                fragmentTransaction2.addToBackStack(null);
                fragmentTransaction2.commit();
            }


        });


        updateCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trust = false;
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Defaults.CAMERA_REQUEST);
            }


        });


        updatePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trust = true;
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Defaults.CAMERA_REQUEST);
            }


        });





        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //profil picture
                if (CoverString != null) {
                    BackendlessUser user2 = Backendless.UserService.CurrentUser();

                    user2.setProperty("Cover", CoverString);
                    Backendless.UserService.update(user2, new AsyncCallback<BackendlessUser>() {
                        public void handleResponse(BackendlessUser user) {
                            CoverString = null;
                            Toast.makeText(getActivity(), "Cover Picture changed successfully!", Toast.LENGTH_LONG).show();

                        }

                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                //cover picture
                if (profilString != null) {
                    BackendlessUser user2 = Backendless.UserService.CurrentUser();

                    user2.setProperty("picture", profilString);
                    Backendless.UserService.update(user2, new AsyncCallback<BackendlessUser>() {
                        public void handleResponse(BackendlessUser user) {
                            profilString = null;
                            Toast.makeText(getActivity(), "Profil Picture changed successfully!", Toast.LENGTH_LONG).show();

                        }

                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                //Email changing
                if (existingEmail.getText().toString().equalsIgnoreCase("" + Backendless.UserService.CurrentUser().getEmail())) {
                    System.out.println("Je suis dedans ");

                    final BackendlessUser user1 = Backendless.UserService.CurrentUser();

                    user1.setEmail(emailSetting.getText().toString());





                    Backendless.Data.of( BackendlessUser.class ).find(new AsyncCallback<BackendlessCollection<BackendlessUser>>() {
                        @Override
                        public void handleResponse(BackendlessCollection<BackendlessUser> users) {
                            Iterator<BackendlessUser> userIterator = users.getCurrentPage().iterator();

                            while (userIterator.hasNext()) {
                                BackendlessUser user = userIterator.next();

                                if (user.getEmail().equalsIgnoreCase(user1.getEmail())) {
                                    Toast.makeText(getActivity(), "This email is already used, please change it!", Toast.LENGTH_LONG).show();

                                } else {
                                    Backendless.UserService.update(user1, new AsyncCallback<BackendlessUser>() {
                                        public void handleResponse(BackendlessUser user) {
                                            existingEmail.setText(null);
                                            emailSetting.setText(null);
                                            Toast.makeText(getActivity(), "Eamil changed successfully!", Toast.LENGTH_LONG).show();
                                        }

                                        public void handleFault(BackendlessFault fault) {
                                            Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void handleFault(BackendlessFault backendlessFault) {
                            Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                        }
                    });




                }


                //name changing
                if (nameSetting.getText().toString().trim().length() > 0) {
                    BackendlessUser user2 = Backendless.UserService.CurrentUser();

                    user2.setProperty("name", nameSetting.getText().toString());
                    Backendless.UserService.update(user2, new AsyncCallback<BackendlessUser>() {
                        public void handleResponse(BackendlessUser user) {
                            nameSetting.setText(null);
                            Toast.makeText(getActivity(), "Name changed successfully!", Toast.LENGTH_LONG).show();

                        }

                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                //password reset
                if ((password2.getText().toString().trim().length() > 0) && (password3.getText().toString().trim().length() > 0)) {
                    BackendlessUser user2 = Backendless.UserService.CurrentUser();

                    if (password2.getText().toString().trim().equalsIgnoreCase(password3.getText().toString().trim())) {
                        user2.setPassword(password2.getText().toString());
                        Backendless.Data.of(BackendlessUser.class).save(user2, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser backendlessUser) {
                                password2.setText(null);
                                password3.setText(null);

                                Toast.makeText(getActivity(), "Password reset successfully!", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void handleFault(BackendlessFault backendlessFault) {
                                Toast.makeText(getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "New Password Doesn't match!", Toast.LENGTH_LONG).show();
                    }

                }

                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                NewsFeedFragment fragment2 = new NewsFeedFragment();
                fragmentTransaction2.replace(R.id.linearLayoutBase, fragment2);
                fragmentTransaction2.addToBackStack(null);
                fragmentTransaction2.commit();


            }
        });






        return rootView;


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
                if(trust == true){
                    profilString=  (String) data.getExtras().get( Defaults.DATA_TAG );
                    new DownloadImageTask( imageProfil).execute("" + profilString);

                }
                else if(trust == false){
                    CoverString =  (String) data.getExtras().get( Defaults.DATA_TAG );
                    new DownloadImageTask( imageCover).execute("" +  CoverString);

                }
        }
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
