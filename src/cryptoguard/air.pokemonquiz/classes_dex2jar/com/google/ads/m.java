package com.google.ads;

import android.os.Handler;
import android.os.Looper;
import com.google.ads.util.i;
import com.google.ads.util.i.b;
import com.google.ads.util.i.c;

public class m
  extends i
{
  private static final m c = new m();
  public final i.b<a> a = new i.b(this, "constants", new a());
  public final i.b<Handler> b = new i.b(this, "uiHandler", new Handler(Looper.getMainLooper()));
  
  private m() {}
  
  public static m a()
  {
    return c;
  }
  
  public static final class a
    extends i
  {
    public final i.c<String> a = new i.c(this, "ASDomains", null);
    public final i.c<Integer> b = new i.c(this, "minHwAccelerationVersionBanner", Integer.valueOf(18));
    public final i.c<Integer> c = new i.c(this, "minHwAccelerationVersionOverlay", Integer.valueOf(18));
    public final i.c<Integer> d = new i.c(this, "minHwAccelerationVersionOverlay", Integer.valueOf(14));
    public final i.c<String> e = new i.c(this, "mraidBannerPath", "http://media.admob.com/mraid/v1/mraid_app_banner.js");
    public final i.c<String> f = new i.c(this, "mraidExpandedBannerPath", "http://media.admob.com/mraid/v1/mraid_app_expanded_banner.js");
    public final i.c<String> g = new i.c(this, "mraidInterstitialPath", "http://media.admob.com/mraid/v1/mraid_app_interstitial.js");
    public final i.c<String> h = new i.c(this, "badAdReportPath", "https://badad.googleplex.com/s/reportAd");
    public final i.c<Long> i = new i.c(this, "appCacheMaxSize", Long.valueOf(0L));
    public final i.c<Long> j = new i.c(this, "appCacheMaxSizePaddingInBytes", Long.valueOf(131072L));
    public final i.c<Long> k = new i.c(this, "maxTotalAppCacheQuotaInBytes", Long.valueOf(5242880L));
    public final i.c<Long> l = new i.c(this, "maxTotalDatabaseQuotaInBytes", Long.valueOf(5242880L));
    public final i.c<Long> m = new i.c(this, "maxDatabaseQuotaPerOriginInBytes", Long.valueOf(1048576L));
    public final i.c<Long> n = new i.c(this, "databaseQuotaIncreaseStepInBytes", Long.valueOf(131072L));
    public final i.c<Boolean> o = new i.c(this, "isInitialized", Boolean.valueOf(false));
    
    public a() {}
  }
}
