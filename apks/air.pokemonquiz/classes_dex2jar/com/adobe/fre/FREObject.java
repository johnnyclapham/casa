package com.adobe.fre;

public class FREObject
{
  private long m_objectPointer;
  
  protected FREObject(double paramDouble)
    throws FREWrongThreadException
  {
    FREObjectFromDouble(paramDouble);
  }
  
  protected FREObject(int paramInt)
    throws FREWrongThreadException
  {
    FREObjectFromInt(paramInt);
  }
  
  protected FREObject(CFREObjectWrapper paramCFREObjectWrapper)
  {
    this.m_objectPointer = paramCFREObjectWrapper.m_objectPointer;
  }
  
  protected FREObject(String paramString)
    throws FREWrongThreadException
  {
    FREObjectFromString(paramString);
  }
  
  public FREObject(String paramString, FREObject[] paramArrayOfFREObject)
    throws FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException, IllegalStateException
  {
    FREObjectFromClass(paramString, paramArrayOfFREObject);
  }
  
  protected FREObject(boolean paramBoolean)
    throws FREWrongThreadException
  {
    FREObjectFromBoolean(paramBoolean);
  }
  
  private native void FREObjectFromBoolean(boolean paramBoolean)
    throws FREWrongThreadException;
  
  private native void FREObjectFromClass(String paramString, FREObject[] paramArrayOfFREObject)
    throws FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException;
  
  private native void FREObjectFromDouble(double paramDouble)
    throws FREWrongThreadException;
  
  private native void FREObjectFromInt(int paramInt)
    throws FREWrongThreadException;
  
  private native void FREObjectFromString(String paramString)
    throws FREWrongThreadException;
  
  public static FREObject newObject(double paramDouble)
    throws FREWrongThreadException
  {
    return new FREObject(paramDouble);
  }
  
  public static FREObject newObject(int paramInt)
    throws FREWrongThreadException
  {
    return new FREObject(paramInt);
  }
  
  public static FREObject newObject(String paramString)
    throws FREWrongThreadException
  {
    return new FREObject(paramString);
  }
  
  public static native FREObject newObject(String paramString, FREObject[] paramArrayOfFREObject)
    throws FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException, IllegalStateException;
  
  public static FREObject newObject(boolean paramBoolean)
    throws FREWrongThreadException
  {
    return new FREObject(paramBoolean);
  }
  
  public native FREObject callMethod(String paramString, FREObject[] paramArrayOfFREObject)
    throws FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException, IllegalStateException;
  
  public native boolean getAsBool()
    throws FRETypeMismatchException, FREInvalidObjectException, FREWrongThreadException, IllegalStateException;
  
  public native double getAsDouble()
    throws FRETypeMismatchException, FREInvalidObjectException, FREWrongThreadException, IllegalStateException;
  
  public native int getAsInt()
    throws FRETypeMismatchException, FREInvalidObjectException, FREWrongThreadException, IllegalStateException;
  
  public native String getAsString()
    throws FRETypeMismatchException, FREInvalidObjectException, FREWrongThreadException, IllegalStateException;
  
  public native FREObject getProperty(String paramString)
    throws FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException, IllegalStateException;
  
  public native void setProperty(String paramString, FREObject paramFREObject)
    throws FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREReadOnlyException, FREWrongThreadException, IllegalStateException;
  
  protected static class CFREObjectWrapper
  {
    private long m_objectPointer;
    
    private CFREObjectWrapper(long paramLong)
    {
      this.m_objectPointer = paramLong;
    }
  }
}
