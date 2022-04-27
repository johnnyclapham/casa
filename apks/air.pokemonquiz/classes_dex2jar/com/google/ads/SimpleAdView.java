package com.google.ads;

import android.app.Activity;

public class SimpleAdView
{
  public SimpleAdView() {}
  
  public static AdView createView(Activity paramActivity, AdSize paramAdSize, String paramString)
  {
    return new AdView(paramActivity, paramAdSize, c.p.check(paramString));
  }
}
