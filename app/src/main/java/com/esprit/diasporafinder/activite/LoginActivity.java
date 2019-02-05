package com.esprit.diasporafinder.activite;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.esprit.diasporafinder.R;
import com.esprit.diasporafinder.util.DefaultCallback;
import com.esprit.diasporafinder.util.Defaults;

public class LoginActivity extends Activity
{
    private TextView  restoreLink;
    private EditText identityField, passwordField;
    private Button loginButton,registerLink;
    public static String PREFERENCE_FILENAME = "Log";


    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerLink = (Button) findViewById( R.id.registerLink );
        restoreLink = (TextView) findViewById( R.id.restoreLink );
        identityField = (EditText) findViewById( R.id.identityField );
        passwordField = (EditText) findViewById( R.id.passwordField );
        loginButton = (Button) findViewById( R.id.loginButton );

        SharedPreferences reportingPref =  getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);

        if(reportingPref.getString("login", "") != null && reportingPref.getString("password", "") != null){

            identityField.setText(reportingPref.getString("login", ""));
            passwordField.setText(reportingPref.getString("password", ""));

        }

        initUI();

        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(this, Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION);

        Backendless.UserService.isValidLogin( new DefaultCallback<Boolean>( this )
        {
            @Override
            public void handleResponse( Boolean isValidLogin )
            {
                if( isValidLogin && Backendless.UserService.CurrentUser() == null )
                {
                    String currentUserId = Backendless.UserService.loggedInUser();

                    if( !currentUserId.equals( "" ) )
                    {
                        Backendless.UserService.findById( currentUserId, new DefaultCallback<BackendlessUser>( LoginActivity.this, "Logging in..." )
                        {
                            @Override
                            public void handleResponse( BackendlessUser currentUser )
                            {
                                super.handleResponse(currentUser);
                                Backendless.UserService.setCurrentUser( currentUser );
                                startActivity( new Intent( getBaseContext(), HomePageActivity.class ) );
                                finish();
                            }
                        } );
                    }
                }

                super.handleResponse( isValidLogin );
            }
        });
    }

    private void initUI()
    {


        String tempString = getResources().getString( R.string.register_text );
        SpannableString underlinedContent = new SpannableString( tempString );
        underlinedContent.setSpan(new UnderlineSpan(), 0, tempString.length(), 0);
         tempString = getResources().getString( R.string.restore_link );
        underlinedContent = new SpannableString( tempString );
        underlinedContent.setSpan( new UnderlineSpan(), 0, tempString.length(), 0 );
        restoreLink.setText( underlinedContent );

        loginButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                onLoginButtonClicked();
            }
        } );

        registerLink.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                onRegisterLinkClicked();
            }
        } );

        restoreLink.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                onRestoreLinkClicked();
            }
        } );
    }

    public void onLoginButtonClicked()
    {
        String identity = identityField.getText().toString();
        String password = passwordField.getText().toString();

            if ( identity.isEmpty() )
            {
                Toast.makeText(this, "Email field can not be empty! ", Toast.LENGTH_SHORT).show();

                return;
            }else  if ( password.isEmpty() )
                {
                    Toast.makeText(this, "Password field can not be empty! ", Toast.LENGTH_SHORT).show();

                    return;
                }
        Backendless.UserService.login( identity, password, new DefaultCallback<BackendlessUser>( LoginActivity.this )
        {
            public void handleResponse( BackendlessUser backendlessUser )
            {
                super.handleResponse( backendlessUser );
                SharedPreferences reportingPref2 =  getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
                SharedPreferences.Editor prefEditor2 = reportingPref2.edit();
                prefEditor2.putString("login", backendlessUser.getEmail());
                prefEditor2.putString("password", backendlessUser.getPassword());
                prefEditor2.commit();
                startActivity( new Intent( LoginActivity.this, HomePageActivity.class ) );
                finish();
            }
        }, true );
    }

    public void onRegisterLinkClicked()
    {
        startActivity( new Intent( this, RegisterActivity.class ) );
     }

    public void onRestoreLinkClicked()
    {
        startActivity( new Intent( this, RestorePasswordActivity.class ) );
     }
}