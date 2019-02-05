package com.esprit.diasporafinder.models;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class Friend
{
  private java.util.Date created;
  private String IdFollow;
  private Boolean state;
  private String ownerId;
  private java.util.Date updated;
  private String objectId;
  public java.util.Date getCreated()
  {
    return created;
  }

  public String getIdFollow()
  {
    return IdFollow;
  }

  public void setIdFollow( String IdFollow )
  {
    this.IdFollow = IdFollow;
  }

  public Boolean getState()
  {
    return state;
  }

  public void setState( Boolean state )
  {
    this.state = state;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getObjectId()
  {
    return objectId;
  }

                                                    
  public Friend save()
  {
    return Backendless.Data.of( Friend.class ).save( this );
  }

  public Future<Friend> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Friend> future = new Future<Friend>();
      Backendless.Data.of( Friend.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<Friend> callback )
  {
    Backendless.Data.of( Friend.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Friend.class ).remove( this );
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
      Backendless.Data.of( Friend.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Friend.class ).remove( this, callback );
  }

  public static Friend findById( String id )
  {
    return Backendless.Data.of( Friend.class ).findById( id );
  }

  public static Future<Friend> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Friend> future = new Future<Friend>();
      Backendless.Data.of( Friend.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<Friend> callback )
  {
    Backendless.Data.of( Friend.class ).findById( id, callback );
  }

  public static Friend findFirst()
  {
    return Backendless.Data.of( Friend.class ).findFirst();
  }

  public static Future<Friend> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Friend> future = new Future<Friend>();
      Backendless.Data.of( Friend.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<Friend> callback )
  {
    Backendless.Data.of( Friend.class ).findFirst( callback );
  }

  public static Friend findLast()
  {
    return Backendless.Data.of( Friend.class ).findLast();
  }

  public static Future<Friend> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Friend> future = new Future<Friend>();
      Backendless.Data.of( Friend.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<Friend> callback )
  {
    Backendless.Data.of( Friend.class ).findLast( callback );
  }

  public static BackendlessCollection<Friend> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( Friend.class ).find( query );
  }

  public static Future<BackendlessCollection<Friend>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<Friend>> future = new Future<BackendlessCollection<Friend>>();
      Backendless.Data.of( Friend.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Friend>> callback )
  {
    Backendless.Data.of( Friend.class ).find( query, callback );
  }
}