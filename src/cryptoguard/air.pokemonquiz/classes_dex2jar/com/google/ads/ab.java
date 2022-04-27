package com.google.ads;

import android.app.Activity;
import android.webkit.WebView;
import com.google.ads.internal.d;
import com.google.ads.util.b;
import com.google.ads.util.i.b;
import com.google.ads.util.i.d;
import java.util.HashMap;

public class ab
  implements o
{
  public ab() {}
  
  public void a(d paramD, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    if ((Activity)paramD.i().c.a() == null)
    {
      b.e("Activity was null while responding to touch gmsg.");
      return;
    }
    String str1 = (String)paramHashMap.get("tx");
    String str2 = (String)paramHashMap.get("ty");
    String str3 = (String)paramHashMap.get("td");
    try
    {
      int i = Integer.parseInt(str1);
      int j = Integer.parseInt(str2);
      int k = Integer.parseInt(str3);
      ((ak)paramD.i().l.a()).a(i, j, k);
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      b.e("Could not parse touch parameters from gmsg.");
    }
  }
}
