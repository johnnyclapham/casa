package com.google.ads.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.ads.AdActivity;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.al;
import com.google.ads.n;
import com.google.ads.o;
import com.google.ads.util.AdUtil;
import com.google.ads.util.b;
import com.google.ads.util.f;
import com.google.ads.util.g.b;
import com.google.ads.util.i.b;
import java.util.HashMap;
import java.util.Map;

public class i
  extends WebViewClient
{
  private static final a c = (a)a.a.b();
  protected d a;
  protected boolean b;
  private final Map<String, o> d;
  private final boolean e;
  private boolean f;
  private boolean g;
  private boolean h;
  private boolean i;
  
  public i(d paramD, Map<String, o> paramMap, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.a = paramD;
    this.d = paramMap;
    this.e = paramBoolean1;
    this.g = paramBoolean2;
    this.b = false;
    this.h = false;
    this.i = false;
  }
  
  public static i a(d paramD, Map<String, o> paramMap, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (AdUtil.a >= 11) {
      return new g.b(paramD, paramMap, paramBoolean1, paramBoolean2);
    }
    return new i(paramD, paramMap, paramBoolean1, paramBoolean2);
  }
  
  public void a(boolean paramBoolean)
  {
    this.b = paramBoolean;
  }
  
  public boolean a()
  {
    return this.f;
  }
  
  public void b(boolean paramBoolean)
  {
    this.g = paramBoolean;
  }
  
  public void c(boolean paramBoolean)
  {
    this.h = paramBoolean;
  }
  
  public void d(boolean paramBoolean)
  {
    this.i = paramBoolean;
  }
  
  public void onPageFinished(WebView paramWebView, String paramString)
  {
    this.f = false;
    if (this.h)
    {
      c localC = this.a.k();
      if (localC == null) {
        break label53;
      }
      localC.c();
    }
    for (;;)
    {
      this.h = false;
      if (this.i)
      {
        c.a(paramWebView);
        this.i = false;
      }
      return;
      label53:
      b.a("adLoader was null while trying to setFinishedLoadingHtml().");
    }
  }
  
  public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
  {
    this.f = true;
  }
  
  public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
  {
    this.f = false;
    c localC = this.a.k();
    if (localC != null) {
      localC.a(AdRequest.ErrorCode.NETWORK_ERROR);
    }
  }
  
  public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
  {
    try
    {
      b.a("shouldOverrideUrlLoading(\"" + paramString + "\")");
      localUri1 = Uri.parse(paramString);
      if (c.a(localUri1))
      {
        c.a(this.a, this.d, localUri1, paramWebView);
        return true;
      }
      if (this.g)
      {
        if (AdUtil.a(localUri1)) {
          return super.shouldOverrideUrlLoading(paramWebView, paramString);
        }
        HashMap localHashMap2 = new HashMap();
        localHashMap2.put("u", paramString);
        AdActivity.launchAdActivity(this.a, new e("intent", localHashMap2));
        return true;
      }
      if (!this.e) {
        break label228;
      }
      n localN = this.a.i();
      Context localContext = (Context)localN.f.a();
      al localAl = (al)localN.m.a();
      if (!localAl.a(localUri1)) {
        break label262;
      }
      localUri2 = localAl.a(localUri1, localContext);
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        Uri localUri1;
        HashMap localHashMap1;
        label228:
        b.d("An unknown error occurred in shouldOverrideUrlLoading.", localThrowable);
        continue;
        label262:
        Uri localUri2 = localUri1;
      }
    }
    localHashMap1 = new HashMap();
    localHashMap1.put("u", localUri2.toString());
    AdActivity.launchAdActivity(this.a, new e("intent", localHashMap1));
    return true;
    b.e("URL is not a GMSG and can't handle URL: " + paramString);
    return true;
  }
}
