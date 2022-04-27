package com.google.ads.util;

import android.annotation.TargetApi;
import android.view.View;
import android.webkit.WebChromeClient.CustomViewCallback;
import com.google.ads.AdSize;
import com.google.ads.internal.ActivationOverlay;
import com.google.ads.internal.AdWebView;
import com.google.ads.n;

@TargetApi(14)
public class IcsUtil
{
  public static class IcsAdWebView
    extends AdWebView
  {
    public IcsAdWebView(n paramN, AdSize paramAdSize)
    {
      super(paramAdSize);
    }
    
    public boolean canScrollHorizontally(int paramInt)
    {
      if (this.a.e.a() != null) {
        return !((ActivationOverlay)this.a.e.a()).b();
      }
      return super.canScrollHorizontally(paramInt);
    }
    
    public boolean canScrollVertically(int paramInt)
    {
      if (this.a.e.a() != null) {
        return !((ActivationOverlay)this.a.e.a()).b();
      }
      return super.canScrollVertically(paramInt);
    }
  }
  
  public static class a
    extends g.a
  {
    public a(n paramN)
    {
      super();
    }
    
    public void onShowCustomView(View paramView, int paramInt, WebChromeClient.CustomViewCallback paramCustomViewCallback)
    {
      paramCustomViewCallback.onCustomViewHidden();
    }
  }
}
