package so.cuo.anes.admob.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import so.cuo.anes.admob.AdMobContext;

public class DestroyADViewFrunction
  implements FREFunction
{
  public DestroyADViewFrunction() {}
  
  public FREObject call(FREContext paramFREContext, FREObject[] paramArrayOfFREObject)
  {
    ((AdMobContext)paramFREContext).destroyADView();
    return null;
  }
}
