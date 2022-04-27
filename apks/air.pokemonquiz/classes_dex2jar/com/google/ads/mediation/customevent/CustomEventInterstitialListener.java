package com.google.ads.mediation.customevent;

public abstract interface CustomEventInterstitialListener
  extends CustomEventListener
{
  public abstract void onReceivedAd();
}
