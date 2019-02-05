package com.esprit.diasporafinder.activite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.exceptions.BackendlessException;
import com.esprit.diasporafinder.R;
import com.esprit.diasporafinder.models.Posts;
import com.esprit.diasporafinder.util.Defaults;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

public class NewPostActivity extends AppCompatActivity {
    private EditText contentPosts;
    private Button uplPhoto, Valider;
    private Posts po;
    boolean postOnce = false;
    private String imgURL = null;
    private CardView imageNewPostVisible;
    private ImageView imageNewPost;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        contentPosts = (EditText) findViewById( R.id.contentPost );
        imageNewPostVisible = (CardView) findViewById(R.id.imageNewPostVisible);
        imageNewPostVisible.setVisibility(View.GONE);
        imageNewPost = (ImageView) findViewById(R.id.imageNewPost);

        initUI();
    }


    private void initUI()
    {
        Valider = (Button) findViewById( R.id.publish );
        uplPhoto = (Button) findViewById( R.id.uploadPhoto );



        Valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    if(contentPosts.getText().length()<=0)
                    {
                        if(imgURL == null){
                            Toast.makeText(view.getContext(), "Please add picture !", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(view.getContext(), "Please write something !", Toast.LENGTH_SHORT).show();
                        }

                    }else  if(contentPosts.getText().length()>=0){
                        if(imgURL != null){
                            if(postOnce == false)
                            {
                                postOnce= true;
                                progressDialog = ProgressDialog.show( view.getContext(), "", "Publishing...", true );
                                createPostsRecord();
                            }
                        }else if(imgURL == null){
                            Toast.makeText(view.getContext(), "Please add picture !", Toast.LENGTH_SHORT).show();

                        }
                    }
                 }
                catch (BackendlessException e){

                    System.out.println("");
               }
             }
        });

        uplPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Defaults.CAMERA_REQUEST);
            }
        });

    }

    //create post
    private void createPostsRecord()
    {
        Posts posts = null;

        try
        {

            posts = new CreatePostsRecordTask().execute().get( 30, TimeUnit.SECONDS );
            po = posts;
        }
        catch ( Exception e )
        {
            Toast.makeText(this, "Please retry", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent nextIntent = new Intent( NewPostActivity.this, HomePageActivity.class );
        startActivity(nextIntent);
        finish();
    }


    //post class
    class CreatePostsRecordTask extends AsyncTask<Void, Void, Posts>
    {
        Posts posts = new Posts();

        @Override
        protected void onPreExecute()
        {
             posts.setOwnerId(Backendless.UserService.CurrentUser().getObjectId().toString());
            posts.setLink(imgURL);
            posts.setContent(contentPosts.getText().toString());
         }

        @Override
        protected Posts doInBackground( Void... voids )
        {
            try{
                return posts.save();

            }
            catch (BackendlessException e){


            }

            return null;
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
                data.setClass( getBaseContext(), UploadingActivity.class );
                startActivityForResult( data, Defaults.URL_REQUEST );
                break;

            case Defaults.URL_REQUEST:
                imgURL =  (String) data.getExtras().get( Defaults.DATA_TAG );
                Picasso.with(this)
                        .load(imgURL)
                        .fit()
                        .into(imageNewPost);
                imageNewPostVisible.setVisibility(View.VISIBLE);
        }
    }



}
