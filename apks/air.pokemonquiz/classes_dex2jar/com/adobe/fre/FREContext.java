package com.adobe.fre;

import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class FREContext
{
  private long m_objectPointer;
  
  public FREContext() {}
  
  private native void registerFunction(long paramLong, String paramString, FREFunction paramFREFunction);
  
  private native void registerFunctionCount(long paramLong, int paramInt);
  
  protected void VisitFunctions(long paramLong)
  {
    Map localMap = getFunctions();
    registerFunctionCount(paramLong, localMap.size());
    Iterator localIterator = localMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      registerFunction(paramLong, (String)localEntry.getKey(), (FREFunction)localEntry.getValue());
    }
  }
  
  public native void dispatchStatusEventAsync(String paramString1, String paramString2)
    throws IllegalArgumentException, IllegalStateException;
  
  public abstract void dispose();
  
  public native FREObject getActionScriptData()
    throws FREWrongThreadException, IllegalStateException;
  
  public native Activity getActivity()
    throws IllegalStateException;
  
  public abstract Map<String, FREFunction> getFunctions();
  
  public native int getResourceId(String paramString)
    throws IllegalArgumentException, Resources.NotFoundException, IllegalStateException;
  
  public native void setActionScriptData(FREObject paramFREObject)
    throws FREWrongThreadException, IllegalArgumentException, IllegalStateException;
}
