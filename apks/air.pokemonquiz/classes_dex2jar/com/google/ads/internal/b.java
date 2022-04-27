package com.google.ads.internal;

public final class b
  extends Exception
{
  public final boolean a;
  
  public b(String paramString, boolean paramBoolean)
  {
    super(paramString);
    this.a = paramBoolean;
  }
  
  public b(String paramString, boolean paramBoolean, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
    this.a = paramBoolean;
  }
  
  public void a(String paramString)
  {
    com.google.ads.util.b.b(c(paramString));
    com.google.ads.util.b.a(null, this);
  }
  
  public void b(String paramString)
  {
    String str = c(paramString);
    if (this.a) {}
    for (b localB = this;; localB = null) {
      throw new RuntimeException(str, localB);
    }
  }
  
  public String c(String paramString)
  {
    if (this.a) {
      return paramString + ": " + getMessage();
    }
    return paramString;
  }
}
