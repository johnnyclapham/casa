package com.adobe.fre;

public abstract interface FREExtension
{
  public abstract FREContext createContext(String paramString);
  
  public abstract void dispose();
  
  public abstract void initialize();
}
