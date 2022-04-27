package so.cuo.anes.admob;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.RelativeLayout.LayoutParams;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.SimpleAdView;
import java.util.HashMap;
import java.util.Map;
import so.cuo.anes.admob.functions.AddToStageFunction;
import so.cuo.anes.admob.functions.CreateViewFunction;
import so.cuo.anes.admob.functions.DestroyADViewFrunction;
import so.cuo.anes.admob.functions.GetAdSizeFunction;
import so.cuo.anes.admob.functions.GetMacAddressFunction;
import so.cuo.anes.admob.functions.GetScreenSizeFunction;
import so.cuo.anes.admob.functions.LoadADFunction;
import so.cuo.anes.admob.functions.RemoveFromStage;
import so.cuo.anes.admob.functions.SetIsLandscapeFunction;
import so.cuo.anes.admob.functions.StopLoadingFunction;

public class AdMobContext
  extends FREContext
{
  private static final String TAG = AdMobContext.class.getName();
  private AbsoluteLayout absoluteLayout;
  private AbsoluteLayout.LayoutParams absparam = new AbsoluteLayout.LayoutParams(-2, -2, 0, 0);
  public AdView adView;
  private YKAdListener listener;
  
  public AdMobContext() {}
  
  public void addToStage(int paramInt1, int paramInt2)
  {
    if (this.adView == null) {
      return;
    }
    this.absparam.x = paramInt1;
    this.absparam.y = paramInt2;
    this.absoluteLayout.addView(this.adView, this.absparam);
  }
  
  public void createView(int paramInt1, int paramInt2, String paramString)
  {
    if (this.absoluteLayout == null)
    {
      this.absoluteLayout = new AbsoluteLayout(getActivity());
      getActivity().addContentView(this.absoluteLayout, new RelativeLayout.LayoutParams(-1, -1));
      this.listener = new YKAdListener(this);
    }
    if ((paramInt1 == -1) && (paramInt2 == -2)) {}
    for (AdSize localAdSize = AdSize.SMART_BANNER;; localAdSize = new AdSize(paramInt1, paramInt2))
    {
      if (this.adView != null) {
        destroyADView();
      }
      this.adView = SimpleAdView.createView(getActivity(), localAdSize, paramString);
      this.adView.setAdListener(this.listener);
      return;
    }
  }
  
  public void destroyADView()
  {
    if (this.adView == null) {
      return;
    }
    this.adView.stopLoading();
    this.adView.setAdListener(null);
    removeFromStage();
    this.adView.destroy();
    this.adView = null;
  }
  
  public void dispose()
  {
    removeFromStage();
    this.absoluteLayout = null;
    this.listener = null;
  }
  
  public Map<String, FREFunction> getFunctions()
  {
    Log.d(TAG, "NativeAdsExtension compose function map.");
    HashMap localHashMap = new HashMap();
    localHashMap.put("addToStage", new AddToStageFunction());
    localHashMap.put("createView", new CreateViewFunction());
    localHashMap.put("destroyADView", new DestroyADViewFrunction());
    localHashMap.put("getMacAddress", new GetMacAddressFunction());
    localHashMap.put("getScreenSize", new GetScreenSizeFunction());
    localHashMap.put("loadAD", new LoadADFunction());
    localHashMap.put("removeFromStage", new RemoveFromStage());
    localHashMap.put("setIsLandscape", new SetIsLandscapeFunction());
    localHashMap.put("stopLoading", new StopLoadingFunction());
    localHashMap.put("getAdSize", new GetAdSizeFunction());
    return localHashMap;
  }
  
  public String getMacAddress()
  {
    return Settings.Secure.getString(getActivity().getApplicationContext().getContentResolver(), "android_id");
  }
  
  public String getScreenSize()
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    int i = localDisplayMetrics.widthPixels;
    int j = localDisplayMetrics.heightPixels;
    return i + "_" + j + "_" + localDisplayMetrics.density;
  }
  
  public String getSize()
  {
    if (this.adView != null) {
      return this.adView.getWidth() + "_" + this.adView.getHeight();
    }
    return "0_0";
  }
  
  public void loadAD(boolean paramBoolean)
  {
    if (this.adView == null) {
      return;
    }
    AdRequest localAdRequest = new AdRequest();
    localAdRequest.setTesting(paramBoolean);
    this.adView.loadAd(localAdRequest);
  }
  
  public void removeFromStage()
  {
    if (this.adView == null) {}
    while (this.adView.getParent() == null) {
      return;
    }
    this.absoluteLayout.removeView(this.adView);
  }
  
  public void setIsLandscape(boolean paramBoolean) {}
  
  public void stopLoading()
  {
    if (this.adView == null) {
      return;
    }
    this.adView.stopLoading();
  }
}
