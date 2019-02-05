package com.esprit.diasporafinder.util;

import android.content.Context;
import android.widget.Toast;

import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;

public class DefaultCallback2<T> extends BackendlessCallback<T>
{
  private Context context;

  public DefaultCallback2(Context context)
  {
    this.context = context;
   }

  public DefaultCallback2(Context context, String message)
  {
    this.context = context;
   }



  @Override
  public void handleResponse( T response )
  {

  }

  @Override
  public void handleFault( BackendlessFault fault )
  {
     System.out.println(fault.getMessage());
    if(fault.getMessage().equalsIgnoreCase("Invalid login or password")){
      Toast.makeText( context, fault.getMessage(), Toast.LENGTH_LONG ).show();

    }
    else {
      Toast.makeText( context, "Internet connexion is needed!", Toast.LENGTH_LONG ).show();

    }
  }
}