package com.esprit.diasporafinder.models;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class Posts
{
  private java.util.Date updated;
  private String Link;
  private String objectId;
  private String Content;
  private String ownerId;
  private java.util.Date created;
  public java.util.Date getUpdated()
  {
    return updated;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public String getLink()
  {
    return Link;
  }

  public void setLink( String Link )
  {
    this.Link = Link;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getContent()
  {
    return Content;
  }

  public void setContent( String Content )
  {
    this.Content = Content;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

  public Posts save()
  {
    return Backendless.Data.of( Posts.class ).save( this );
  }

  public Future<Posts> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Posts> future = new Future<Posts>();
      Backendless.Data.of( Posts.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<Posts> callback )
  {
    Backendless.Data.of( Posts.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Posts.class ).remove( this );
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
      Backendless.Data.of( Posts.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Posts.class ).remove( this, callback );
  }

  public static Posts findById( String id )
  {
    return Backendless.Data.of( Posts.class ).findById( id );
  }

  public static Future<Posts> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Posts> future = new Future<Posts>();
      Backendless.Data.of( Posts.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<Posts> callback )
  {
    Backendless.Data.of( Posts.class ).findById( id, callback );
  }

  public static Posts findFirst()
  {
    return Backendless.Data.of( Posts.class ).findFirst();
  }

  public static Future<Posts> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Posts> future = new Future<Posts>();
      Backendless.Data.of( Posts.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<Posts> callback )
  {
    Backendless.Data.of( Posts.class ).findFirst( callback );
  }

  public static Posts findLast()
  {
    return Backendless.Data.of( Posts.class ).findLast();
  }

  public static Future<Posts> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Posts> future = new Future<Posts>();
      Backendless.Data.of( Posts.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<Posts> callback )
  {
    Backendless.Data.of( Posts.class ).findLast( callback );
  }

  public static BackendlessCollection<Posts> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( Posts.class ).find( query );
  }

  public static Future<BackendlessCollection<Posts>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<Posts>> future = new Future<BackendlessCollection<Posts>>();
      Backendless.Data.of( Posts.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Posts>> callback )
  {
    Backendless.Data.of( Posts.class ).find( query, callback );
  }
}