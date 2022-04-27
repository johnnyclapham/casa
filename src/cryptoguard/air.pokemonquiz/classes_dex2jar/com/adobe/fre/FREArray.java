package com.adobe.fre;

public class FREArray
  extends FREObject
{
  private FREArray(FREObject.CFREObjectWrapper paramCFREObjectWrapper)
  {
    super(paramCFREObjectWrapper);
  }
  
  protected FREArray(String paramString, FREObject[] paramArrayOfFREObject)
    throws FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException
  {
    super("Vector.<" + paramString + ">", paramArrayOfFREObject);
  }
  
  protected FREArray(FREObject[] paramArrayOfFREObject)
    throws FRETypeMismatchException, FREInvalidObjectException, FREASErrorException, FRENoSuchNameException, FREWrongThreadException
  {
    super("Array", paramArrayOfFREObject);
  }
  
  public static FREArray newArray(int paramInt)
    throws FREASErrorException, FREWrongThreadException, IllegalStateException
  {
    FREObject[] arrayOfFREObject = new FREObject[1];
    arrayOfFREObject[0] = new FREObject(paramInt);
    try
    {
      FREArray localFREArray = new FREArray(arrayOfFREObject);
      return localFREArray;
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
  
  public static FREArray newArray(String paramString, int paramInt, boolean paramBoolean)
    throws FREASErrorException, FRENoSuchNameException, FREWrongThreadException, IllegalStateException
  {
    FREObject[] arrayOfFREObject = new FREObject[2];
    arrayOfFREObject[0] = new FREObject(paramInt);
    arrayOfFREObject[1] = new FREObject(paramBoolean);
    try
    {
      FREArray localFREArray = new FREArray(paramString, arrayOfFREObject);
      return localFREArray;
    }
    catch (FREInvalidObjectException localFREInvalidObjectException)
    {
      return null;
    }
    catch (FRETypeMismatchException localFRETypeMismatchException)
    {
      for (;;) {}
    }
  }
  
  public native long getLength()
    throws FREInvalidObjectException, FREWrongThreadException;
  
  public native FREObject getObjectAt(long paramLong)
    throws FREInvalidObjectException, IllegalArgumentException, FREWrongThreadException;
  
  public native void setLength(long paramLong)
    throws FREInvalidObjectException, IllegalArgumentException, FREReadOnlyException, FREWrongThreadException;
  
  public native void setObjectAt(long paramLong, FREObject paramFREObject)
    throws FREInvalidObjectException, FRETypeMismatchException, FREWrongThreadException;
}
