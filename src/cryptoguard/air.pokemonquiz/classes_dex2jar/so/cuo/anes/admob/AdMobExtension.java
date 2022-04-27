package so.cuo.anes.admob;

import android.util.Log;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class AdMobExtension
  implements FREExtension
{
  private static String TAG = AdMobExtension.class.getName();
  public AdMobContext context;
  
  public AdMobExtension() {}
  
  public FREContext createContext(String paramString)
  {
    this.context = new AdMobContext();
    return this.context;
  }
  
  public void dispose()
  {
    Log.d(TAG, "NativeAdsExtension disposed.");
    this.context.dispose();
    this.context = null;
  }
  
  public void initialize()
  {
    Log.d(TAG, "NativeAdsExtension initialized.");
  }
}
