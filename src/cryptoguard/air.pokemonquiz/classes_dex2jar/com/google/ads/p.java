package com.google.ads;

import android.webkit.WebView;
import com.google.ads.internal.d;
import com.google.ads.util.b;
import java.util.HashMap;

public class p
  implements o
{
  public p() {}
  
  public void a(d paramD, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    String str = (String)paramHashMap.get("name");
    if (str == null)
    {
      b.b("Error: App event with no name parameter.");
      return;
    }
    paramD.a(str, (String)paramHashMap.get("info"));
  }
}
