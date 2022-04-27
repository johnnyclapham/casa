package com.google.ads.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.ads.AdActivity;
import com.google.ads.AdSize;
import com.google.ads.ak;
import com.google.ads.n;
import com.google.ads.util.AdUtil;
import com.google.ads.util.IcsUtil.a;
import com.google.ads.util.b;
import com.google.ads.util.g;
import com.google.ads.util.g.a;
import com.google.ads.util.h;
import com.google.ads.util.i.b;
import java.lang.ref.WeakReference;

public class AdWebView
  extends WebView
{
  protected final n a;
  private WeakReference<AdActivity> b;
  private AdSize c;
  private boolean d;
  private boolean e;
  private boolean f;
  
  public AdWebView(n paramN, AdSize paramAdSize)
  {
    super((Context)paramN.f.a());
    this.a = paramN;
    this.c = paramAdSize;
    this.b = null;
    this.d = false;
    this.e = false;
    this.f = false;
    setBackgroundColor(0);
    AdUtil.a(this);
    WebSettings localWebSettings = getSettings();
    localWebSettings.setSupportMultipleWindows(false);
    localWebSettings.setJavaScriptEnabled(true);
    localWebSettings.setSavePassword(false);
    setDownloadListener(new DownloadListener()
    {
      public void onDownloadStart(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, String paramAnonymousString4, long paramAnonymousLong)
      {
        try
        {
          Intent localIntent = new Intent("android.intent.action.VIEW");
          localIntent.setDataAndType(Uri.parse(paramAnonymousString1), paramAnonymousString4);
          AdActivity localAdActivity = AdWebView.this.i();
          if ((localAdActivity != null) && (AdUtil.a(localIntent, localAdActivity))) {
            localAdActivity.startActivity(localIntent);
          }
          return;
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
          b.a("Couldn't find an Activity to view url/mimetype: " + paramAnonymousString1 + " / " + paramAnonymousString4);
          return;
        }
        catch (Throwable localThrowable)
        {
          b.b("Unknown error trying to start activity to view URL: " + paramAnonymousString1, localThrowable);
        }
      }
    });
    if (AdUtil.a >= 17) {
      h.a(localWebSettings, paramN);
    }
    do
    {
      for (;;)
      {
        setScrollBarStyle(33554432);
        if (AdUtil.a < 14) {
          break;
        }
        setWebChromeClient(new IcsUtil.a(paramN));
        return;
        if (AdUtil.a >= 11) {
          g.a(localWebSettings, paramN);
        }
      }
    } while (AdUtil.a < 11);
    setWebChromeClient(new g.a(paramN));
  }
  
  public void a(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      setOnTouchListener(new View.OnTouchListener()
      {
        public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
        {
          return paramAnonymousMotionEvent.getAction() == 2;
        }
      });
      return;
    }
    setOnTouchListener(null);
  }
  
  public void destroy()
  {
    try
    {
      super.destroy();
    }
    catch (Throwable localThrowable1)
    {
      for (;;)
      {
        try
        {
          setWebViewClient(new WebViewClient());
          return;
        }
        catch (Throwable localThrowable2) {}
        localThrowable1 = localThrowable1;
        b.d("An error occurred while destroying an AdWebView:", localThrowable1);
      }
    }
  }
  
  public void f()
  {
    AdActivity localAdActivity = i();
    if (localAdActivity != null) {
      localAdActivity.finish();
    }
  }
  
  public void g()
  {
    if (AdUtil.a >= 11) {
      g.a(this);
    }
    this.e = true;
  }
  
  public void h()
  {
    if ((this.e) && (AdUtil.a >= 11)) {
      g.b(this);
    }
    this.e = false;
  }
  
  public AdActivity i()
  {
    if (this.b != null) {
      return (AdActivity)this.b.get();
    }
    return null;
  }
  
  public boolean j()
  {
    return this.f;
  }
  
  public boolean k()
  {
    return this.e;
  }
  
  public void loadDataWithBaseURL(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    try
    {
      super.loadDataWithBaseURL(paramString1, paramString2, paramString3, paramString4, paramString5);
      return;
    }
    catch (Throwable localThrowable)
    {
      b.d("An error occurred while loading data in AdWebView:", localThrowable);
    }
  }
  
  public void loadUrl(String paramString)
  {
    try
    {
      super.loadUrl(paramString);
      return;
    }
    catch (Throwable localThrowable)
    {
      b.d("An error occurred while loading a URL in AdWebView:", localThrowable);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    try
    {
      if (isInEditMode()) {
        super.onMeasure(paramInt1, paramInt2);
      }
      for (;;)
      {
        return;
        if ((this.c != null) && (!this.d)) {
          break;
        }
        super.onMeasure(paramInt1, paramInt2);
      }
      i = View.MeasureSpec.getMode(paramInt1);
    }
    finally {}
    int i;
    int j = View.MeasureSpec.getSize(paramInt1);
    int k = View.MeasureSpec.getMode(paramInt2);
    int m = View.MeasureSpec.getSize(paramInt2);
    float f1 = getContext().getResources().getDisplayMetrics().density;
    int n = (int)(f1 * this.c.getWidth());
    int i1 = (int)(f1 * this.c.getHeight());
    if (i != Integer.MIN_VALUE) {
      if (i == 1073741824) {
        break label230;
      }
    }
    for (;;)
    {
      label130:
      b.b("Not enough space to show ad! Wants: <" + n + ", " + i1 + ">, Has: <" + j + ", " + m + ">");
      setVisibility(8);
      setMeasuredDimension(j, m);
      break;
      int i3;
      label230:
      do
      {
        setMeasuredDimension(n, i1);
        break;
        int i2;
        do
        {
          i3 = Integer.MAX_VALUE;
          break;
          i2 = Integer.MAX_VALUE;
          continue;
          i2 = j;
        } while ((k != Integer.MIN_VALUE) && (k != 1073741824));
        i3 = m;
        if (n - f1 * 6.0F > i2) {
          break label130;
        }
      } while (i1 <= i3);
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    ((ak)this.a.l.a()).a(paramMotionEvent);
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void setAdActivity(AdActivity paramAdActivity)
  {
    this.b = new WeakReference(paramAdActivity);
  }
  
  public void setAdSize(AdSize paramAdSize)
  {
    try
    {
      this.c = paramAdSize;
      requestLayout();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void setCustomClose(boolean paramBoolean)
  {
    this.f = paramBoolean;
    if (this.b != null)
    {
      AdActivity localAdActivity = (AdActivity)this.b.get();
      if (localAdActivity != null) {
        localAdActivity.setCustomClose(paramBoolean);
      }
    }
  }
  
  public void setIsExpandedMraid(boolean paramBoolean)
  {
    this.d = paramBoolean;
  }
  
  public void stopLoading()
  {
    try
    {
      super.stopLoading();
      return;
    }
    catch (Throwable localThrowable)
    {
      b.d("An error occurred while stopping loading in AdWebView:", localThrowable);
    }
  }
}
