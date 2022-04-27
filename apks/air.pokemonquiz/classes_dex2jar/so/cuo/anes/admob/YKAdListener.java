package so.cuo.anes.admob;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest.ErrorCode;

public class YKAdListener
  implements AdListener
{
  private AdMobContext context;
  
  public YKAdListener(AdMobContext paramAdMobContext)
  {
    this.context = paramAdMobContext;
  }
  
  public void onDismissScreen(Ad paramAd)
  {
    this.context.dispatchStatusEventAsync("onDismissScreen", "AD_ADMOB_LEVEL");
  }
  
  public void onFailedToReceiveAd(Ad paramAd, AdRequest.ErrorCode paramErrorCode)
  {
    this.context.dispatchStatusEventAsync("onFailedToReceiveAd", paramErrorCode.toString());
  }
  
  public void onLeaveApplication(Ad paramAd)
  {
    this.context.dispatchStatusEventAsync("onLeaveApplication", "AD_ADMOB_LEVEL");
  }
  
  public void onPresentScreen(Ad paramAd)
  {
    this.context.dispatchStatusEventAsync("onPresentScreen", "AD_ADMOB_LEVEL");
  }
  
  public void onReceiveAd(Ad paramAd)
  {
    this.context.dispatchStatusEventAsync("onReceiveAd", "AD_ADMOB_LEVEL");
  }
}
