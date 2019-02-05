package com.esprit.diasporafinder.models;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class Comments
{
  private String objectId;
  private String ContentComment;
  private java.util.Date created;
  private java.util.Date updated;
  private String PostID;
  private String ownerId;
  public String getObjectId()
  {
    return objectId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

  public String getContentComment()
  {
    return ContentComment;
  }

  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

  public void setContentComment( String ContentComment )
  {
    this.ContentComment = ContentComment;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getPostID()
  {
    return PostID;
  }

  public void setPostID( String PostID )
  {
    this.PostID = PostID;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

                                                    
  public Comments save()
  {
    return Backendless.Data.of( Comments.class ).save( this );
  }

  public Future<Comments> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Comments> future = new Future<Comments>();
      Backendless.Data.of( Comments.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<Comments> callback )
  {
    Backendless.Data.of( Comments.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Comments.class ).remove( this );
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
      Backendless.Data.of( Comments.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Comments.class ).remove( this, callback );
  }

  public static Comments findById( String id )
  {
    return Backendless.Data.of( Comments.class ).findById( id );
  }

  public static Future<Comments> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Comments> future = new Future<Comments>();
      Backendless.Data.of( Comments.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<Comments> callback )
  {
    Backendless.Data.of( Comments.class ).findById( id, callback );
  }

  public static Comments findFirst()
  {
    return Backendless.Data.of( Comments.class ).findFirst();
  }

  public static Future<Comments> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Comments> future = new Future<Comments>();
      Backendless.Data.of( Comments.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<Comments> callback )
  {
    Backendless.Data.of( Comments.class ).findFirst( callback );
  }

  public static Comments findLast()
  {
    return Backendless.Data.of( Comments.class ).findLast();
  }

  public static Future<Comments> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Comments> future = new Future<Comments>();
      Backendless.Data.of( Comments.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<Comments> callback )
  {
    Backendless.Data.of( Comments.class ).findLast( callback );
  }

  public static BackendlessCollection<Comments> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( Comments.class ).find( query );
  }

  public static Future<BackendlessCollection<Comments>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<Comments>> future = new Future<BackendlessCollection<Comments>>();
      Backendless.Data.of( Comments.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Comments>> callback )
  {
    Backendless.Data.of( Comments.class ).find( query, callback );
  }
}