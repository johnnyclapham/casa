package com.adobe.fre;

import java.nio.ByteBuffer;

public class FREByteArray
  extends FREObject
{
  private long m_dataPointer;
  
  protected FREByteArray()
    throws FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException, IllegalStateException
  {
    super("flash.utils.ByteArray", null);
  }
  
  private FREByteArray(FREObject.CFREObjectWrapper paramCFREObjectWrapper)
  {
    super(paramCFREObjectWrapper);
  }
  
  public static FREByteArray newByteArray()
    throws FREASErrorException, FREWrongThreadException, IllegalStateException
  {
    try
    {
      FREByteArray localFREByteArray = new FREByteArray();
      return localFREByteArray;
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
  
  public native ByteBuffer getBytes()
    throws FREInvalidObjectException, FREWrongThreadException, IllegalStateException;
  
  public native long getLength()
    throws FREInvalidObjectException, FREWrongThreadException, IllegalStateException;
  
  public native void release()
    throws FREInvalidObjectException, FREWrongThreadException, IllegalStateException;
}
