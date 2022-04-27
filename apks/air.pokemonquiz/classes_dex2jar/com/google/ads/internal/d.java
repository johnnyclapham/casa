/*
 * Decompiled with CFR 0_96.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Handler
 *  android.text.TextUtils
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.webkit.WebViewClient
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 */
package com.google.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.google.ads.Ad;
import com.google.ads.AdActivity;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.AppEventListener;
import com.google.ads.InterstitialAd;
import com.google.ads.SwipeableAdListener;
import com.google.ads.ae;
import com.google.ads.af;
import com.google.ads.as;
import com.google.ads.b;
import com.google.ads.c;
import com.google.ads.d;
import com.google.ads.doubleclick.SwipeableDfpAdView;
import com.google.ads.e;
import com.google.ads.f;
import com.google.ads.g;
import com.google.ads.h;
import com.google.ads.internal.ActivationOverlay;
import com.google.ads.internal.AdWebView;
import com.google.ads.internal.a;
import com.google.ads.internal.c;
import com.google.ads.internal.e;
import com.google.ads.internal.g;
import com.google.ads.internal.h;
import com.google.ads.internal.i;
import com.google.ads.l;
import com.google.ads.m;
import com.google.ads.n;
import com.google.ads.o;
import com.google.ads.util.AdUtil;
import com.google.ads.util.IcsUtil;
import com.google.ads.util.a;
import com.google.ads.util.b;
import com.google.ads.util.i;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class com.google.ads.internal.d {
    private static final Object a = new Object();
    private String A = null;
    private final n b;
    private com.google.ads.internal.c c;
    private AdRequest d;
    private com.google.ads.internal.g e;
    private AdWebView f;
    private i g;
    private boolean h = false;
    private long i;
    private boolean j;
    private boolean k;
    private boolean l;
    private boolean m;
    private boolean n;
    private SharedPreferences o;
    private long p;
    private af q;
    private boolean r;
    private LinkedList<String> s;
    private LinkedList<String> t;
    private int u = -1;
    private Boolean v;
    private d w;
    private e x;
    private f y;
    private String z = null;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public com.google.ads.internal.d(Ad ad, Activity activity, AdSize adSize, String string, ViewGroup viewGroup, boolean bl) {
        this.r = bl;
        this.e = new com.google.ads.internal.g();
        this.c = null;
        this.d = null;
        this.k = false;
        this.p = 60000;
        this.l = false;
        this.n = false;
        this.m = true;
        com.google.ads.internal.h h = adSize == null ? com.google.ads.internal.h.a : com.google.ads.internal.h.a(adSize, activity.getApplicationContext());
        if (ad instanceof SwipeableDfpAdView) {
            h.a(true);
        }
        if (activity == null) {
            m m = m.a();
            AdView adView = ad instanceof AdView ? (AdView)ad : null;
            InterstitialAd interstitialAd = ad instanceof InterstitialAd ? (InterstitialAd)ad : null;
            this.b = new n(m, ad, adView, interstitialAd, string, null, null, viewGroup, h, this);
            return;
        }
        Object object = a;
        synchronized (object) {
            long l;
            this.o = activity.getApplicationContext().getSharedPreferences("GoogleAdMobAdsPrefs", 0);
            this.i = bl ? ((l = this.o.getLong("Timeout" + string, -1)) < 0 ? 5000 : l) : 60000;
        }
        m m = m.a();
        AdView adView = ad instanceof AdView ? (AdView)ad : null;
        InterstitialAd interstitialAd = ad instanceof InterstitialAd ? (InterstitialAd)ad : null;
        this.b = new n(m, ad, adView, interstitialAd, string, activity, activity.getApplicationContext(), viewGroup, h, this);
        this.q = new af(this);
        this.s = new LinkedList();
        this.t = new LinkedList();
        this.a();
        AdUtil.h(this.b.f.a());
        this.w = new d();
        this.x = new e(this);
        this.v = null;
        this.y = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(f f, Boolean bl) {
        List<String> list;
        List<String> list2 = f.d();
        if (list2 == null) {
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add("http://e.admob.com/imp?ad_loc=@gw_adlocid@&qdata=@gw_qdata@&ad_network_id=@gw_adnetid@&js=@gw_sdkver@&session_id=@gw_sessid@&seq_num=@gw_seqnum@&nr=@gw_adnetrefresh@&adt=@gw_adt@&aec=@gw_aec@");
            list = arrayList;
        } else {
            list = list2;
        }
        String string = f.b();
        this.a(list, f.a(), string, f.c(), bl, this.e.d(), this.e.e());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a(List<String> list, String string) {
        List<String> list2;
        if (list == null) {
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add("http://e.admob.com/nofill?ad_loc=@gw_adlocid@&qdata=@gw_qdata@&js=@gw_sdkver@&session_id=@gw_sessid@&seq_num=@gw_seqnum@&adt=@gw_adt@&aec=@gw_aec@");
            list2 = arrayList;
        } else {
            list2 = list;
        }
        this.a(list2, null, null, string, null, this.e.d(), this.e.e());
    }

    private void a(List<String> list, String string, String string2, String string3, Boolean bl, String string4, String string5) {
        String string6 = AdUtil.a(this.b.f.a());
        b b = b.a();
        String string7 = b.b().toString();
        String string8 = b.c().toString();
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            new Thread(new ae(g.a(iterator.next(), this.b.h.a(), bl, string6, string, string2, string3, string7, string8, string4, string5), this.b.f.a())).start();
        }
        this.e.b();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void b(f f, Boolean bl) {
        List<String> list;
        List<String> list2 = f.e();
        if (list2 == null) {
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add("http://e.admob.com/clk?ad_loc=@gw_adlocid@&qdata=@gw_qdata@&ad_network_id=@gw_adnetid@&js=@gw_sdkver@&session_id=@gw_sessid@&seq_num=@gw_seqnum@&nr=@gw_adnetrefresh@");
            list = arrayList;
        } else {
            list = list2;
        }
        String string = f.b();
        this.a(list, f.a(), string, f.c(), bl, null, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void A() {
        synchronized (this) {
            com.google.ads.util.a.a(this.b.b());
            if (this.k) {
                this.k = false;
                if (this.v == null) {
                    com.google.ads.util.b.b("isMediationFlag is null in show() with isReady() true. we should have an ad and know whether this is a mediation request or not. ");
                } else if (this.v.booleanValue()) {
                    if (this.x.c()) {
                        this.a(this.y, (Boolean)false);
                    }
                } else {
                    AdActivity.launchAdActivity(this, new com.google.ads.internal.e("interstitial"));
                    this.y();
                }
            } else {
                com.google.ads.util.b.c("Cannot show interstitial because it is not loaded and ready.");
            }
            return;
        }
    }

    public void B() {
        synchronized (this) {
            if (this.c != null) {
                this.c.a();
                this.c = null;
            }
            if (this.f != null) {
                this.f.stopLoading();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    protected void C() {
        synchronized (this) {
            Activity activity = this.b.c.a();
            if (activity == null) {
                com.google.ads.util.b.e("activity was null while trying to ping click tracking URLs.");
            } else {
                Iterator<String> iterator = this.t.iterator();
                while (iterator.hasNext()) {
                    new Thread(new ae(iterator.next(), activity.getApplicationContext())).start();
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    protected void D() {
        synchronized (this) {
            this.c = null;
            this.k = true;
            this.f.setVisibility(0);
            if (this.b.a()) {
                this.a((View)this.f);
            }
            this.e.g();
            if (this.b.a()) {
                this.y();
            }
            com.google.ads.util.b.c("onReceiveAd()");
            AdListener adListener = this.b.q.a();
            if (adListener != null) {
                adListener.onReceiveAd(this.b.a.a());
            }
            this.b.n.a(this.b.o.a());
            this.b.o.a(null);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void a() {
        synchronized (this) {
            AdWebView adWebView;
            AdSize adSize = this.b.g.a().c();
            IcsUtil.IcsAdWebView icsAdWebView = AdUtil.a >= 14 ? new IcsUtil.IcsAdWebView(this.b, adSize) : (adWebView = new AdWebView(this.b, adSize));
            this.f = icsAdWebView;
            this.f.setVisibility(8);
            this.g = i.a(this, a.d, true, this.b.b());
            this.f.setWebViewClient((WebViewClient)this.g);
            m.a a = this.b.d.a().a.a();
            if (!(AdUtil.a >= a.b.a() || this.b.g.a().a())) {
                com.google.ads.util.b.a("Disabling hardware acceleration for a banner.");
                this.f.g();
            }
            return;
        }
    }

    public void a(float f) {
        synchronized (this) {
            long l = this.p;
            this.p = (long)(1000.0f * f);
            if (this.t() && this.p != l) {
                this.f();
                this.g();
            }
            return;
        }
    }

    public void a(int n) {
        synchronized (this) {
            this.u = n;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void a(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        ActivationOverlay activationOverlay = this.b.e.a();
        Context context = this.b.f.a();
        int n7 = n3 < 0 ? this.b.g.a().c().getWidth() : n3;
        int n8 = AdUtil.a(context, n7);
        Context context2 = this.b.f.a();
        int n9 = n4 < 0 ? this.b.g.a().c().getHeight() : n4;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(n8, AdUtil.a(context2, n9));
        if (n3 < 0) {
            n6 = 0;
            n5 = 0;
        } else {
            n6 = n2;
            n5 = n;
        }
        int n10 = n5 < 0 ? this.b.e.a().d() : n5;
        if (n6 < 0) {
            n6 = this.b.e.a().c();
        }
        this.b.e.a().setXPosition(n10);
        this.b.e.a().setYPosition(n6);
        layoutParams.setMargins(AdUtil.a(this.b.f.a(), n10), AdUtil.a(this.b.f.a(), n6), 0, 0);
        activationOverlay.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void a(long l) {
        Object object = a;
        synchronized (object) {
            SharedPreferences.Editor editor = this.o.edit();
            editor.putLong("Timeout" + this.b.h, l);
            editor.commit();
            if (this.r) {
                this.i = l;
            }
            return;
        }
    }

    public void a(View view) {
        this.b.i.a().setVisibility(0);
        this.b.i.a().removeAllViews();
        this.b.i.a().addView(view);
        if (this.b.g.a().b()) {
            this.b.b.a().a(this.b.n.a(), false, -1, -1, -1, -1);
            if (this.b.e.a().a()) {
                this.b.i.a().addView((View)this.b.e.a(), AdUtil.a(this.b.f.a(), this.b.g.a().c().getWidth()), AdUtil.a(this.b.f.a(), this.b.g.a().c().getHeight()));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void a(View view, h h, f f, boolean bl) {
        synchronized (this) {
            com.google.ads.util.b.a("AdManager.onReceiveGWhirlAd() called.");
            this.k = true;
            this.y = f;
            if (this.b.a()) {
                this.a(view);
                this.a(f, (Boolean)bl);
            }
            this.x.d(h);
            AdListener adListener = this.b.q.a();
            if (adListener != null) {
                adListener.onReceiveAd(this.b.a.a());
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void a(AdRequest.ErrorCode errorCode) {
        synchronized (this) {
            this.c = null;
            if (errorCode == AdRequest.ErrorCode.NETWORK_ERROR) {
                this.a(60.0f);
                if (!this.t()) {
                    this.h();
                }
            }
            if (this.b.b()) {
                if (errorCode == AdRequest.ErrorCode.NO_FILL) {
                    this.e.B();
                } else if (errorCode == AdRequest.ErrorCode.NETWORK_ERROR) {
                    this.e.z();
                }
            }
            com.google.ads.util.b.c("onFailedToReceiveAd(" + (Object)errorCode + ")");
            AdListener adListener = this.b.q.a();
            if (adListener != null) {
                adListener.onFailedToReceiveAd(this.b.a.a(), errorCode);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void a(AdRequest adRequest) {
        synchronized (this) {
            if (this.h) {
                com.google.ads.util.b.e("loadAd called after ad was destroyed.");
            } else if (this.q()) {
                com.google.ads.util.b.e("loadAd called while the ad is already loading, so aborting.");
            } else if (AdActivity.isShowing()) {
                com.google.ads.util.b.e("loadAd called while an interstitial or landing page is displayed, so aborting");
            } else if (AdUtil.c(this.b.f.a()) && AdUtil.b(this.b.f.a())) {
                long l = this.o.getLong("GoogleAdMobDoritosLife", 60000);
                if (as.a(this.b.f.a(), l)) {
                    as.a(this.b.c.a());
                }
                this.k = false;
                this.s.clear();
                this.d = adRequest;
                if (this.w.a()) {
                    this.x.a(this.w.b(), adRequest);
                } else {
                    l l2 = new l(this.b);
                    this.b.o.a(l2);
                    this.c = l2.b.a();
                    this.c.a(adRequest);
                }
            }
            return;
        }
    }

    public void a(c c) {
        synchronized (this) {
            this.c = null;
            this.x.a(c, this.d);
            return;
        }
    }

    public void a(f f, boolean bl) {
        synchronized (this) {
            Locale locale = Locale.US;
            Object[] arrobject = new Object[]{bl};
            com.google.ads.util.b.a(String.format(locale, "AdManager.onGWhirlAdClicked(%b) called.", arrobject));
            this.b(f, bl);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void a(l l, boolean bl, int n, int n2, int n3, int n4) {
        ActivationOverlay activationOverlay = this.b.e.a();
        boolean bl2 = !bl;
        activationOverlay.setOverlayActivated(bl2);
        this.a(n, n2, n3, n4);
        if (this.b.s.a() != null) {
            if (!bl) {
                this.b.s.a().onAdDeactivated(this.b.a.a());
                return;
            }
            this.b.s.a().onAdActivated(this.b.a.a());
        }
    }

    public void a(String string) {
        this.A = string;
        Uri uri = new Uri.Builder().encodedQuery(string).build();
        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> hashMap = AdUtil.b(uri);
        for (String string2 : hashMap.keySet()) {
            stringBuilder.append(string2).append(" = ").append(hashMap.get(string2)).append("\n");
        }
        this.z = stringBuilder.toString().trim();
        if (TextUtils.isEmpty((CharSequence)this.z)) {
            this.z = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void a(String string, String string2) {
        synchronized (this) {
            AppEventListener appEventListener = this.b.r.a();
            if (appEventListener != null) {
                appEventListener.onAppEvent(this.b.a.a(), string, string2);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    protected void a(LinkedList<String> linkedList) {
        synchronized (this) {
            Iterator<String> iterator = linkedList.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.t = linkedList;
                    return;
                }
                String string = iterator.next();
                com.google.ads.util.b.a("Adding a click tracking URL: " + string);
            } while (true);
        }
    }

    public void a(boolean bl) {
        synchronized (this) {
            this.j = bl;
            return;
        }
    }

    public void b() {
        synchronized (this) {
            if (this.x != null) {
                this.x.b();
            }
            this.b.q.a(null);
            this.b.r.a(null);
            this.B();
            this.f();
            if (this.f != null) {
                this.f.destroy();
            }
            this.h = true;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void b(long l) {
        synchronized (this) {
            if (l > 0) {
                this.o.edit().putLong("GoogleAdMobDoritosLife", l).commit();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void b(c c) {
        synchronized (this) {
            com.google.ads.util.b.a("AdManager.onGWhirlNoFill() called.");
            this.a(c.i(), c.c());
            AdListener adListener = this.b.q.a();
            if (adListener != null) {
                adListener.onFailedToReceiveAd(this.b.a.a(), AdRequest.ErrorCode.NO_FILL);
            }
            return;
        }
    }

    protected void b(String string) {
        synchronized (this) {
            com.google.ads.util.b.a("Adding a tracking URL: " + string);
            this.s.add(string);
            return;
        }
    }

    public void b(boolean bl) {
        this.v = bl;
    }

    public String c() {
        return this.z;
    }

    public String d() {
        return this.A;
    }

    public void e() {
        synchronized (this) {
            this.m = false;
            com.google.ads.util.b.a("Refreshing is no longer allowed on this AdView.");
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void f() {
        synchronized (this) {
            if (this.l) {
                com.google.ads.util.b.a("Disabling refreshing.");
                m.a().b.a().removeCallbacks((Runnable)this.q);
                this.l = false;
            } else {
                com.google.ads.util.b.a("Refreshing is already disabled.");
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void g() {
        synchronized (this) {
            this.n = false;
            if (this.b.a()) {
                if (this.m) {
                    if (!this.l) {
                        com.google.ads.util.b.a("Enabling refreshing every " + this.p + " milliseconds.");
                        m.a().b.a().postDelayed((Runnable)this.q, this.p);
                        this.l = true;
                    } else {
                        com.google.ads.util.b.a("Refreshing is already enabled.");
                    }
                } else {
                    com.google.ads.util.b.a("Refreshing disabled on this AdView");
                }
            } else {
                com.google.ads.util.b.a("Tried to enable refreshing on something other than an AdView.");
            }
            return;
        }
    }

    public void h() {
        this.g();
        this.n = true;
    }

    public n i() {
        return this.b;
    }

    public d j() {
        synchronized (this) {
            d d = this.w;
            return d;
        }
    }

    public com.google.ads.internal.c k() {
        synchronized (this) {
            com.google.ads.internal.c c = this.c;
            return c;
        }
    }

    public AdWebView l() {
        synchronized (this) {
            AdWebView adWebView = this.f;
            return adWebView;
        }
    }

    public i m() {
        synchronized (this) {
            i i = this.g;
            return i;
        }
    }

    public com.google.ads.internal.g n() {
        return this.e;
    }

    public int o() {
        synchronized (this) {
            int n = this.u;
            return n;
        }
    }

    public long p() {
        return this.i;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean q() {
        synchronized (this) {
            com.google.ads.internal.c c = this.c;
            if (c == null) return false;
            return true;
        }
    }

    public boolean r() {
        synchronized (this) {
            boolean bl = this.j;
            return bl;
        }
    }

    public boolean s() {
        synchronized (this) {
            boolean bl = this.k;
            return bl;
        }
    }

    public boolean t() {
        synchronized (this) {
            boolean bl = this.l;
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void u() {
        synchronized (this) {
            this.e.C();
            com.google.ads.util.b.c("onDismissScreen()");
            AdListener adListener = this.b.q.a();
            if (adListener != null) {
                adListener.onDismissScreen(this.b.a.a());
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void v() {
        synchronized (this) {
            com.google.ads.util.b.c("onPresentScreen()");
            AdListener adListener = this.b.q.a();
            if (adListener != null) {
                adListener.onPresentScreen(this.b.a.a());
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void w() {
        synchronized (this) {
            com.google.ads.util.b.c("onLeaveApplication()");
            AdListener adListener = this.b.q.a();
            if (adListener != null) {
                adListener.onLeaveApplication(this.b.a.a());
            }
            return;
        }
    }

    public void x() {
        this.e.f();
        this.C();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void y() {
        synchronized (this) {
            Activity activity = this.b.c.a();
            if (activity == null) {
                com.google.ads.util.b.e("activity was null while trying to ping tracking URLs.");
            } else {
                Iterator<String> iterator = this.s.iterator();
                while (iterator.hasNext()) {
                    new Thread(new ae(iterator.next(), activity.getApplicationContext())).start();
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void z() {
        synchronized (this) {
            boolean bl = this.h;
            if (!bl) {
                if (this.d != null) {
                    if (this.b.a()) {
                        if (this.b.j.a().isShown() && AdUtil.d()) {
                            com.google.ads.util.b.c("Refreshing ad.");
                            this.a(this.d);
                        } else {
                            com.google.ads.util.b.a("Not refreshing because the ad is not visible.");
                        }
                        if (this.n) {
                            this.f();
                        } else {
                            m.a().b.a().postDelayed((Runnable)this.q, this.p);
                        }
                    } else {
                        com.google.ads.util.b.a("Tried to refresh an ad that wasn't an AdView.");
                    }
                } else {
                    com.google.ads.util.b.a("Tried to refresh before calling loadAd().");
                }
            }
            return;
        }
    }
}

