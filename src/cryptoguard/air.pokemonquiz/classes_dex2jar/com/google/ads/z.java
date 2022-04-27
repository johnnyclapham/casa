package com.google.ads;

import android.webkit.WebView;
import com.google.ads.internal.d;
import com.google.ads.internal.e;
import com.google.ads.util.b;
import java.util.HashMap;

public class z
  implements o
{
  private AdActivity.StaticMethodWrapper a;
  
  public z()
  {
    this(new AdActivity.StaticMethodWrapper());
  }
  
  public z(AdActivity.StaticMethodWrapper paramStaticMethodWrapper)
  {
    this.a = paramStaticMethodWrapper;
  }
  
  public void a(d paramD, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    String str = (String)paramHashMap.get("a");
    if (str == null)
    {
      b.a("Could not get the action parameter for open GMSG.");
      return;
    }
    if (str.equals("webapp"))
    {
      this.a.launchAdActivity(paramD, new e("webapp", paramHashMap));
      return;
    }
    if (str.equals("expand"))
    {
      this.a.launchAdActivity(paramD, new e("expand", paramHashMap));
      return;
    }
    this.a.launchAdActivity(paramD, new e("intent", paramHashMap));
  }
}
