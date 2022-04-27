package com.google.ads;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class AdSize
{
  public static final int AUTO_HEIGHT = -2;
  public static final AdSize BANNER;
  public static final int FULL_WIDTH = -1;
  public static final AdSize IAB_BANNER = new AdSize(468, 60, "as");
  public static final AdSize IAB_LEADERBOARD = new AdSize(728, 90, "as");
  public static final AdSize IAB_MRECT;
  public static final AdSize IAB_WIDE_SKYSCRAPER = new AdSize(160, 600, "as");
  public static final int LANDSCAPE_AD_HEIGHT = 32;
  public static final int LARGE_AD_HEIGHT = 90;
  public static final int PORTRAIT_AD_HEIGHT = 50;
  public static final AdSize SMART_BANNER = new AdSize(-1, -2, "mb");
  private final int a;
  private final int b;
  private boolean c;
  private boolean d;
  private boolean e;
  private String f;
  
  static
  {
    BANNER = new AdSize(320, 50, "mb");
    IAB_MRECT = new AdSize(300, 250, "as");
  }
  
  public AdSize(int paramInt1, int paramInt2)
  {
    this(paramInt1, paramInt2, null);
    if (a())
    {
      this.e = false;
      this.f = "mb";
      return;
    }
    this.e = true;
  }
  
  private AdSize(int paramInt1, int paramInt2, String paramString)
  {
    this.a = paramInt1;
    this.b = paramInt2;
    this.f = paramString;
    boolean bool1;
    if (paramInt1 == -1)
    {
      bool1 = true;
      this.c = bool1;
      if (paramInt2 != -2) {
        break label60;
      }
    }
    label60:
    for (boolean bool2 = true;; bool2 = false)
    {
      this.d = bool2;
      this.e = false;
      return;
      bool1 = false;
      break;
    }
  }
  
  private static int a(Context paramContext)
  {
    DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
    return (int)(localDisplayMetrics.widthPixels / localDisplayMetrics.density);
  }
  
  private boolean a()
  {
    return (this.a < 0) || (this.b < 0);
  }
  
  private static int b(Context paramContext)
  {
    DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
    int i = (int)(localDisplayMetrics.heightPixels / localDisplayMetrics.density);
    if (i <= 400) {
      return 32;
    }
    if (i <= 720) {
      return 50;
    }
    return 90;
  }
  
  public static AdSize createAdSize(AdSize paramAdSize, Context paramContext)
  {
    if ((paramContext == null) || (!paramAdSize.a()))
    {
      if (paramAdSize.a()) {
        return BANNER;
      }
      return paramAdSize;
    }
    int i;
    if (paramAdSize.c)
    {
      i = a(paramContext);
      if (!paramAdSize.d) {
        break label101;
      }
    }
    label101:
    for (int j = b(paramContext);; j = paramAdSize.getHeight())
    {
      AdSize localAdSize = new AdSize(i, j, paramAdSize.f);
      localAdSize.d = paramAdSize.d;
      localAdSize.c = paramAdSize.c;
      localAdSize.e = paramAdSize.e;
      return localAdSize;
      i = paramAdSize.getWidth();
      break;
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AdSize)) {
      return false;
    }
    AdSize localAdSize = (AdSize)paramObject;
    return (this.a == localAdSize.a) && (this.b == localAdSize.b);
  }
  
  public AdSize findBestSize(AdSize... paramVarArgs)
  {
    double d1 = 0.0D;
    Object localObject1 = null;
    if (paramVarArgs != null)
    {
      int i = paramVarArgs.length;
      Object localObject2 = null;
      for (int j = 0; j < i; j++)
      {
        AdSize localAdSize = paramVarArgs[j];
        if (isSizeAppropriate(localAdSize.a, localAdSize.b))
        {
          double d2 = localAdSize.a * localAdSize.b / (this.a * this.b);
          if (d2 > 1.0D) {
            d2 = 1.0D / d2;
          }
          if (d2 > d1)
          {
            d1 = d2;
            localObject2 = localAdSize;
          }
        }
      }
      localObject1 = localObject2;
    }
    return localObject1;
  }
  
  public int getHeight()
  {
    if (this.b < 0) {
      throw new UnsupportedOperationException("Ad size was not set before getHeight() was called.");
    }
    return this.b;
  }
  
  public int getHeightInPixels(Context paramContext)
  {
    return (int)TypedValue.applyDimension(1, this.b, paramContext.getResources().getDisplayMetrics());
  }
  
  public int getWidth()
  {
    if (this.a < 0) {
      throw new UnsupportedOperationException("Ad size was not set before getWidth() was called.");
    }
    return this.a;
  }
  
  public int getWidthInPixels(Context paramContext)
  {
    return (int)TypedValue.applyDimension(1, this.a, paramContext.getResources().getDisplayMetrics());
  }
  
  public int hashCode()
  {
    return Integer.valueOf(this.a).hashCode() << 16 | 0xFFFF & Integer.valueOf(this.b).hashCode();
  }
  
  public boolean isAutoHeight()
  {
    return this.d;
  }
  
  public boolean isCustomAdSize()
  {
    return this.e;
  }
  
  public boolean isFullWidth()
  {
    return this.c;
  }
  
  public boolean isSizeAppropriate(int paramInt1, int paramInt2)
  {
    return (paramInt1 <= 1.25D * this.a) && (paramInt1 >= 0.8D * this.a) && (paramInt2 <= 1.25D * this.b) && (paramInt2 >= 0.8D * this.b);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append(getWidth()).append("x").append(getHeight());
    if (this.f == null) {}
    for (String str = "";; str = "_" + this.f) {
      return str;
    }
  }
}
