package com.google.ads;

import android.webkit.WebView;
import com.google.ads.internal.c;
import com.google.ads.internal.c.d;
import com.google.ads.internal.d;
import com.google.ads.util.b;
import java.util.HashMap;

public class w
  implements o
{
  public w() {}
  
  public void a(d paramD, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    String str1 = (String)paramHashMap.get("url");
    String str2 = (String)paramHashMap.get("type");
    String str3 = (String)paramHashMap.get("afma_notify_dt");
    String str4 = (String)paramHashMap.get("activation_overlay_url");
    boolean bool1 = "1".equals(paramHashMap.get("drt_include"));
    String str5 = (String)paramHashMap.get("request_scenario");
    boolean bool2 = "1".equals(paramHashMap.get("use_webview_loadurl"));
    c.d localD;
    if (c.d.d.e.equals(str5)) {
      localD = c.d.d;
    }
    for (;;)
    {
      b.c("Received ad url: <url: \"" + str1 + "\" type: \"" + str2 + "\" afmaNotifyDt: \"" + str3 + "\" activationOverlayUrl: \"" + str4 + "\" useWebViewLoadUrl: \"" + bool2 + "\">");
      c localC = paramD.k();
      if (localC != null)
      {
        localC.d(bool1);
        localC.a(localD);
        localC.e(bool2);
        localC.e(str4);
        localC.d(str1);
      }
      return;
      if (c.d.c.e.equals(str5)) {
        localD = c.d.c;
      } else if (c.d.a.e.equals(str5)) {
        localD = c.d.a;
      } else {
        localD = c.d.b;
      }
    }
  }
}
