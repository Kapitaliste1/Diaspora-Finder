package com.esprit.diasporafinder.activite;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.esprit.diasporafinder.R;
import com.esprit.diasporafinder.models.DiasporaFinderUser;
import com.esprit.diasporafinder.models.Followers;
import com.esprit.diasporafinder.util.DefaultCallback;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends Activity {
    private Spinner NationalityField;
    private Spinner TypeField;


    private EditText emailField;
    private EditText nameField;
    private EditText passwordField;

    private Button registerButton;

    private String Nationality;
    private String email;
    private String gender;
    private String typeAccout;

    private String name;
    private String password;

    private DiasporaFinderUser user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUI();
    }

    private void initUI() {
        NationalityField = (Spinner) findViewById(R.id.NationalityField);
        //Création d'une liste d'élément à mettre dans le Spinner(pour l'exemple)
        List exempleList = new ArrayList();
        //A
        exempleList.add("Afghanistan");
        exempleList.add("Albania");
        exempleList.add("Algeria");
        exempleList.add("American Samoa");
        exempleList.add("Andorra");
        exempleList.add("Angola");
        exempleList.add("Anguilla");
        exempleList.add("Antigua and Barbuda");
        exempleList.add("Argentina");
        exempleList.add("Armenia");
        exempleList.add("Aruba");
        exempleList.add("Australia");
        exempleList.add("Austria");
        exempleList.add("Azerbaijan");
        //B
        exempleList.add("Bahamas");
        exempleList.add("Bahrain");
        exempleList.add("Bangladesh");
        exempleList.add("Barbados");
        exempleList.add("Belarus");
        exempleList.add("Belgium");
        exempleList.add("Belize");
        exempleList.add("Benin");
        exempleList.add("Bermuda");
        exempleList.add("Bhutan");
        exempleList.add("Bolivia");
        exempleList.add("Bosnia and Herzegovina");
        exempleList.add("Botswana");
        exempleList.add("Brazil");
        exempleList.add("Brunei Darussalam");
        exempleList.add("Bulgaria");
        exempleList.add("Burkina Faso");
        exempleList.add("Burundi");
        //C
        exempleList.add("Cambodia");
        exempleList.add("Cameroon");
        exempleList.add("Canada");
        exempleList.add("Cape Verde");
        exempleList.add("Cayman Islands");
        exempleList.add("Central African Republic");
        exempleList.add("Chad");
        exempleList.add("Chile");
        exempleList.add("China");
        exempleList.add("Christmas Island");
        exempleList.add("Cocos (Keeling) Islands");
        exempleList.add("Colombia");
        exempleList.add("Comoros");
        exempleList.add("Democratic Republic of the Congo (Kinshasa)");
        exempleList.add("Congo, Republic of (Brazzaville)");
        exempleList.add("Costa Rica");
        exempleList.add("Ivory Coast");
        exempleList.add("Croatia");
        exempleList.add("Cuba");
        exempleList.add("Cyprus");
        exempleList.add("Czech Republic");
        //D
        exempleList.add("Denmark");
        exempleList.add("Djibouti");
        exempleList.add("Dominica");
        exempleList.add("Dominican Republic");
        //E
        exempleList.add("East Timor");
        exempleList.add("Ecuador");
        exempleList.add("Egypt");
        exempleList.add("El Salvador");
        exempleList.add("Equatorial Guinea");
        exempleList.add("Eritrea");
        exempleList.add("Estonia");
        exempleList.add("Ethiopia");
        //F
        exempleList.add("Falkland Islands");
        exempleList.add("Faroe Islands");
        exempleList.add("Fiji");
        exempleList.add("Finland");
        exempleList.add("France");
        exempleList.add("French Guiana");
        exempleList.add("French Polynesia");
        exempleList.add("French Southern Territories");
        //G
        exempleList.add("Gabon");
        exempleList.add("Gambia");
        exempleList.add("Georgia");
        exempleList.add("Germany");
        exempleList.add("Ghana");
        exempleList.add("Gibraltar");
        exempleList.add("Great Britain");
        exempleList.add("Greece");
        exempleList.add("Greenland");
        exempleList.add("Grenada");
        exempleList.add("Guadeloupe");
        exempleList.add("Guam");
        exempleList.add("Guatemala");
        exempleList.add("Guinea");
        exempleList.add("Guinea-Bissau");
        exempleList.add("Guyana");
        //H
        exempleList.add("Haiti");
        exempleList.add("Holy See");
        exempleList.add("Honduras");
        exempleList.add("Hong Kong");
        exempleList.add("Hungary");
        //I
        exempleList.add("Iceland");
        exempleList.add("India");
        exempleList.add("Indonesia");
        exempleList.add("Iran");
        exempleList.add("Iraq");
        exempleList.add("Ireland");
        exempleList.add("Israel");
        exempleList.add("Italy");
        //J
        exempleList.add("Jamaica");
        exempleList.add("Japan");
        exempleList.add("Jordan");
        //K
        exempleList.add("Kazakhstan");
        exempleList.add("Kenya");
        exempleList.add("Kiribati");
        exempleList.add("Korea, Democratic People's Rep. (North Korea)");
        exempleList.add("Korea, Republic of (South Korea)");
        exempleList.add("Kosovo");
        exempleList.add("Kuwait");
        exempleList.add("Kyrgyzstan");
        //L
        exempleList.add("Lebanon");
        exempleList.add("Lesotho");
        exempleList.add("Liberia");
        exempleList.add("Libya");
        exempleList.add("Lithuania");
        exempleList.add("Luxembourg");
        //M
        exempleList.add("Macedonia, Rep. of");
        exempleList.add("Madagascar");
        exempleList.add("Malawi");
        exempleList.add("Malaysia");
        exempleList.add("Maldives");
        exempleList.add("Mali");
        exempleList.add("Malta");
        exempleList.add("Martinique");
        exempleList.add("Mauritania");
        exempleList.add("Mauritius");
        exempleList.add("Mexico");
        exempleList.add("Moldova, Republic of");
        exempleList.add("Mongolia");
        exempleList.add("Morocco");
        exempleList.add("Mozambique");
        //N
        exempleList.add("Namibia");
        exempleList.add("Nepal");
        exempleList.add("Netherlands");
        exempleList.add("New Zealand");
        exempleList.add("Nicaragua");
        exempleList.add("Niger");
        exempleList.add("Nigeria");
        exempleList.add("Norway");
        //O
        exempleList.add("Oman");
        //P
        exempleList.add("Pakistan");
        exempleList.add("Palestinian territories");
        exempleList.add("Panama");
        exempleList.add("Paraguay");
        exempleList.add("Peru");
        exempleList.add("Philippines");
        exempleList.add("Poland");
        exempleList.add("Portugal");
        exempleList.add("Puerto Rico");
        //Q
        exempleList.add("Qatar");
        //R
        exempleList.add("Romania");
        exempleList.add("Rwanda");
        //S
        exempleList.add("Samoa");
        exempleList.add("San Marino");
        exempleList.add("Sao Tome and Principe");
        exempleList.add("Saudi Arabia");
        exempleList.add("Senegal");
        exempleList.add("Serbia");
        exempleList.add("Seychelles");
        exempleList.add("Sierra Leone");
        exempleList.add("Singapore");
        exempleList.add("Slovakia");
        exempleList.add("Slovenia");
        exempleList.add("Somalia");
        exempleList.add("South Africa");
        exempleList.add("South Sudan");
        exempleList.add("Spain");
        exempleList.add("Sudan");
        exempleList.add("Swaziland");
        exempleList.add("Sweden");
        exempleList.add("Switzerland");
        exempleList.add("Syria");
        //T
        exempleList.add("Taiwan");
        exempleList.add("Tajikistan");
        exempleList.add("Tanzania");
        exempleList.add("Thailand");
        exempleList.add("Togo");
        exempleList.add("Tunisia");
        exempleList.add("Turkey");
        //U
        exempleList.add("Uganda");
        exempleList.add("Ukraine");
        exempleList.add("United Arab Emirates");
        exempleList.add("United Kingdom");
        exempleList.add("United States");
        exempleList.add("Uruguay");
        exempleList.add("Uzbekistan");
        //V
        exempleList.add("Venezuela");
        exempleList.add("Vietnam");
        //Y
        exempleList.add("Yemen");
        //Z
        exempleList.add("Zambia");
        exempleList.add("Zimbabwe");


 /*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
                un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
 Avec la liste des elements (exemple) */
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                exempleList
        );


               /* On definit une présentation du spinner quand il est déroulé         (android.R.layout.simple_spinner_dropdown_item) */
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        NationalityField.setAdapter(adapter);

        emailField = (EditText) findViewById(R.id.emailField);


        TypeField = (Spinner) findViewById(R.id.TypeField);


        List typeFieldList = new ArrayList();
        typeFieldList.add("Groupe");
        typeFieldList.add("Individual");
        ArrayAdapter adapter3 = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                typeFieldList
        );
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TypeField.setAdapter(adapter3);


        nameField = (EditText) findViewById(R.id.nameField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        registerButton = (Button) findViewById(R.id.registerButton);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterButtonClicked();
            }
        });
    }

    private void onRegisterButtonClicked() {
        String NationalityText = NationalityField.getSelectedItem().toString().trim();
        String emailText = emailField.getText().toString().trim();
        String nameText = nameField.getText().toString().trim();
        String passwordText = passwordField.getText().toString().trim();
        String type = TypeField.getSelectedItem().toString().trim();
        if (emailText.isEmpty()) {
            Toast.makeText(this, "Email field can not be empty! ", Toast.LENGTH_SHORT).show();

            return;
        }
        if (nameText.isEmpty()) {
            Toast.makeText(this, "Name field can not be empty! ", Toast.LENGTH_SHORT).show();

            return;
        }

        if (passwordText.isEmpty()) {
            Toast.makeText(this, "Password field can not be empty! ", Toast.LENGTH_SHORT).show();

            return;
        }

        if (!NationalityText.isEmpty()) {

            Nationality = NationalityText;
        }

        if (!emailText.isEmpty()) {
            email = emailText;
        }


        if (!type.isEmpty()) {
            typeAccout = type;
        }

        if (!nameText.isEmpty()) {
            name = nameText;
        }

        if (!passwordText.isEmpty()) {
            password = passwordText;
        }

        user = new DiasporaFinderUser();

        if (Nationality != null) {
            user.setNationality(Nationality);
        }

        if (email != null) {
            user.setEmail(email);
        }




        if (typeAccout != null) {
            user.setType(typeAccout);
        }

        if (name != null) {
            user.setName(name);
        }

        if (password != null) {
            user.setPassword(password);
        }
        String deviceId = Build.SERIAL;
        user.setPhoneID(deviceId);


        Backendless.UserService.register(user, new DefaultCallback<BackendlessUser>(RegisterActivity.this) {
            @Override
            public void handleResponse(final BackendlessUser response) {
                Local(response);

                Followers md = new Followers();

                md.setState(true);
                md.setOwnerId(response.getObjectId());
                md.setIdFollowing(response.getObjectId());
                Backendless.Persistence.save(md, new AsyncCallback<Followers>() {
                    public void handleResponse(Followers dsgf) {
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }

                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getBaseContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


    }

    public void Local(final BackendlessUser us) {
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                us.setProperty("Latitude", location.getLatitude());
                us.setProperty("Longitude", location.getLongitude());

                Backendless.UserService.update(us, new AsyncCallback<BackendlessUser>() {
                    public void handleResponse(BackendlessUser user) {



                    }

                    public void handleFault(BackendlessFault fault) {
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
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


    }

    private void showToast( String msg )
    {
        Toast.makeText( this, "Internet Connexion is needed !", Toast.LENGTH_SHORT ).show();
    }
}