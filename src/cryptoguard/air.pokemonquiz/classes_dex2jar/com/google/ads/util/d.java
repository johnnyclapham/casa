package com.google.ads.util;

import android.os.Build;

class d
{
  static final d d = new d();
  static final d e = new d("unknown", "generic", "generic");
  static final d f = new d("unknown", "generic_x86", "Android");
  public final String a;
  public final String b;
  public final String c;
  
  d()
  {
    this.a = Build.BOARD;
    this.b = Build.DEVICE;
    this.c = Build.BRAND;
  }
  
  d(String paramString1, String paramString2, String paramString3)
  {
    this.a = paramString1;
    this.b = paramString2;
    this.c = paramString3;
  }
  
  private static boolean a(String paramString1, String paramString2)
  {
    if (paramString1 != null) {
      return paramString1.equals(paramString2);
    }
    return paramString1 == paramString2;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof d)) {
      return false;
    }
    d localD = (d)paramObject;
    return (a(this.a, localD.a)) && (a(this.b, localD.b)) && (a(this.c, localD.c));
  }
  
  public int hashCode()
  {
    String str = this.a;
    int i = 0;
    if (str != null) {
      i = 0 + this.a.hashCode();
    }
    if (this.b != null) {
      i += this.b.hashCode();
    }
    if (this.c != null) {
      i += this.c.hashCode();
    }
    return i;
  }
}
