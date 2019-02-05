package com.esprit.diasporafinder.models;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class Historique
{
  private String idReceiver;
  private String ownerId;
  private java.util.Date created;
  private java.util.Date updated;
  private String idDiscussion;
  private String objectId;
  public String getIdReceiver()
  {
    return idReceiver;
  }

  public void setIdReceiver( String idReceiver )
  {
    this.idReceiver = idReceiver;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getIdDiscussion()
  {
    return idDiscussion;
  }

  public void setIdDiscussion( String idDiscussion )
  {
    this.idDiscussion = idDiscussion;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public Historique save()
  {
    return Backendless.Data.of( Historique.class ).save( this );
  }

  public Future<Historique> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Historique> future = new Future<Historique>();
      Backendless.Data.of( Historique.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<Historique> callback )
  {
    Backendless.Data.of( Historique.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Historique.class ).remove( this );
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
      Backendless.Data.of( Historique.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Historique.class ).remove( this, callback );
  }

  public static Historique findById( String id )
  {
    return Backendless.Data.of( Historique.class ).findById( id );
  }

  public static Future<Historique> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Historique> future = new Future<Historique>();
      Backendless.Data.of( Historique.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<Historique> callback )
  {
    Backendless.Data.of( Historique.class ).findById( id, callback );
  }

  public static Historique findFirst()
  {
    return Backendless.Data.of( Historique.class ).findFirst();
  }

  public static Future<Historique> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Historique> future = new Future<Historique>();
      Backendless.Data.of( Historique.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<Historique> callback )
  {
    Backendless.Data.of( Historique.class ).findFirst( callback );
  }

  public static Historique findLast()
  {
    return Backendless.Data.of( Historique.class ).findLast();
  }

  public static Future<Historique> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Historique> future = new Future<Historique>();
      Backendless.Data.of( Historique.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<Historique> callback )
  {
    Backendless.Data.of( Historique.class ).findLast( callback );
  }

  public static BackendlessCollection<Historique> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( Historique.class ).find( query );
  }

  public static Future<BackendlessCollection<Historique>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<Historique>> future = new Future<BackendlessCollection<Historique>>();
      Backendless.Data.of( Historique.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Historique>> callback )
  {
    Backendless.Data.of( Historique.class ).find( query, callback );
  }
}