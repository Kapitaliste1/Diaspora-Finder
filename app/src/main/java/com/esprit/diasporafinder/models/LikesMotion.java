package com.esprit.diasporafinder.models;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class LikesMotion
{
  private Boolean state;
  private String ownerId;
  private String IdPost;
  private java.util.Date updated;
  private String objectId;
  private java.util.Date created;
  public Boolean getState()
  {
    return state;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public void setState( Boolean state )
  {
    this.state = state;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getIdPost()
  {
    return IdPost;
  }

  public void setIdPost( String IdPost )
  {
    this.IdPost = IdPost;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

                                                    
  public LikesMotion save()
  {
    return Backendless.Data.of( LikesMotion.class ).save( this );
  }

  public Future<LikesMotion> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<LikesMotion> future = new Future<LikesMotion>();
      Backendless.Data.of( LikesMotion.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<LikesMotion> callback )
  {
    Backendless.Data.of( LikesMotion.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( LikesMotion.class ).remove( this );
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
      Backendless.Data.of( LikesMotion.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( LikesMotion.class ).remove( this, callback );
  }

  public static LikesMotion findById( String id )
  {
    return Backendless.Data.of( LikesMotion.class ).findById( id );
  }

  public static Future<LikesMotion> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<LikesMotion> future = new Future<LikesMotion>();
      Backendless.Data.of( LikesMotion.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<LikesMotion> callback )
  {
    Backendless.Data.of( LikesMotion.class ).findById( id, callback );
  }

  public static LikesMotion findFirst()
  {
    return Backendless.Data.of( LikesMotion.class ).findFirst();
  }

  public static Future<LikesMotion> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<LikesMotion> future = new Future<LikesMotion>();
      Backendless.Data.of( LikesMotion.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<LikesMotion> callback )
  {
    Backendless.Data.of( LikesMotion.class ).findFirst( callback );
  }

  public static LikesMotion findLast()
  {
    return Backendless.Data.of( LikesMotion.class ).findLast();
  }

  public static Future<LikesMotion> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<LikesMotion> future = new Future<LikesMotion>();
      Backendless.Data.of( LikesMotion.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<LikesMotion> callback )
  {
    Backendless.Data.of( LikesMotion.class ).findLast( callback );
  }

  public static BackendlessCollection<LikesMotion> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( LikesMotion.class ).find( query );
  }

  public static Future<BackendlessCollection<LikesMotion>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<LikesMotion>> future = new Future<BackendlessCollection<LikesMotion>>();
      Backendless.Data.of( LikesMotion.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<LikesMotion>> callback )
  {
    Backendless.Data.of( LikesMotion.class ).find( query, callback );
  }
}