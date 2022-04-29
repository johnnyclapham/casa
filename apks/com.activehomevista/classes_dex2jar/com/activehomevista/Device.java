package com.activehomevista;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Parcel;
import java.io.DataInputStream;
import java.io.IOException;

@SuppressLint({"ParcelCreator"})
public class Device
  extends Room
{
  private byte deviceCode;
  private byte deviceType;
  private byte dimDuration;
  private char houseCode;
  private Bitmap offBmp;
  private Bitmap onBmp;
  
  private Device() {}
  
  public static Device createFromStream(DataInputStream paramDataInputStream)
    throws OutOfMemoryError
  {
    Device localDevice = new Device();
    try
    {
      localDevice.name = paramDataInputStream.readLine();
      String str = paramDataInputStream.readLine();
      if (str != null)
      {
        localDevice.houseCode = str.charAt(0);
        localDevice.deviceCode = paramDataInputStream.readByte();
        localDevice.dimDuration = paramDataInputStream.readByte();
        localDevice.deviceType = paramDataInputStream.readByte();
        localDevice.cap = Integer.reverseBytes(paramDataInputStream.readInt());
        localDevice.onBmp = localDevice.readImage(paramDataInputStream);
        localDevice.offBmp = localDevice.readImage(paramDataInputStream);
        if (localDevice.onBmp == null)
        {
          Bitmap localBitmap = localDevice.offBmp;
          if (localBitmap == null) {
            localDevice = null;
          }
        }
        return localDevice;
      }
      return null;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      System.gc();
      throw localOutOfMemoryError;
    }
    catch (IOException localIOException) {}
    return null;
  }
  
  private Bitmap readImage(DataInputStream paramDataInputStream)
    throws OutOfMemoryError
  {
    try
    {
      int k = Integer.reverseBytes(paramDataInputStream.readInt());
      i = k;
    }
    catch (IOException localIOException1)
    {
      for (;;)
      {
        Bitmap localBitmap1;
        byte[] arrayOfByte;
        int i = 0;
      }
    }
    localBitmap1 = null;
    if (i > 0)
    {
      arrayOfByte = new byte[i];
      try
      {
        int j = paramDataInputStream.read(arrayOfByte, 0, i);
        localBitmap1 = null;
        if (j == i) {
          localBitmap1 = BitmapFactory.decodeByteArray(arrayOfByte, 0, i);
        }
        if ((localBitmap1 != null) && (localBitmap1.getHeight() != localBitmap1.getWidth()))
        {
          Bitmap localBitmap2 = Bitmap.createBitmap(Math.max(localBitmap1.getHeight(), localBitmap1.getWidth()), Math.max(localBitmap1.getHeight(), localBitmap1.getWidth()), localBitmap1.getConfig());
          Canvas localCanvas = new Canvas(localBitmap2);
          Rect localRect1 = new Rect(0, 0, localBitmap1.getWidth(), localBitmap1.getHeight());
          Rect localRect2 = new Rect(localRect1);
          if (localBitmap1.getWidth() > localBitmap1.getHeight()) {
            localRect2.offset(0, (localBitmap1.getWidth() - localBitmap1.getHeight()) / 2);
          }
          for (;;)
          {
            localCanvas.drawBitmap(localBitmap1, localRect1, localRect2, null);
            return localBitmap2.copy(localBitmap1.getConfig(), false);
            localRect2.offset((localBitmap1.getHeight() - localBitmap1.getWidth()) / 2, 0);
          }
        }
        return localBitmap1;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        System.gc();
        throw localOutOfMemoryError;
      }
      catch (IOException localIOException2) {}
    }
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public byte getDeviceCode()
  {
    return this.deviceCode;
  }
  
  public boolean getDeviceDimMemoryCap()
  {
    return (0x10 & this.cap) > 0;
  }
  
  public boolean getDeviceDimsCap()
  {
    return (0x2 & this.cap) > 0;
  }
  
  public boolean getDeviceExtendedType0Cap()
  {
    return (0x4 & this.cap) > 0;
  }
  
  public boolean getDeviceExtendedType3Cap()
  {
    return (0x8 & this.cap) > 0;
  }
  
  public boolean getDeviceOnlyOnCmdCap()
  {
    return (0x200 & this.cap) > 0;
  }
  
  public boolean getDeviceRFDeviceCap()
  {
    return (0x400 & this.cap) > 0;
  }
  
  public boolean getDeviceStatusCap()
  {
    return (0x1 & this.cap) > 0;
  }
  
  public byte getDeviceType()
  {
    return this.deviceType;
  }
  
  public byte getDimDuration()
  {
    return this.dimDuration;
  }
  
  public char getHouseCode()
  {
    return this.houseCode;
  }
  
  public Bitmap getOffBitmap()
  {
    return this.offBmp;
  }
  
  public Bitmap getOnBitmap()
  {
    return this.onBmp;
  }
  
  protected void readFromParcel(Parcel paramParcel)
  {
    super.readFromParcel(paramParcel);
    this.houseCode = ((char)paramParcel.readByte());
    this.deviceCode = paramParcel.readByte();
    this.dimDuration = paramParcel.readByte();
    this.deviceType = paramParcel.readByte();
    this.onBmp = ((Bitmap)paramParcel.readParcelable(Bitmap.class.getClassLoader()));
    this.offBmp = ((Bitmap)paramParcel.readParcelable(Bitmap.class.getClassLoader()));
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeByte((byte)this.houseCode);
    paramParcel.writeByte(this.deviceCode);
    paramParcel.writeByte(this.dimDuration);
    paramParcel.writeByte(this.deviceType);
    paramParcel.writeParcelable(this.onBmp, paramInt);
    paramParcel.writeParcelable(this.offBmp, paramInt);
  }
}
