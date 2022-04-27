package com.google.ads;

import android.app.Activity;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import com.google.ads.internal.d;
import com.google.ads.internal.g;
import com.google.ads.util.b;
import com.google.ads.util.i.b;
import com.google.ads.util.i.d;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class e
{
  private final d a;
  private h b = null;
  private final Object c = new Object();
  private Thread d = null;
  private final Object e = new Object();
  private boolean f = false;
  private final Object g = new Object();
  
  protected e()
  {
    this.a = null;
  }
  
  public e(d paramD)
  {
    com.google.ads.util.a.b(paramD);
    this.a = paramD;
  }
  
  public static boolean a(c paramC, d paramD)
  {
    if (paramC.j() == null) {
      return true;
    }
    if (paramD.i().b())
    {
      if (!paramC.j().a())
      {
        b.e("InterstitialAd received a mediation response corresponding to a non-interstitial ad. Make sure you specify 'interstitial' as the ad-type in the mediation UI.");
        return false;
      }
      return true;
    }
    AdSize localAdSize1 = ((com.google.ads.internal.h)paramD.i().g.a()).c();
    if (paramC.j().a())
    {
      b.e("AdView received a mediation response corresponding to an interstitial ad. Make sure you specify the banner ad size corresponding to the AdSize you used in your AdView  (" + localAdSize1 + ") in the ad-type field in the mediation UI.");
      return false;
    }
    AdSize localAdSize2 = paramC.j().c();
    if (localAdSize2 != localAdSize1)
    {
      b.e("Mediation server returned ad size: '" + localAdSize2 + "', while the AdView was created with ad size: '" + localAdSize1 + "'. Using the ad-size passed to the AdView on creation.");
      return false;
    }
    return true;
  }
  
  private boolean a(h paramH, String paramString)
  {
    if (e() != paramH)
    {
      b.c("GWController: ignoring callback to " + paramString + " from non showing ambassador with adapter class: '" + paramH.h() + "'.");
      return false;
    }
    return true;
  }
  
  private boolean a(String paramString, Activity paramActivity, AdRequest paramAdRequest, final f paramF, HashMap<String, String> paramHashMap, long paramLong)
  {
    synchronized (new h(this, (com.google.ads.internal.h)this.a.i().g.a(), paramF, paramString, paramAdRequest, paramHashMap))
    {
      ???.a(paramActivity);
      long l1 = paramLong;
      try
      {
        while ((!???.c()) && (l1 > 0L))
        {
          long l2 = SystemClock.elapsedRealtime();
          ???.wait(l1);
          long l3 = SystemClock.elapsedRealtime();
          l1 -= l3 - l2;
        }
        final View localView;
        localObject = finally;
      }
      catch (InterruptedException localInterruptedException)
      {
        b.a("Interrupted while waiting for ad network to load ad using adapter class: " + paramString);
        this.a.n().a(???.e());
        if ((???.c()) && (???.d()))
        {
          if (this.a.i().b()) {}
          for (localView = null;; localView = ???.f())
          {
            ((Handler)m.a().b.a()).post(new Runnable()
            {
              public void run()
              {
                if (e.a(e.this, localH))
                {
                  b.a("Trying to switch GWAdNetworkAmbassadors, but GWController().destroy() has been called. Destroying the new ambassador and terminating mediation.");
                  return;
                }
                e.b(e.this).a(localView, localH, paramF, false);
              }
            });
            return true;
          }
        }
        ???.b();
        return false;
      }
    }
  }
  
  private void b(final c paramC, AdRequest paramAdRequest)
  {
    label243:
    do
    {
      long l;
      HashMap localHashMap;
      f localF;
      String str4;
      Activity localActivity;
      do
      {
        for (;;)
        {
          synchronized (this.e)
          {
            com.google.ads.util.a.a(Thread.currentThread(), this.d);
            List localList1 = paramC.f();
            if (paramC.a())
            {
              l = paramC.b();
              Iterator localIterator1 = localList1.iterator();
              if (!localIterator1.hasNext()) {
                break label294;
              }
              a localA = (a)localIterator1.next();
              b.a("Looking to fetch ads from network: " + localA.b());
              List localList2 = localA.c();
              localHashMap = localA.e();
              localList3 = localA.d();
              String str1 = localA.a();
              String str2 = localA.b();
              String str3 = paramC.c();
              if (localList3 == null) {
                break label243;
              }
              localF = new f(str1, str2, str3, localList3, paramC.h(), paramC.i());
              Iterator localIterator2 = localList2.iterator();
              if (!localIterator2.hasNext()) {
                continue;
              }
              str4 = (String)localIterator2.next();
              localActivity = (Activity)this.a.i().c.a();
              if (localActivity != null) {
                break;
              }
              b.a("Activity is null while mediating.  Terminating mediation thread.");
              return;
            }
          }
          l = 10000L;
          continue;
          List localList3 = paramC.g();
        }
        this.a.n().c();
      } while (a(str4, localActivity, paramAdRequest, localF, localHashMap, l));
    } while (!d());
    b.a("GWController.destroy() called. Terminating mediation thread.");
    return;
    label294:
    ((Handler)m.a().b.a()).post(new Runnable()
    {
      public void run()
      {
        e.b(e.this).b(paramC);
      }
    });
  }
  
  private boolean d()
  {
    synchronized (this.g)
    {
      boolean bool = this.f;
      return bool;
    }
  }
  
  private h e()
  {
    synchronized (this.c)
    {
      h localH = this.b;
      return localH;
    }
  }
  
  private boolean e(h paramH)
  {
    synchronized (this.g)
    {
      if (d())
      {
        paramH.b();
        return true;
      }
      return false;
    }
  }
  
  public void a(final c paramC, final AdRequest paramAdRequest)
  {
    for (;;)
    {
      synchronized (this.e)
      {
        if (a())
        {
          b.c("Mediation thread is not done executing previous mediation  request. Ignoring new mediation request");
          return;
        }
        if (paramC.d())
        {
          this.a.a(paramC.e());
          if (!this.a.t()) {
            this.a.g();
          }
          a(paramC, this.a);
          this.d = new Thread(new Runnable()
          {
            public void run()
            {
              e.a(e.this, paramC, paramAdRequest);
              synchronized (e.a(e.this))
              {
                e.a(e.this, null);
                return;
              }
            }
          });
          this.d.start();
          return;
        }
      }
      if (this.a.t()) {
        this.a.f();
      }
    }
  }
  
  public void a(h paramH)
  {
    if (!a(paramH, "onPresentScreen")) {
      return;
    }
    ((Handler)m.a().b.a()).post(new Runnable()
    {
      public void run()
      {
        e.b(e.this).v();
      }
    });
  }
  
  public void a(h paramH, final View paramView)
  {
    if (e() != paramH)
    {
      b.c("GWController: ignoring onAdRefreshed() callback from non-showing ambassador (adapter class name is '" + paramH.h() + "').");
      return;
    }
    this.a.n().a(g.a.a);
    final f localF = this.b.a();
    ((Handler)m.a().b.a()).post(new Runnable()
    {
      public void run()
      {
        e.b(e.this).a(paramView, e.c(e.this), localF, true);
      }
    });
  }
  
  public void a(h paramH, final boolean paramBoolean)
  {
    if (!a(paramH, "onAdClicked()")) {
      return;
    }
    final f localF = paramH.a();
    ((Handler)m.a().b.a()).post(new Runnable()
    {
      public void run()
      {
        e.b(e.this).a(localF, paramBoolean);
      }
    });
  }
  
  public boolean a()
  {
    for (;;)
    {
      synchronized (this.e)
      {
        if (this.d != null)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  public void b()
  {
    synchronized (this.g)
    {
      this.f = true;
      d(null);
      synchronized (this.e)
      {
        if (this.d != null) {
          this.d.interrupt();
        }
        return;
      }
    }
  }
  
  public void b(h paramH)
  {
    if (!a(paramH, "onDismissScreen")) {
      return;
    }
    ((Handler)m.a().b.a()).post(new Runnable()
    {
      public void run()
      {
        e.b(e.this).u();
      }
    });
  }
  
  public void c(h paramH)
  {
    if (!a(paramH, "onLeaveApplication")) {
      return;
    }
    ((Handler)m.a().b.a()).post(new Runnable()
    {
      public void run()
      {
        e.b(e.this).w();
      }
    });
  }
  
  public boolean c()
  {
    com.google.ads.util.a.a(this.a.i().b());
    h localH = e();
    if (localH != null)
    {
      localH.g();
      return true;
    }
    b.b("There is no ad ready to show.");
    return false;
  }
  
  public void d(h paramH)
  {
    synchronized (this.c)
    {
      if (this.b != paramH)
      {
        if (this.b != null) {
          this.b.b();
        }
        this.b = paramH;
      }
      return;
    }
  }
}
