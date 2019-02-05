package com.esprit.diasporafinder.activite;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.esprit.diasporafinder.R;
import com.esprit.diasporafinder.fragments.GroupFragment;
import com.esprit.diasporafinder.fragments.FinderFragment;
import com.esprit.diasporafinder.fragments.HistoriqueFragment;
import com.esprit.diasporafinder.fragments.NewsFeedFragment;
import com.esprit.diasporafinder.fragments.ToolsFragment;
import com.esprit.diasporafinder.models.Followers;
import com.esprit.diasporafinder.util.DefaultCallback;
import com.esprit.diasporafinder.util.Defaults;
import com.esprit.diasporafinder.util.DownloadImageTask;

import java.util.List;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    private Fragment fragment;
     private  double latitude  ;
    private double longitude  ;
    private String CountryName;
    private ImageView coverDrawer,profilDrawer;
    private TextView countryDrawer,nameDrawer,followingProfil,followersProfil;
    private   NavigationView navigationView;
    public static String PREFERENCE_FILENAME = "Log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        this.Local();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(HomePageActivity.this, NewPostActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

          navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final TextView imageView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.countryDrawer);

        profilDrawer = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.profilDrawer);
        coverDrawer= (ImageView) navigationView.getHeaderView(0).findViewById(R.id.coverDrawer);
        nameDrawer = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nameDrawer);
       countryDrawer = (TextView) navigationView.getHeaderView(0).findViewById(R.id.countryDrawer);

         followersProfil = (TextView) navigationView.getHeaderView(0).findViewById(R.id.followersProfil);
        followingProfil = (TextView) navigationView.getHeaderView(0).findViewById(R.id.followingProfil);



        Backendless.Messaging.registerDevice(Defaults.GOOGLE_PROJECT_ID, Defaults.DEFAULT_CHANNEL, new AsyncCallback<Void>() {
            @Override
            public void handleResponse(Void response) {
               // Toast.makeText(getApplication(), "Registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                System.out.println(fault.getMessage());
                Toast.makeText(getApplication(), "foutaise", Toast.LENGTH_SHORT).show();
            }
        });
        this.Recharge();
        try {
            new DownloadImageTask(profilDrawer).execute("" + Backendless.UserService.CurrentUser().getProperty("picture"));
        }
        catch (BackendlessException e){
            Toast.makeText(this, "Internet connexion is needed!", Toast.LENGTH_LONG).show();

        }
        try {


            new DownloadImageTask(coverDrawer).execute("" + Backendless.UserService.CurrentUser().getProperty("Cover"));
        }
        catch (BackendlessException e){
            Toast.makeText(this, "Internet connexion is needed!", Toast.LENGTH_LONG).show();

        }
        try {


            nameDrawer.setText("" + Backendless.UserService.CurrentUser().getProperty("name"));




        }
        catch (BackendlessException e){
            Toast.makeText(this, "Internet connexion is needed!", Toast.LENGTH_LONG).show();

        }
        try {


            countryDrawer.setText("" + Backendless.UserService.CurrentUser().getProperty("Nationality"));
        }
        catch (BackendlessException e){
            Toast.makeText(this, "Internet connexion is needed!", Toast.LENGTH_LONG).show();

        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        NewsFeedFragment startFragment = new NewsFeedFragment();
        transaction.add(R.id.linearLayoutBase,startFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(this, HomePageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
       if (id == R.id.nav_actuality_feed) {
            fragment = new NewsFeedFragment();
           new DownloadImageTask(profilDrawer).execute("" + Backendless.UserService.CurrentUser().getProperty("picture"));
           new DownloadImageTask(coverDrawer).execute("" + Backendless.UserService.CurrentUser().getProperty("Cover"));
           nameDrawer.setText("" + Backendless.UserService.CurrentUser().getProperty("name"));
           countryDrawer.setText("" + Backendless.UserService.CurrentUser().getProperty("Nationality"));
           Recharge();
           FragmentTransaction tras = getSupportFragmentManager().beginTransaction();
           tras.replace(R.id.linearLayoutBase,fragment);
           tras.addToBackStack("actualite");
           tras.commit();

           DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
           drawer.closeDrawer(GravityCompat.START);

        }else    if (id == R.id.nav_friends) {
                fragment = new FinderFragment();
           new DownloadImageTask(profilDrawer).execute("" + Backendless.UserService.CurrentUser().getProperty("picture"));
           new DownloadImageTask(coverDrawer).execute("" + Backendless.UserService.CurrentUser().getProperty("Cover"));
           nameDrawer.setText("" + Backendless.UserService.CurrentUser().getProperty("name"));
           countryDrawer.setText("" + Backendless.UserService.CurrentUser().getProperty("Nationality"));
           Recharge();
           FragmentTransaction tras = getSupportFragmentManager().beginTransaction();
           tras.replace(R.id.linearLayoutBase,fragment);
           tras.addToBackStack("friend");
           tras.commit();

           DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
           drawer.closeDrawer(GravityCompat.START);
          } else if (id == R.id.nav_actuality_feed) {
            fragment = new NewsFeedFragment();
           new DownloadImageTask(profilDrawer).execute("" + Backendless.UserService.CurrentUser().getProperty("picture"));
           new DownloadImageTask(coverDrawer).execute("" + Backendless.UserService.CurrentUser().getProperty("Cover"));
           nameDrawer.setText("" + Backendless.UserService.CurrentUser().getProperty("name"));
           countryDrawer.setText("" + Backendless.UserService.CurrentUser().getProperty("Nationality"));
           Recharge();
           FragmentTransaction tras = getSupportFragmentManager().beginTransaction();
           tras.replace(R.id.linearLayoutBase,fragment);
           tras.addToBackStack("feed");
           tras.commit();

           DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
           drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.nav_events) {
            fragment = new GroupFragment();
           new DownloadImageTask(profilDrawer).execute("" + Backendless.UserService.CurrentUser().getProperty("picture"));
           new DownloadImageTask(coverDrawer).execute("" + Backendless.UserService.CurrentUser().getProperty("Cover"));
           nameDrawer.setText("" + Backendless.UserService.CurrentUser().getProperty("name"));
           countryDrawer.setText("" + Backendless.UserService.CurrentUser().getProperty("Nationality"));
           Recharge();
           FragmentTransaction tras = getSupportFragmentManager().beginTransaction();
           tras.replace(R.id.linearLayoutBase,fragment);
           tras.addToBackStack("group");
           tras.commit();

           DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
           drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.nav_tools) {
            fragment = new ToolsFragment();
           new DownloadImageTask(profilDrawer).execute("" + Backendless.UserService.CurrentUser().getProperty("picture"));
           new DownloadImageTask(coverDrawer).execute("" + Backendless.UserService.CurrentUser().getProperty("Cover"));
           nameDrawer.setText("" + Backendless.UserService.CurrentUser().getProperty("name"));
           countryDrawer.setText("" + Backendless.UserService.CurrentUser().getProperty("Nationality"));
           Recharge();
           FragmentTransaction tras = getSupportFragmentManager().beginTransaction();
           tras.replace(R.id.linearLayoutBase,fragment);
           tras.addToBackStack("tools");
           tras.commit();

           DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
           drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.nav_messages) {
            fragment = new HistoriqueFragment();
           new DownloadImageTask(profilDrawer).execute("" + Backendless.UserService.CurrentUser().getProperty("picture"));
           new DownloadImageTask(coverDrawer).execute("" + Backendless.UserService.CurrentUser().getProperty("Cover"));
           nameDrawer.setText("" + Backendless.UserService.CurrentUser().getProperty("name"));
           countryDrawer.setText("" + Backendless.UserService.CurrentUser().getProperty("Nationality"));
           Recharge();
           FragmentTransaction tras = getSupportFragmentManager().beginTransaction();
           tras.replace(R.id.linearLayoutBase,fragment);
           tras.addToBackStack("messages");
           tras.commit();

           DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
           drawer.closeDrawer(GravityCompat.START);
        }else if (id == R.id.nav_logout) {

            Backendless.UserService.logout( new DefaultCallback<Void>( this )
           {
               @Override
               public void handleResponse( Void response )
               {
                   super.handleResponse( response );
                   try
                   {
                       Backendless.Messaging.unregisterDevice();
                   }
                   catch( BackendlessException exception )
                   {
                       // either device is not registered or an error occurred during de-registration
                   }
                   SharedPreferences reportingPref2 =  getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
                   SharedPreferences.Editor prefEditor2 = reportingPref2.edit();
                   prefEditor2.putString("login",null);
                   prefEditor2.putString("password",null);
                   prefEditor2.commit();
                   startActivity( new Intent( HomePageActivity.this, LoginActivity.class ) );
                   finish();
               }

               @Override
               public void handleFault( BackendlessFault fault )
               {
                   if( fault.getCode().equals( "3023" ) ){

                       Toast.makeText( getApplication(), "Unable to logout!", Toast.LENGTH_LONG ).show();
                       // : not logged in (session expired, etc.)
                       handleResponse(null);

                   }
                   else{
                       super.handleFault( fault );

                   }
               }
           } );

       }

        return true;

    }


    public void Recharge(){
        AsyncCallback<BackendlessCollection<Followers>> callback2 = new AsyncCallback<BackendlessCollection<Followers>>() {
            @Override
            public void handleResponse(BackendlessCollection<Followers> response) {
                List<Followers> firstPage = response.getCurrentPage();
                int a = 0;
                int b = 0;
                for (int j = 0; j < firstPage.size(); j++) {
                    if (firstPage.get(j).getIdFollowing().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId())) {
                        a++;
                    }
                    if (firstPage.get(j).getOwnerId().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId())) {
                        b++;
                    }

                }
                if(a >= 1 ){
                    a--;
                }

                if(b >= 1 ){
                    b--;
                }
                followingProfil.setText(b+ " Following");
                followersProfil.setText(a+ " Followers");



            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        };

        Backendless.Persistence.of(Followers.class).find(callback2);







    }



    public void Local(){
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                String deviceId = Build.SERIAL;
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                Backendless.UserService.CurrentUser().setProperty("phoneID", deviceId);
                 Backendless.UserService.CurrentUser().setProperty("Latitude", latitude);
                Backendless.UserService.CurrentUser().setProperty("Longitude", longitude);

                Backendless.UserService.update(Backendless.UserService.CurrentUser(), new AsyncCallback<BackendlessUser>() {
                    public void handleResponse(BackendlessUser user) {
                        Backendless.UserService.setCurrentUser(user);

                    }

                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText( getApplication(), "Internet connexion is needed!", Toast.LENGTH_LONG ).show();

                    }
                });
                if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.removeUpdates(this);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

// Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, locationListener);


    }

}
