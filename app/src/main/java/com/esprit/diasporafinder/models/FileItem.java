package com.esprit.diasporafinder.models;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class FileItem
{
  private String ownerId;
  private String objectId;
  private String deviceId;
  private java.util.Date created;
  private String url;
  private java.util.Date updated;
  public String getOwnerId()
  {
    return ownerId;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getDeviceId()
  {
    return deviceId;
  }

  public void setDeviceId( String deviceId )
  {
    this.deviceId = deviceId;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public String getUrl()
  {
    return url;
  }

  public void setUrl( String url )
  {
    this.url = url;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

                                                    
  public FileItem save()
  {
    return Backendless.Data.of( FileItem.class ).save( this );
  }

  public Future<FileItem> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<FileItem> future = new Future<FileItem>();
      Backendless.Data.of( FileItem.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<FileItem> callback )
  {
    Backendless.Data.of( FileItem.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( FileItem.class ).remove( this );
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
      Backendless.Data.of( FileItem.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( FileItem.class ).remove( this, callback );
  }

  public static FileItem findById( String id )
  {
    return Backendless.Data.of( FileItem.class ).findById( id );
  }

  public static Future<FileItem> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<FileItem> future = new Future<FileItem>();
      Backendless.Data.of( FileItem.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<FileItem> callback )
  {
    Backendless.Data.of( FileItem.class ).findById( id, callback );
  }

  public static FileItem findFirst()
  {
    return Backendless.Data.of( FileItem.class ).findFirst();
  }

  public static Future<FileItem> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<FileItem> future = new Future<FileItem>();
      Backendless.Data.of( FileItem.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<FileItem> callback )
  {
    Backendless.Data.of( FileItem.class ).findFirst( callback );
  }

  public static FileItem findLast()
  {
    return Backendless.Data.of( FileItem.class ).findLast();
  }

  public static Future<FileItem> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<FileItem> future = new Future<FileItem>();
      Backendless.Data.of( FileItem.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<FileItem> callback )
  {
    Backendless.Data.of( FileItem.class ).findLast( callback );
  }

  public static BackendlessCollection<FileItem> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( FileItem.class ).find( query );
  }

  public static Future<BackendlessCollection<FileItem>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<FileItem>> future = new Future<BackendlessCollection<FileItem>>();
      Backendless.Data.of( FileItem.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<FileItem>> callback )
  {
    Backendless.Data.of( FileItem.class ).find( query, callback );
  }
}