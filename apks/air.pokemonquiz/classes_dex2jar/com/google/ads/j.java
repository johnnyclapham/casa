package com.google.ads;

import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.util.a;
import com.google.ads.util.b;

class j
  implements MediationBannerListener
{
  private final h a;
  private boolean b;
  
  public j(h paramH)
  {
    this.a = paramH;
  }
  
  public void onClick(MediationBannerAdapter<?, ?> paramMediationBannerAdapter)
  {
    synchronized (this.a)
    {
      a.a(this.a.c());
      this.a.j().a(this.a, this.b);
      return;
    }
  }
  
  public void onDismissScreen(MediationBannerAdapter<?, ?> paramMediationBannerAdapter)
  {
    synchronized (this.a)
    {
      this.a.j().b(this.a);
      return;
    }
  }
  
  public void onFailedToReceiveAd(MediationBannerAdapter<?, ?> paramMediationBannerAdapter, AdRequest.ErrorCode paramErrorCode)
  {
    synchronized (this.a)
    {
      a.a(paramMediationBannerAdapter, this.a.i());
      b.a("Mediation adapter " + paramMediationBannerAdapter.getClass().getName() + " failed to receive ad with error code: " + paramErrorCode);
      if (!this.a.c())
      {
        h localH2 = this.a;
        if (paramErrorCode == AdRequest.ErrorCode.NO_FILL)
        {
          localA = g.a.b;
          localH2.a(false, localA);
        }
      }
      else
      {
        return;
      }
      g.a localA = g.a.c;
    }
  }
  
  public void onLeaveApplication(MediationBannerAdapter<?, ?> paramMediationBannerAdapter)
  {
    synchronized (this.a)
    {
      this.a.j().c(this.a);
      return;
    }
  }
  
  public void onPresentScreen(MediationBannerAdapter<?, ?> paramMediationBannerAdapter)
  {
    synchronized (this.a)
    {
      this.a.j().a(this.a);
      return;
    }
  }
  
  public void onReceivedAd(MediationBannerAdapter<?, ?> paramMediationBannerAdapter)
  {
    synchronized (this.a)
    {
      a.a(paramMediationBannerAdapter, this.a.i());
      try
      {
        this.a.a(paramMediationBannerAdapter.getBannerView());
        if (!this.a.c())
        {
          this.b = false;
          this.a.a(true, g.a.a);
          return;
        }
      }
      catch (Throwable localThrowable)
      {
        b.b("Error while getting banner View from adapter (" + this.a.h() + "): ", localThrowable);
        if (!this.a.c()) {
          this.a.a(false, g.a.f);
        }
        return;
      }
    }
    this.b = true;
    this.a.j().a(this.a, this.a.f());
  }
}
