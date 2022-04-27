package com.google.ads;

import android.text.TextUtils;
import android.webkit.WebView;
import com.google.ads.internal.ActivationOverlay;
import com.google.ads.internal.AdWebView;
import com.google.ads.internal.d;
import com.google.ads.internal.h;
import com.google.ads.util.AdUtil;
import com.google.ads.util.b;
import com.google.ads.util.g;
import com.google.ads.util.i.b;
import com.google.ads.util.i.c;
import java.util.HashMap;

public class x
  implements o
{
  public x() {}
  
  private void a(HashMap<String, String> paramHashMap, String paramString, i.c<Integer> paramC)
  {
    try
    {
      String str = (String)paramHashMap.get(paramString);
      if (!TextUtils.isEmpty(str)) {
        paramC.a(Integer.valueOf(str));
      }
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      b.a("Could not parse \"" + paramString + "\" constant.");
    }
  }
  
  private void b(HashMap<String, String> paramHashMap, String paramString, i.c<Long> paramC)
  {
    try
    {
      String str = (String)paramHashMap.get(paramString);
      if (!TextUtils.isEmpty(str)) {
        paramC.a(Long.valueOf(str));
      }
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      b.a("Could not parse \"" + paramString + "\" constant.");
    }
  }
  
  private void c(HashMap<String, String> paramHashMap, String paramString, i.c<String> paramC)
  {
    String str = (String)paramHashMap.get(paramString);
    if (!TextUtils.isEmpty(str)) {
      paramC.a(str);
    }
  }
  
  public void a(d paramD, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    n localN = paramD.i();
    m.a localA = (m.a)((m)localN.d.a()).a.a();
    c(paramHashMap, "as_domains", localA.a);
    c(paramHashMap, "bad_ad_report_path", localA.h);
    a(paramHashMap, "min_hwa_banner", localA.b);
    a(paramHashMap, "min_hwa_activation_overlay", localA.c);
    a(paramHashMap, "min_hwa_overlay", localA.d);
    c(paramHashMap, "mraid_banner_path", localA.e);
    c(paramHashMap, "mraid_expanded_banner_path", localA.f);
    c(paramHashMap, "mraid_interstitial_path", localA.g);
    b(paramHashMap, "ac_max_size", localA.i);
    b(paramHashMap, "ac_padding", localA.j);
    b(paramHashMap, "ac_total_quota", localA.k);
    b(paramHashMap, "db_total_quota", localA.l);
    b(paramHashMap, "db_quota_per_origin", localA.m);
    b(paramHashMap, "db_quota_step_size", localA.n);
    AdWebView localAdWebView = paramD.l();
    if (AdUtil.a >= 11)
    {
      g.a(localAdWebView.getSettings(), localN);
      g.a(paramWebView.getSettings(), localN);
    }
    boolean bool2;
    int j;
    label296:
    ActivationOverlay localActivationOverlay;
    boolean bool1;
    int i;
    if (!((h)localN.g.a()).a())
    {
      bool2 = localAdWebView.k();
      if (AdUtil.a < ((Integer)localA.b.a()).intValue())
      {
        j = 1;
        if ((j != 0) || (!bool2)) {
          break label437;
        }
        b.a("Re-enabling hardware acceleration for a banner after reading constants.");
        localAdWebView.h();
      }
    }
    else
    {
      localActivationOverlay = (ActivationOverlay)localN.e.a();
      if ((!((h)localN.g.a()).b()) && (localActivationOverlay != null))
      {
        bool1 = localActivationOverlay.k();
        if (AdUtil.a >= ((Integer)localA.c.a()).intValue()) {
          break label460;
        }
        i = 1;
        label361:
        if ((i != 0) || (!bool1)) {
          break label466;
        }
        b.a("Re-enabling hardware acceleration for an activation overlay after reading constants.");
        localActivationOverlay.h();
      }
    }
    for (;;)
    {
      String str = (String)localA.a.a();
      if (!TextUtils.isEmpty(str)) {
        ((al)localN.m.a()).a(str);
      }
      localA.o.a(Boolean.valueOf(true));
      return;
      j = 0;
      break;
      label437:
      if ((j == 0) || (bool2)) {
        break label296;
      }
      b.a("Disabling hardware acceleration for a banner after reading constants.");
      localAdWebView.g();
      break label296;
      label460:
      i = 0;
      break label361;
      label466:
      if ((i != 0) && (!bool1))
      {
        b.a("Disabling hardware acceleration for an activation overlay after reading constants.");
        localActivationOverlay.g();
      }
    }
  }
}
