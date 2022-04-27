package com.google.ads;

import android.text.TextUtils;
import android.webkit.WebView;
import com.google.ads.internal.ActivationOverlay;
import com.google.ads.internal.d;
import com.google.ads.util.b;
import java.util.HashMap;

public class aa
  implements o
{
  public aa() {}
  
  public void a(d paramD, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    if ((paramWebView instanceof ActivationOverlay)) {}
    for (;;)
    {
      try
      {
        if (TextUtils.isEmpty((CharSequence)paramHashMap.get("w"))) {
          break label256;
        }
        i = Integer.parseInt((String)paramHashMap.get("w"));
        if (TextUtils.isEmpty((CharSequence)paramHashMap.get("h"))) {
          break label250;
        }
        j = Integer.parseInt((String)paramHashMap.get("h"));
        if (TextUtils.isEmpty((CharSequence)paramHashMap.get("x"))) {
          break label244;
        }
        k = Integer.parseInt((String)paramHashMap.get("x"));
        if (TextUtils.isEmpty((CharSequence)paramHashMap.get("y"))) {
          break label238;
        }
        int n = Integer.parseInt((String)paramHashMap.get("y"));
        m = n;
        if ((paramHashMap.get("a") != null) && (((String)paramHashMap.get("a")).equals("1")))
        {
          paramD.a(null, true, k, m, i, j);
          return;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        b.d("Invalid number format in activation overlay response.", localNumberFormatException);
        return;
      }
      if ((paramHashMap.get("a") != null) && (((String)paramHashMap.get("a")).equals("0")))
      {
        paramD.a(null, false, k, m, i, j);
        return;
      }
      paramD.a(k, m, i, j);
      return;
      b.b("Trying to activate an overlay when this is not an overlay.");
      return;
      label238:
      int m = -1;
      continue;
      label244:
      int k = -1;
      continue;
      label250:
      int j = -1;
      continue;
      label256:
      int i = -1;
    }
  }
}
