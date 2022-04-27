package com.adobe.fre;

import java.nio.ByteBuffer;

public class FREBitmapData
  extends FREObject
{
  private long m_dataPointer;
  
  private FREBitmapData(FREObject.CFREObjectWrapper paramCFREObjectWrapper)
  {
    super(paramCFREObjectWrapper);
  }
  
  protected FREBitmapData(FREObject[] paramArrayOfFREObject)
    throws FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException, IllegalStateException
  {
    super("flash.display.BitmapData", paramArrayOfFREObject);
  }
  
  public static FREBitmapData newBitmapData(int paramInt1, int paramInt2, boolean paramBoolean, Byte[] paramArrayOfByte)
    throws FREASErrorException, FREWrongThreadException, IllegalArgumentException
  {
    if (paramArrayOfByte.length != 4) {
      throw new IllegalArgumentException("fillColor has wrong length");
    }
    FREObject[] arrayOfFREObject = new FREObject[4];
    arrayOfFREObject[0] = new FREObject(paramInt1);
    arrayOfFREObject[1] = new FREObject(paramInt2);
    arrayOfFREObject[2] = new FREObject(paramBoolean);
    int i = 0;
    int j = -1;
    for (int k = 0; k < 4; k++)
    {
      int m = 8 * (3 - k);
      i |= j & paramArrayOfByte[k].byteValue() << m;
      j >>>= 8;
    }
    arrayOfFREObject[3] = new FREObject(i);
    try
    {
      FREBitmapData localFREBitmapData = new FREBitmapData(arrayOfFREObject);
      return localFREBitmapData;
    }
    catch (FRENoSuchNameException localFRENoSuchNameException)
    {
      return null;
    }
    catch (FREInvalidObjectException localFREInvalidObjectException)
    {
      for (;;) {}
    }
    catch (FRETypeMismatchException localFRETypeMismatchException)
    {
      for (;;) {}
    }
  }
  
  public native void acquire()
    throws FREInvalidObjectException, FREWrongThreadException, IllegalStateException;
  
  public native ByteBuffer getBits()
    throws FREInvalidObjectException, FREWrongThreadException, IllegalStateException;
  
  public native int getHeight()
    throws FREInvalidObjectException, FREWrongThreadException, IllegalStateException;
  
  public native int getLineStride32()
    throws FREInvalidObjectException, FREWrongThreadException, IllegalStateException;
  
  public native int getWidth()
    throws FREInvalidObjectException, FREWrongThreadException, IllegalStateException;
  
  public native boolean hasAlpha()
    throws FREInvalidObjectException, FREWrongThreadException, IllegalStateException;
  
  public native void invalidateRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws FREInvalidObjectException, FREWrongThreadException, IllegalStateException, IllegalArgumentException;
  
  public native boolean isInvertedY()
    throws FREInvalidObjectException, FREWrongThreadException, IllegalStateException;
  
  public native boolean isPremultiplied()
    throws FREInvalidObjectException, FREWrongThreadException, IllegalStateException;
  
  public native void release()
    throws FREInvalidObjectException, FREWrongThreadException, IllegalStateException;
}
