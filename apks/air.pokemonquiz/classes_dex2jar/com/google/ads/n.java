package com.google.ads;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import com.google.ads.internal.ActivationOverlay;
import com.google.ads.internal.d;
import com.google.ads.internal.h;
import com.google.ads.util.i;
import com.google.ads.util.i.b;
import com.google.ads.util.i.c;
import com.google.ads.util.i.d;

public class n
  extends i
{
  public final i.b<Ad> a;
  public final i.b<d> b;
  public final i.d<Activity> c;
  public final i.b<m> d;
  public final i.b<ActivationOverlay> e;
  public final i.b<Context> f;
  public final i.b<h> g;
  public final i.b<String> h;
  public final i.b<ViewGroup> i;
  public final i.b<AdView> j;
  public final i.b<InterstitialAd> k;
  public final i.b<ak> l;
  public final i.b<al> m;
  public final i.c<l> n = new i.c(this, "currentAd", null);
  public final i.c<l> o = new i.c(this, "nextAd", null);
  public final i.c<AdSize[]> p;
  public final i.c<AdListener> q = new i.c(this, "adListener");
  public final i.c<AppEventListener> r = new i.c(this, "appEventListener");
  public final i.c<SwipeableAdListener> s = new i.c(this, "swipeableEventListener");
  
  public n(m paramM, Ad paramAd, AdView paramAdView, InterstitialAd paramInterstitialAd, String paramString, Activity paramActivity, Context paramContext, ViewGroup paramViewGroup, h paramH, d paramD)
  {
    this.d = new i.b(this, "appState", paramM);
    this.a = new i.b(this, "ad", paramAd);
    this.j = new i.b(this, "adView", paramAdView);
    this.g = new i.b(this, "adType", paramH);
    this.h = new i.b(this, "adUnitId", paramString);
    this.c = new i.d(this, "activity", paramActivity);
    this.k = new i.b(this, "interstitialAd", paramInterstitialAd);
    this.i = new i.b(this, "bannerContainer", paramViewGroup);
    this.f = new i.b(this, "applicationContext", paramContext);
    this.p = new i.c(this, "adSizes", null);
    this.b = new i.b(this, "adManager", paramD);
    if ((paramH != null) && (paramH.b())) {}
    for (ActivationOverlay localActivationOverlay = new ActivationOverlay(this);; localActivationOverlay = null)
    {
      this.e = new i.b(this, "activationOverlay", localActivationOverlay);
      ak localAk1;
      al localAl1;
      if (paramActivity != null)
      {
        ak localAk2 = ak.a("afma-sdk-a-v6.3.1", paramActivity);
        al localAl2 = new al(localAk2);
        localAk1 = localAk2;
        localAl1 = localAl2;
      }
      for (;;)
      {
        this.l = new i.b(this, "spamSignals", localAk1);
        this.m = new i.b(this, "spamSignalsUtil", localAl1);
        return;
        localAl1 = null;
        localAk1 = null;
      }
    }
  }
  
  public boolean a()
  {
    return !b();
  }
  
  public boolean b()
  {
    return ((h)this.g.a()).a();
  }
}
