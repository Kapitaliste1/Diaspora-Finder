package com.esprit.diasporafinder.models;

import com.backendless.BackendlessUser;

public class DiasporaFinderUser extends BackendlessUser
{
  public String getEmail()
  {
    return super.getEmail();
  }

  public void setEmail( String email )
  {
    super.setEmail( email );
  }

  public String getPassword()
  {
    return super.getPassword();
  }

  public String getCover()
  {
    return (String) super.getProperty( "Cover" );
  }

  public void setCover( String Cover )
  {
    super.setProperty( "Cover", Cover );
  }

  public Double getLatitude()
  {
    return (Double) super.getProperty( "Latitude" );
  }

  public void setLatitude( Double Latitude )
  {
    super.setProperty( "Latitude", Latitude );
  }

  public Double getLongitude()
  {
    return (Double) super.getProperty( "Longitude" );
  }

  public void setLongitude( Double Longitude )
  {
    super.setProperty( "Longitude", Longitude );
  }

  public String getNationality()
  {
    return (String) super.getProperty( "Nationality" );
  }

  public void setNationality( String Nationality )
  {
    super.setProperty( "Nationality", Nationality );
  }

  public String getName()
  {
    return (String) super.getProperty( "name" );
  }

  public void setName( String name )
  {
    super.setProperty( "name", name );
  }

  public String getPhoneID()
  {
    return (String) super.getProperty( "phoneID" );
  }

  public void setPhoneID( String phoneID )
  {
    super.setProperty( "phoneID", phoneID );
  }

  public String getPicture()
  {
    return (String) super.getProperty( "picture" );
  }

  public void setPicture( String picture )
  {
    super.setProperty( "picture", picture );
  }

  public String getType()
  {
    return (String) super.getProperty( "type" );
  }

  public void setType( String type )
  {
    super.setProperty( "type", type );
  }
}