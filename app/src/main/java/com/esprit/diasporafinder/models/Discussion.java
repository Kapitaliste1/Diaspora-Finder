package com.esprit.diasporafinder.models;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class Discussion
{
  private String objectId;
  private java.util.Date updated;
  private java.util.Date created;
  private String idReceiver;
  private String ownerId;
  private String content;
  private String idDicussion;
  public String getObjectId()
  {
    return objectId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

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

  public String getContent()
  {
    return content;
  }

  public void setContent( String content )
  {
    this.content = content;
  }

  public String getIdDicussion()
  {
    return idDicussion;
  }

  public void setIdDicussion( String idDicussion )
  {
    this.idDicussion = idDicussion;
  }

                                                    
  public Discussion save()
  {
    return Backendless.Data.of( Discussion.class ).save( this );
  }

  public Future<Discussion> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Discussion> future = new Future<Discussion>();
      Backendless.Data.of( Discussion.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<Discussion> callback )
  {
    Backendless.Data.of( Discussion.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Discussion.class ).remove( this );
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
      Backendless.Data.of( Discussion.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Discussion.class ).remove( this, callback );
  }

  public static Discussion findById( String id )
  {
    return Backendless.Data.of( Discussion.class ).findById( id );
  }

  public static Future<Discussion> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Discussion> future = new Future<Discussion>();
      Backendless.Data.of( Discussion.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<Discussion> callback )
  {
    Backendless.Data.of( Discussion.class ).findById( id, callback );
  }

  public static Discussion findFirst()
  {
    return Backendless.Data.of( Discussion.class ).findFirst();
  }

  public static Future<Discussion> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Discussion> future = new Future<Discussion>();
      Backendless.Data.of( Discussion.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<Discussion> callback )
  {
    Backendless.Data.of( Discussion.class ).findFirst( callback );
  }

  public static Discussion findLast()
  {
    return Backendless.Data.of( Discussion.class ).findLast();
  }

  public static Future<Discussion> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Discussion> future = new Future<Discussion>();
      Backendless.Data.of( Discussion.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<Discussion> callback )
  {
    Backendless.Data.of( Discussion.class ).findLast( callback );
  }

  public static BackendlessCollection<Discussion> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( Discussion.class ).find( query );
  }

  public static Future<BackendlessCollection<Discussion>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<Discussion>> future = new Future<BackendlessCollection<Discussion>>();
      Backendless.Data.of( Discussion.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Discussion>> callback )
  {
    Backendless.Data.of( Discussion.class ).find( query, callback );
  }
}