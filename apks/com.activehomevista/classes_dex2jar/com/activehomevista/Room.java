package com.activehomevista;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Room
  implements Parcelable
{
  public static final Parcelable.Creator<Room> CREATOR = new Parcelable.Creator()
  {
    public Room createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Room(paramAnonymousParcel, null);
    }
    
    public Room[] newArray(int paramAnonymousInt)
    {
      return new Room[paramAnonymousInt];
    }
  };
  int cap;
  public List<Device> devices;
  private int id;
  String name;
  private int nrOfDevices;
  
  public Room() {}
  
  private Room(Parcel paramParcel)
  {
    readFromParcel(paramParcel);
  }
  
  public static Room createFromStream(DataInputStream paramDataInputStream)
  {
    Room localRoom = new Room();
    try
    {
      localRoom.name = paramDataInputStream.readLine();
      localRoom.cap = Integer.reverseBytes(paramDataInputStream.readInt());
      localRoom.devices = new LinkedList();
      localRoom.id = Integer.reverseBytes(paramDataInputStream.readInt());
      localRoom.nrOfDevices = Integer.reverseBytes(paramDataInputStream.readInt());
      return localRoom;
    }
    catch (IOException localIOException) {}
    return null;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean getDeviceAllLightsOffCap()
  {
    return (0x80 & this.cap) > 0;
  }
  
  public boolean getDeviceAllLightsOnCap()
  {
    return (0x40 & this.cap) > 0;
  }
  
  public boolean getDeviceAllUnitsOffCap()
  {
    return (0x20 & this.cap) > 0;
  }
  
  public boolean getDeviceAllUnitsOnCap()
  {
    return (0x100 & this.cap) > 0;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public int getNrOfDevices()
  {
    return this.nrOfDevices;
  }
  
  void readFromParcel(Parcel paramParcel)
  {
    this.name = paramParcel.readString();
    this.cap = paramParcel.readInt();
    this.id = paramParcel.readInt();
    this.nrOfDevices = paramParcel.readInt();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.name);
    paramParcel.writeInt(this.cap);
    paramParcel.writeInt(this.id);
    paramParcel.writeInt(this.nrOfDevices);
  }
}
