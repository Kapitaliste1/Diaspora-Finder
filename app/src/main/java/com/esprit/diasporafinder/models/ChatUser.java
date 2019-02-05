package com.esprit.diasporafinder.models;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class ChatUser
{
  private String deviceId;
  private String objectId;
  private java.util.Date updated;
  private String nickname;
  private java.util.Date created;
  private String ownerId;
  public String getDeviceId()
  {
    return deviceId;
  }

  public void setDeviceId( String deviceId )
  {
    this.deviceId = deviceId;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getNickname()
  {
    return nickname;
  }

  public void setNickname( String nickname )
  {
    this.nickname = nickname;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

                                                    
  public ChatUser save()
  {
    return Backendless.Data.of( ChatUser.class ).save( this );
  }

  public Future<ChatUser> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ChatUser> future = new Future<ChatUser>();
      Backendless.Data.of( ChatUser.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<ChatUser> callback )
  {
    Backendless.Data.of( ChatUser.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( ChatUser.class ).remove( this );
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
      Backendless.Data.of( ChatUser.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( ChatUser.class ).remove( this, callback );
  }

  public static ChatUser findById( String id )
  {
    return Backendless.Data.of( ChatUser.class ).findById( id );
  }

  public static Future<ChatUser> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ChatUser> future = new Future<ChatUser>();
      Backendless.Data.of( ChatUser.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<ChatUser> callback )
  {
    Backendless.Data.of( ChatUser.class ).findById( id, callback );
  }

  public static ChatUser findFirst()
  {
    return Backendless.Data.of( ChatUser.class ).findFirst();
  }

  public static Future<ChatUser> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ChatUser> future = new Future<ChatUser>();
      Backendless.Data.of( ChatUser.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<ChatUser> callback )
  {
    Backendless.Data.of( ChatUser.class ).findFirst( callback );
  }

  public static ChatUser findLast()
  {
    return Backendless.Data.of( ChatUser.class ).findLast();
  }

  public static Future<ChatUser> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ChatUser> future = new Future<ChatUser>();
      Backendless.Data.of( ChatUser.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<ChatUser> callback )
  {
    Backendless.Data.of( ChatUser.class ).findLast( callback );
  }

  public static BackendlessCollection<ChatUser> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( ChatUser.class ).find( query );
  }

  public static Future<BackendlessCollection<ChatUser>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<ChatUser>> future = new Future<BackendlessCollection<ChatUser>>();
      Backendless.Data.of( ChatUser.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<ChatUser>> callback )
  {
    Backendless.Data.of( ChatUser.class ).find( query, callback );
  }
}