package com.google.ads;

import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.google.ads.util.a;
import com.google.ads.util.b;

class k
  implements MediationInterstitialListener
{
  private final h a;
  
  k(h paramH)
  {
    this.a = paramH;
  }
  
  public void onDismissScreen(MediationInterstitialAdapter<?, ?> paramMediationInterstitialAdapter)
  {
    synchronized (this.a)
    {
      this.a.j().b(this.a);
      return;
    }
  }
  
  public void onFailedToReceiveAd(MediationInterstitialAdapter<?, ?> paramMediationInterstitialAdapter, AdRequest.ErrorCode paramErrorCode)
  {
    for (;;)
    {
      synchronized (this.a)
      {
        a.a(paramMediationInterstitialAdapter, this.a.i());
        b.a("Mediation adapter " + paramMediationInterstitialAdapter.getClass().getName() + " failed to receive ad with error code: " + paramErrorCode);
        if (this.a.c())
        {
          b.b("Got an onFailedToReceiveAd() callback after loadAdTask is done from an interstitial adapter.  Ignoring callback.");
          return;
        }
        h localH2 = this.a;
        if (paramErrorCode == AdRequest.ErrorCode.NO_FILL)
        {
          localA = g.a.b;
          localH2.a(false, localA);
        }
      }
      g.a localA = g.a.c;
    }
  }
  
  public void onLeaveApplication(MediationInterstitialAdapter<?, ?> paramMediationInterstitialAdapter)
  {
    synchronized (this.a)
    {
      this.a.j().c(this.a);
      return;
    }
  }
  
  public void onPresentScreen(MediationInterstitialAdapter<?, ?> paramMediationInterstitialAdapter)
  {
    synchronized (this.a)
    {
      this.a.j().a(this.a);
      return;
    }
  }
  
  public void onReceivedAd(MediationInterstitialAdapter<?, ?> paramMediationInterstitialAdapter)
  {
    synchronized (this.a)
    {
      a.a(paramMediationInterstitialAdapter, this.a.i());
      if (this.a.c())
      {
        b.e("Got an onReceivedAd() callback after loadAdTask is done from an interstitial adapter. Ignoring callback.");
        return;
      }
      this.a.a(true, g.a.a);
    }
  }
}
