package com.google.ads.util;

import android.util.Log;

public final class b
{
  public static b a = null;
  private static int b = 5;
  
  private static void a(a paramA, String paramString)
  {
    a(paramA, paramString, null);
  }
  
  private static void a(a paramA, String paramString, Throwable paramThrowable)
  {
    if (a != null) {
      a.a(paramA, paramString, paramThrowable);
    }
  }
  
  public static void a(String paramString)
  {
    if (a("Ads", 3)) {
      Log.d("Ads", paramString);
    }
    a(a.b, paramString);
  }
  
  public static void a(String paramString, Throwable paramThrowable)
  {
    if (a("Ads", 3)) {
      Log.d("Ads", paramString, paramThrowable);
    }
    a(a.b, paramString, paramThrowable);
  }
  
  private static boolean a(int paramInt)
  {
    return paramInt >= b;
  }
  
  public static boolean a(String paramString, int paramInt)
  {
    return (a(paramInt)) || (Log.isLoggable(paramString, paramInt));
  }
  
  public static void b(String paramString)
  {
    if (a("Ads", 6)) {
      Log.e("Ads", paramString);
    }
    a(a.e, paramString);
  }
  
  public static void b(String paramString, Throwable paramThrowable)
  {
    if (a("Ads", 6))
    {
      Log.e("Ads", paramString);
      Log.i("Ads", "The following was caught and handled:", paramThrowable);
    }
    a(a.e, paramString, paramThrowable);
  }
  
  public static void c(String paramString)
  {
    if (a("Ads", 4)) {
      Log.i("Ads", paramString);
    }
    a(a.c, paramString);
  }
  
  public static void c(String paramString, Throwable paramThrowable)
  {
    if (a("Ads", 4)) {
      Log.i("Ads", paramString, paramThrowable);
    }
    a(a.c, paramString, paramThrowable);
  }
  
  public static void d(String paramString)
  {
    if (a("Ads", 2)) {
      Log.v("Ads", paramString);
    }
    a(a.a, paramString);
  }
  
  public static void d(String paramString, Throwable paramThrowable)
  {
    if (a("Ads", 5))
    {
      Log.w("Ads", paramString);
      Log.i("Ads", "The following was caught and handled:", paramThrowable);
    }
    a(a.d, paramString, paramThrowable);
  }
  
  public static void e(String paramString)
  {
    if (a("Ads", 5)) {
      Log.w("Ads", paramString);
    }
    a(a.d, paramString);
  }
  
  public static enum a
  {
    public final int f;
    
    static
    {
      a[] arrayOfA = new a[5];
      arrayOfA[0] = a;
      arrayOfA[1] = b;
      arrayOfA[2] = c;
      arrayOfA[3] = d;
      arrayOfA[4] = e;
      g = arrayOfA;
    }
    
    private a(int paramInt)
    {
      this.f = paramInt;
    }
  }
  
  public static abstract interface b
  {
    public abstract void a(b.a paramA, String paramString, Throwable paramThrowable);
  }
}
