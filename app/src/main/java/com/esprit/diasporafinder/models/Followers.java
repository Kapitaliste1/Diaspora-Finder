package com.esprit.diasporafinder.models;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class Followers
{
  private java.util.Date updated;
  private String ownerId;
  private java.util.Date created;
  private String objectId;
  private String idFollowing;
  private Boolean state;
  public java.util.Date getUpdated()
  {
    return updated;
  }

  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getIdFollowing()
  {
    return idFollowing;
  }

  public void setIdFollowing( String idFollowing )
  {
    this.idFollowing = idFollowing;
  }

  public Boolean getState()
  {
    return state;
  }

  public void setState( Boolean state )
  {
    this.state = state;
  }

                                                    
  public Followers save()
  {
    return Backendless.Data.of( Followers.class ).save( this );
  }

  public Future<Followers> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Followers> future = new Future<Followers>();
      Backendless.Data.of( Followers.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<Followers> callback )
  {
    Backendless.Data.of( Followers.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Followers.class ).remove( this );
  }

  public Future<Long> removeAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Long> future = new Future<Long>();
      Backendless.Data.of( Followers.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Followers.class ).remove( this, callback );
  }

  public static Followers findById( String id )
  {
    return Backendless.Data.of( Followers.class ).findById( id );
  }

  public static Future<Followers> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Followers> future = new Future<Followers>();
      Backendless.Data.of( Followers.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<Followers> callback )
  {
    Backendless.Data.of( Followers.class ).findById( id, callback );
  }

  public static Followers findFirst()
  {
    return Backendless.Data.of( Followers.class ).findFirst();
  }

  public static Future<Followers> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Followers> future = new Future<Followers>();
      Backendless.Data.of( Followers.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<Followers> callback )
  {
    Backendless.Data.of( Followers.class ).findFirst( callback );
  }

  public static Followers findLast()
  {
    return Backendless.Data.of( Followers.class ).findLast();
  }

  public static Future<Followers> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Followers> future = new Future<Followers>();
      Backendless.Data.of( Followers.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<Followers> callback )
  {
    Backendless.Data.of( Followers.class ).findLast( callback );
  }

  public static BackendlessCollection<Followers> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( Followers.class ).find( query );
  }

  public static Future<BackendlessCollection<Followers>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<Followers>> future = new Future<BackendlessCollection<Followers>>();
      Backendless.Data.of( Followers.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Followers>> callback )
  {
    Backendless.Data.of( Followers.class ).find( query, callback );
  }
}